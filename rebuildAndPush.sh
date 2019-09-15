docker rmi $(docker images -qa 'sachingoyaldocker/baat-org-gqlapi')

./gradlew clean build bootJar
docker build --no-cache -t sachingoyaldocker/baat-org-gqlapi:1.0 . 
docker push sachingoyaldocker/baat-org-gqlapi:1.0
