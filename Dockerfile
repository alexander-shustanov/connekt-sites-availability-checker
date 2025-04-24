FROM ghcr.io/amplicode/connekt:0.1

WORKDIR /connekt

COPY checkSitesAvailability.connekt.kts /connekt/.
COPY connekt.env.json /connekt/.

RUN ./bin/connekt-scripting-host --script checkSitesAvailability.connekt.kts --compile-only
ENTRYPOINT ./bin/connekt-scripting-host --script checkSitesAvailability.connekt.kts -d --env-file connekt.env.json --env-name env
