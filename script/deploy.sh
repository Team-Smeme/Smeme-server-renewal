#!/bin/bash
source .env

NGINX_CONFIG_PATH="/etc/nginx"
ALL_PORT=("8081" "8082")
AVAILABLE_PORT=()
REGISTRY_URL=${REGISTRY_URL}
IMAGE_NAME=${IMAGE_NAME}
SECRET_MANAGER_TOKEN=${SECRET_MANAGER_TOKEN}
SECRET_MANAGER_WORKSPACE_ID=${SECRET_MANAGER_WORKSPACE_ID}
TAG="latest"
CONTAINER_NAME="smeem"
HEALTH_CHECK_URI="/actuator/health"

docker_ps_output=$(sudo docker ps | grep "$CONTAINER_NAME-")
running_container_name=$(echo "$docker_ps_output" | awk '{print $NF}')
blue_port=$(echo "$running_container_name" | awk -F'-' '{print $NF}')

if [ -z "$blue_port" ]; then
    echo "> Running port: none"
else
    echo "> Running port: $blue_port"
fi

# 실행 가능한 포트 확인
for item in "${ALL_PORT[@]}"; do
    if [ "$item" != "$blue_port" ]; then
        AVAILABLE_PORT+=("$item")
    fi
done

# 실행 가능한 포트 없으면 끝내기
if [ ${#AVAILABLE_PORT[@]} -eq 0 ]; then
    echo "> No available port"
    exit 1
fi

green_port=${AVAILABLE_PORT[0]}

echo "----------------------------------------------------------------------"

# 도커 이미지 풀 받기
echo "> Pull Docker Image"
sudo docker pull "${REGISTRY_URL}"/"${IMAGE_NAME}":"${TAG}"

# 그린 포트로 서버 실행
echo "> Run Docker with ${green_port} port"

if [ "$(sudo docker ps -a -q -f name=${CONTAINER_NAME}-"${green_port}")" ]; then
  docker stop ${CONTAINER_NAME}-"${green_port}"
  docker rm ${CONTAINER_NAME}-"${green_port}"
fi

docker run -d --name ${CONTAINER_NAME}-"${green_port}" -p "${green_port}":8080 \
  -e SECRET_MANAGER_TOKEN="${SECRET_MANAGER_TOKEN}" \
  -e SECRET_MANAGER_WORKSPACE_ID="${SECRET_MANAGER_WORKSPACE_ID}" \
  "${REGISTRY_URL}"/"${IMAGE_NAME}":${TAG}

echo "----------------------------------------------------------------------"

# green_port 서버 실행 확인
sleep 15

for retry_count in {1..15}
do
    echo "> Health check"

    response=$(curl -s http://localhost:"${green_port}"${HEALTH_CHECK_URI})
    # shellcheck disable=SC2126
    up_count=$(echo "${response}" | grep 'UP' | wc -l)

    if [ "${up_count}" -ge 1 ]
    then
        echo "> SUCCESS"
        break
    else
        echo "> Not run yet"
        echo "> Response: ${response}"
    fi

    if [ "${retry_count}" -eq 15 ]
		then
        echo "> Failed to running server"
        docker rm -f ${CONTAINER_NAME}-"${green_port}"
        exit 1
    fi

    sleep 2
done

echo "----------------------------------------------------------------------"

# Nginx 포트 스위칭
echo "> Nginx Switching"

# Nginx 실행 중인지 확인
if ! pgrep -x "nginx" > /dev/null
then
    echo "Start Nginx"
    sudo systemctl start nginx
fi

## 서비스 URL 업데이트 및 Nginx 리로드
echo "set \$service_url 127.0.0.1:${green_port};" | sudo tee ${NGINX_CONFIG_PATH}/conf.d/service-url.inc
sudo nginx -t && sudo nginx -s reload

sleep 1

echo "----------------------------------------------------------------------"

# Nginx 통해서 서버 접근 가능한지 확인
response=$(curl -s http://localhost:"${green_port}"${HEALTH_CHECK_URI})
# shellcheck disable=SC2126
up_count=$(echo "$response" | grep 'UP' | wc -l)

if [ "$up_count" -ge 1 ]
then
    echo "> Success to Switching"
else
    echo "> Failed to Switching"
    echo "> Response: ${response}"
    exit 1
fi

# blue_port 서버 있다면 중단
if [ -n "$blue_port" ]; then
    echo "> Stop ${blue_port} port"
    sudo docker rm -f ${CONTAINER_NAME}-"${blue_port}"
fi
