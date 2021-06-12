echo "Attention ceci va tuer et supprimer tous les docker containers/images."
docker kill $(docker ps -q) 
docker rmi $(docker images -q)
docker rm $(docker ps -aq)