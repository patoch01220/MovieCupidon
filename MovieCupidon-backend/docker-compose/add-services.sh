curl -XPOST "http://localhost:8001/services" --header "Content-Type: application/json" --data '
{
  "name": "lobby-service",
  "url": "http://host.docker.internal:8080/"
}'

curl -XPOST "http://localhost:8001/services/lobby-service/routes" --header "Content-Type: application/json" --data '
{
  "paths": ["/lobby"],
  "preserve_host": true
}
'

curl -XPOST "http://localhost:8001/services" --header "Content-Type: application/json" --data '
{
  "name": "sample-selection-service",
  "url": "http://host.docker.internal:8081/"
}'

curl -XPOST "http://localhost:8001/services/sample-selection-service/routes" --header "Content-Type: application/json" --data '
{
  "paths": ["/lobby/sample-selection"],
  "preserve_host": true
}
'
