docker rmi $(docker images -qa 'sachingoyaldocker/baat-org-gql_api')

./gradlew clean build bootJar
docker build --no-cache -t sachingoyaldocker/baat-org-gql_api:1.0 . 
docker push sachingoyaldocker/baat-org-gql_api:1.0
