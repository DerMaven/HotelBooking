FROM ubuntu:latest
LABEL authors="mironnaidanov"

ENTRYPOINT ["top", "-b"]