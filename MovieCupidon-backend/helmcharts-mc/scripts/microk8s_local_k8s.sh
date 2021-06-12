microk8s kubectl create namespace $1
microk8s enable ingress
helm upgrade --install moviecupidon-microk8s ./../moviecupidon-helm-microk8s/ -f ./../moviecupidon-helm-microk8s/values.yaml --namespace $1 
helm upgrade moviecupidon-microk8s ./../moviecupidon-helm-microk8s/ -f ./../moviecupidon-helm-microk8s/values.yaml --namespace $1 