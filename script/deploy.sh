#!/bin/bash
source .env

REGISTRY_URL=${REGISTRY_URL}
IMAGE_NAME=${IMAGE_NAME}
SECRET_MANAGER_TOKEN=${SECRET_MANAGER_TOKEN}
SECRET_MANAGER_WORKSPACE_ID=${SECRET_MANAGER_WORKSPACE_ID}
TAG="latest"
CONTAINER_NAME="smeem"
HEALTH_CHECK_URI="/actuator/health"

echo "> Pull docker image"
sudo docker pull "${REGISTRY_URL}"/"${IMAGE_NAME}":"${TAG}"

echo "> Stop running docker container"
if [ "$(sudo docker ps -a -q -f name=${CONTAINER_NAME})" ]; then
  sudo docker stop ${CONTAINER_NAME}
  sudo docker rm ${CONTAINER_NAME}
fi

echo "> Run docker"
sudo docker run -d --name ${CONTAINER_NAME} -p 80:8080 "${REGISTRY_URL}"/"${IMAGE_NAME}":${TAG} \
  -e SECRET_MANAGER_TOKEN="${SECRET_MANAGER_TOKEN}" \
  -e SECRET_MANAGER_WORKSPACE_ID="${SECRET_MANAGER_WORKSPACE_ID}"

echo "----------------------------------------------------------------------"

sleep 15
for RETRY_COUNT in {1..15}
do
    echo "> Health check"

    RESPONSE=$(curl -s http://localhost${HEALTH_CHECK_URI})
    # shellcheck disable=SC2126
    UP_COUNT=$(echo "${RESPONSE}" | grep 'UP' | wc -l)

    if [ "${UP_COUNT}" -ge 1 ]
    then
        echo "> Success"
        break
    else
        echo "> Not run yet"
        echo "> 응답 결과: ${RESPONSE}"
    fi
    if [ "${RETRY_COUNT}" -eq 15 ]
		then
        echo "> Failed to running server"
        sudo docker rm -f ${CONTAINER_NAME}
        exit 1
    fi
    sleep 2
done
echo "----------------------------------------------------------------------"
