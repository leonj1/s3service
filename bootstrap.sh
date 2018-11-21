#!/bin/bash

HTTP_SERVER_PORT=${HTTP_SERVER_PORT:=3455}
AWS_KEY=${AWS_KEY:-}
AWS_SECRET=${AWS_SECRET:=}
S3_ENDPOINT=${S3_ENDPOINT:=}

java -jar /s3service.jar \
    --http.port=${HTTP_SERVER_PORT} \
    --aws_ses_access_key=${AWS_KEY} \
    --aws_ses_secret_key=${AWS_SECRET} \
    --s3.endpoint=${S3_ENDPOINT}
