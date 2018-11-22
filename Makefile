ifndef VERSION
  VERSION=$(shell git rev-parse --short HEAD)
endif

DOCKER_REPO=www.dockerhub.us
DOCKER_IMAGE=s3service

compile:
	mvn clean package

docker: compile
	docker build -t ${DOCKER_REPO}/${DOCKER_IMAGE}:$(VERSION) .

publish: docker
	docker push ${DOCKER_REPO}/${DOCKER_IMAGE}:$(VERSION)

