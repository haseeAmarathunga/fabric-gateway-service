# fabric-gateway-service
Hyperledger fabric service is used as the gateway between hyperledger blockchain network and the client applications.

> ### Docker Instructions
> 1. docker build -t fabric-gateway-service:v1 .
> 
> 2. docker run -d -p 6060:6060 -v /home/usresol/hyperledger/fabric/config:/home/usresol/hyperledger/fabric/config --name fabric-gateway-service fabric-gateway-service:v1