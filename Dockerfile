FROM tomcat:9-alpine

COPY src /build/src
COPY pom.xml /build

RUN apk add --no-cache --virtual .build_deps \
	maven \
	openjdk8 \
    && cd /build \
    && mvn package \
    && cp target/relation-analyzer.war /usr/local/tomcat/webapps \
    && rm -rf /build /root/.m2 \
    && apk del .build_deps
