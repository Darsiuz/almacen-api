#!/bin/bash
set -a
export $(grep -v '^#' .env | xargs)
set +a

./mvnw spring-boot:run