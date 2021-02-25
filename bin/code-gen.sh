#!/bin/bash

GENERATOR_VERSION=4.3.1
wget https://repo1.maven.org/maven2/org/openapitools/openapi-generator-cli/${GENERATOR_VERSION}/openapi-generator-cli-${GENERATOR_VERSION}.jar -O /tmp/openapi-generator-cli.jar

java -jar /tmp/openapi-generator-cli.jar generate \
-i https://gist.githubusercontent.com/jasonpaige/75124578d7e4253e898ef6e381f0be85/raw/db10f455c6622372a3d11ebef9d4f9ab82a9e9a1/bulk-scan-api-v2-poc.json \
--api-package uk.gov.hmcts.bulkscan.client.api \
--model-package uk.gov.hmcts.bulkscan.client.model \
--invoker-package uk.gov.hmcts.bulkscan.client.invoker \
--group-id uk.gov.hmcts.reform \
--artifact-id bulk-scan-client \
--artifact-version 0.0.1-SNAPSHOT \
-g spring -p java8=true --library spring-cloud \
-o .
