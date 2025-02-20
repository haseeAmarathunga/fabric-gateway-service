FROM openjdk:17-jdk-alpine
WORKDIR /opt
ENV PORT 6060
ENV FABRIC_CONFIG_PATH /home/usresol/hyperledger/fabric/config
ENV CRYPTO_PATH /home/usresol/hyperledger/fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com
EXPOSE 6060
COPY target/fabric-gateway-service-v1.jar /opt/fabric-gateway-service-v1.jar
ENTRYPOINT exec java $JAVA_OPT -jar fabric-gateway-service-v1.jar