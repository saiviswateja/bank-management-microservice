#Deleting the current local docker images if exists
docker rmi viswateja/accounts
docker rmi viswateja/configserver
docker rmi viswateja/loans
docker rmi viswateja/cards

#Build images
cd configserver
mvn spring-boot:build-image
cd ..
cd accounts
mvn spring-boot:build-image
cd ..
cd loans
mvn spring-boot:build-image
cd ..
cd cards
mvn spring-boot:build-image

#Pushing images to the docker hub
docker tag viswateja/configserver:latest saiviswateja1412/easybytespractise:configserver
docker tag viswateja/accounts:latest saiviswateja1412/easybytespractise:accounts
docker tag viswateja/loans:latest saiviswateja1412/easybytespractise:loans
docker tag viswateja/cards:latest saiviswateja1412/easybytespractise:cards
docker push saiviswateja1412/easybytespractise:configserver
docker push saiviswateja1412/easybytespractise:accounts
docker push saiviswateja1412/easybytespractise:loans
docker push saiviswateja1412/easybytespractise:cards
