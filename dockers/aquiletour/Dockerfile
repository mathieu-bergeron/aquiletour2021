FROM alpine:3.13 as build
RUN apk update
RUN apk add openjdk8
ENV JAVA_HOME=/usr/lib/jvm/java-1.8-openjdk
ENV PATH="$JAVA_HOME/bin:${PATH}"
RUN apk add gradle
RUN apk add nodejs npm
RUN npm install -g typescript
RUN mkdir /code
WORKDIR /code
COPY . /code
RUN gradle js
RUN gradle aquiletour_server:shadowJar

FROM alpine:3.13
RUN apk update
RUN apk add openjdk8-jre
RUN apk add nodejs
RUN mkdir /app
WORKDIR /app
COPY --from=build /code/aquiletour_server/build/libs/aquiletour_server-all.jar ./aqlt_srv.jar
RUN mkdir -p src/main/resources
COPY --from=build /code/aquiletour_server/src/main/resources src/main/resources
RUN mkdir -p __data__
COPY --from=build /code/aquiletour_server/__test_data__ __data__
CMD java -cp aqlt_srv.jar ca.aquiletour.server.JavaMainServer
