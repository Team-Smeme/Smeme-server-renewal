#!/bin/bash
source .env

REGISTRY_URL=${REGISTRY_URL}
IMAGE_NAME=${IMAGE_NAME}
SECRET_MANAGER_TOKEN=${SECRET_MANAGER_TOKEN}
SECRET_MANAGER_WORKSPACE_ID=${SECRET_MANAGER_WORKSPACE_ID}
TAG="latest"
CONTAINER_NAME="smeem"
HEALTH_CHECK_URI="/actuator/health"

echo "> Stop old version"
if [ "$(docker ps -a -q -f name=${CONTAINER_NAME})" ]; then
  docker stop ${CONTAINER_NAME}
  docker rm ${CONTAINER_NAME}
fi

echo "----------------------------------------------------------------------"

# 도커 이미지 풀 받기
echo "> Pull Docker Image"
set +x
echo "${DOCKER_PASSWORD}" | docker login -u "${DOCKER_USERNAME}" --password-stdin
set -x
docker pull "${REGISTRY_URL}"/"${IMAGE_NAME}":"${TAG}"

# 서버 실행
echo "> Run Docker"

docker run -d --name ${CONTAINER_NAME} -p 80:8080 \
  -e SECRET_MANAGER_TOKEN="${SECRET_MANAGER_TOKEN}" \
  -e SECRET_MANAGER_WORKSPACE_ID="${SECRET_MANAGER_WORKSPACE_ID}" \
  "${REGISTRY_URL}"/"${IMAGE_NAME}":${TAG}

echo "----------------------------------------------------------------------"

# green_port 서버 실행 확인
sleep 15

for retry_count in {1..15}
do
    echo "> Health check"

    response=$(curl -s http://localhost${HEALTH_CHECK_URI})
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
        docker rm -f ${CONTAINER_NAME}
        exit 1
    fi

    sleep 2
done

echo "----------------------------------------------------------------------"
