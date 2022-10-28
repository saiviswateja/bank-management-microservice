#Deleting the current local docker images if exists
docker rmi viswateja/eurekaserver
docker rmi viswateja/cloud-gateway-for-banks-application
docker rmi viswateja/accounts
docker rmi viswateja/configserver
docker rmi viswateja/loans
docker rmi viswateja/cards

#Build images
cd cloud-gateway-for-banks-application
mvn spring-boot:build-image -Dmaven.test.skip=true
# cd ..
# cd eurekaserver
# mvn spring-boot:build-image -Dmaven.test.skip=true
# cd .. 
# cd configserver
# mvn spring-boot:build-image-Dmaven.test.skip=true
# cd ..
# cd accounts
# mvn spring-boot:build-image -Dmaven.test.skip=true
# cd ..
# cd loans
# mvn spring-boot:build-image -Dmaven.test.skip=true
# cd ..
# cd cards
# mvn spring-boot:build-image -Dmaven.test.skip=true

#Pushing images to the docker hub
docker tag viswateja/cloud-gateway-for-banks-application:latest saiviswateja1412/easybytespractise:cloud-gateway-for-banks-application
# docker tag viswateja/eurekaserver:latest saiviswateja1412/easybytespractise:eurekaserver
# docker tag viswateja/configserver:latest saiviswateja1412/easybytespractise:configserver
# docker tag viswateja/accounts:latest saiviswateja1412/easybytespractise:accounts
# docker tag viswateja/loans:latest saiviswateja1412/easybytespractise:loans
# docker tag viswateja/cards:latest saiviswateja1412/easybytespractise:cards

docker push saiviswateja1412/easybytespractise:cloud-gateway-for-banks-application
# docker push saiviswateja1412/easybytespractise:eurekaserver
# docker push saiviswateja1412/easybytespractise:configserver
# docker push saiviswateja1412/easybytespractise:accounts
# docker push saiviswateja1412/easybytespractise:loans
# docker push saiviswateja1412/easybytespractise:cards
