@echo off
echo Stopping wigell-travel-service
docker stop wigell-travel-service
echo Deleting container wigell-travel-service
docker rm wigell-travel-service
echo Deleting image wigell-travel-service
docker rmi wigell-travel-service
echo Running mvn package
call mvn package
echo Creating image wigell-travel-service
docker build -t wigell-travel-service .
echo Creating and running container wigell-travel-service
docker run -d -p 8585:8585 --name wigell-travel-service --network wigell-network wigell-travel-service
echo Done!