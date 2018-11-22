FROM anapsix/alpine-java

MAINTAINER Jose Leon <leonj1@gmail.com>

RUN apk update && \
    apk add bash bash-doc bash-completion

ADD target/s3service*SNAPSHOT.jar /s3service.jar
ADD bootstrap.sh /

ENTRYPOINT ["/bootstrap.sh"]

