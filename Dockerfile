FROM tomcat:9-alpine

ADD https://jdbc.postgresql.org/download/postgresql-42.0.0.jar /usr/local/tomcat/lib

COPY src /build/src
COPY pom.xml /build

RUN apk add --no-cache --virtual .build_deps \
        maven \
        openjdk8 \
    && cd /build \
    && mvn package \
    && rm -rf /usr/local/tomcat/webapps/ROOT \
    && mkdir /usr/local/tomcat/webapps/ROOT \
    && (cd /usr/local/tomcat/webapps/ROOT && jar xvf /build/target/relation-analyzer.war) \
    && rm -rf /build /root/.m2 \
    && apk del .build_deps


COPY docker-entrypoint.sh /

CMD ["/docker-entrypoint.sh"]

