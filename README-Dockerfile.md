# How to use Dockerfile

Dockerfile in this repository creates Relation Analyzer instance running on port
8080.

This image is based on Tomcat image and does not contain any database. For full
functionality (caching, search) database is required. This image supports
PostgreSQL databases. To setup the database provide following environment
variables:
* `DB_URL` - database URL in format `jdbc:postgresql://[host]/[database name]`
* `DB_USER` - database username
* `DB_PASSWORD` - database password
* `SRTM_DIRECTORY` - directory where SRTM files are available


You can build the image using command:
```
docker build -t relation-analyzer .
```

# Using docker-compose
`docker-compose.yml` provides full environment for Relation Analyzer including
database. It exposes Relation Analyzer on Docker host port 80. It creates
separate volume that will hold database data. Before relation analyzer can
start you need to create the table `relation` and populate it with data (see
`src/main/schema/test-data.sql`).

To startup your environent use:
```
docker-compose up .
```

Add `-d` option if you want the docker containers to run in background.

If you have updated any files within repository running:
```
docker-compose up --build
```
will update the images if needed and recreate containers if needed.
