mvn clean install
docker build -t fabric-gateway-service:v1 .
docker run -p 6060:6060 -v /home/usresol/hyperledger/fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com:/home/usresol/hyperledger/fabric/fabric-samples/test-network/organizations/peerOrganizations/org1.example.com --name fabric-gateway-service fabric-gateway-service:v1