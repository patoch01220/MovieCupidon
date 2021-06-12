cd ../
# On Build les packages
echo "On build les packages"
mvn package

# On build les images docker 
echo "On build les docker images"
cd lobby-service/
echo "Entrain de build: lobby-service !"
docker build -f src/main/docker/Dockerfile.jvm -t moviecupidon/lobby-service:latest .
cd ..

cd sample-selection-service/
echo "Entrain de build: sample-selection-service !"
docker build -f src/main/docker/Dockerfile.jvm -t moviecupidon/sample-selection-service:latest .
cd ..


# On démarre docker-compose pour démarrer KONG API GATEWAY
cd docker-compose/
if [[ $(uname -r) =~ WSL ]]; then 

echo "WSL detecté"
echo "On démarre KONG API GATEWAY ET ON SLEEP 5s "
docker-compose -f docker-compose-wsl.yml up &

elif [[ $(uname) =~ Darwin ]]; then
echo "MacOS detecté"
echo "On démarre KONG API GATEWAY ET ON SLEEP 5s "
docker-compose -f docker-compose-wsl.yml up &

else
echo "On démarre KONG API GATEWAY ET ON SLEEP 5s "
docker-compose up &
fi




# On démarre les services
echo "On démarre les services"
echo "Démarre: lobby-service sur le port 8080"
docker run -i --rm -p 8080:8080 moviecupidon/lobby-service &

echo "Démarre: sample-selection-service sur le port 8081"
docker run -i --rm -p 8081:8080 moviecupidon/sample-selection-service &

sleep 10 # Nécessaire? Je pense que oui pour éviter d'exécuter les commandes curl avant que Kong soit prêt.

echo "On ajoute les paths au Kong api gateway"
# Elles étaient hard codées  ici, dans l'ideal il faudrait lancer add-services.sh 
#sh add-services.sh

# curl -XPOST "http://localhost:8001/services" --header "Content-Type: application/json" --data '
# {
#   "name": "lobby-service",       
#   "url": "http://host.docker.internal:8080/"
# }'

# curl -XPOST "http://localhost:8001/services/lobby-service/routes" --header "Content-Type: application/json" --data '
# {
#   "paths": ["/lobby"]
# }
# '

# curl -XPOST "http://localhost:8001/services" --header "Content-Type: application/json" --data '
# {
#   "name": "sample-selection-service",       
#   "url": "http://host.docker.internal:8080/"
# }'

# curl -XPOST "http://localhost:8001/services/sample-selection-service/routes" --header "Content-Type: application/json" --data '
# {
#   "paths": ["/lobby/sample-selection"]
# }
# '

sleep 20
echo "N'oubliez pas de run ./add-services.sh"
echo "Tout devrait être entrain de run!"

# ATTENTION, Il se peut que les requetes pour Kong se fassent trop tôt. Juste run add-services.sh dans ce cas.
