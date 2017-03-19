FROM tomcat:9-alpine

COPY src /build/src
COPY pom.xml /build

RUN apk add --no-cache --virtual .build_deps maven \
    && cd /build \
    && mvn package \
    && cp relation-analyzer.war $CATALINA_BASE/webapps
    && apk del .build_deps
