microk8s kubectl rollout restart deploy webui-deployment -n $1
microk8s kubectl rollout restart deploy lobby-deployment -n $1
microk8s kubectl rollout restart deploy sample-deployment -n $1
microk8s kubectl rollout restart deploy chat-deployment -n $1
microk8s kubectl rollout restart deploy play-deployment -n $1
