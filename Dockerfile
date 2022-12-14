FROM --platform=linux/arm64 arm64v8/adoptopenjdk:15-jre-hotspot as stage-arm64
FROM --platform=linux/amd64 adoptopenjdk:15-jre-hotspot as stage-amd64

ARG TARGETARCH
# Select final stage based on TARGETARCH ARG
FROM stage-${TARGETARCH} as final

WORKDIR /app

ADD ./api/target/priljubljeni-izdelki-api-1.0-SNAPSHOT.jar /app

EXPOSE 8082

CMD ["java", "-jar", "priljubljeni-izdelki-api-1.0-SNAPSHOT.jar"]
