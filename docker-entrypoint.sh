#!/bin/sh

cat >> /usr/local/tomcat/webapps/ROOT/WEB-INF/classes/config.properties << EOF
db.driverClassName=org.postgresql.Driver
db.url=jdbc:postgresql://${DB_HOST:-postgres}/${DB_NAME:-osm}
db.username=${DB_USER}
db.password=${DB_PASSWORD}
srtmDataDirectory=${SRTM_DIRECTORY}
EOF

exec catalina.sh run
