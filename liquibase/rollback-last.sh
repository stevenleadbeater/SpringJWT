#!/usr/bin/env bash
#    --logLevel=debug \
java -jar ./liquibase.jar \
    --driver=org.postgresql.Driver \
    --classpath=/usr/share/java/postgresql-9.4.1209.jar \
    --changeLogFile=./db.changelog.xml \
    --url="jdbc:postgresql://localhost:5432/gis" \
    --username=application_admin \
    --password=test123!#9845 \
    rollbackCount 1