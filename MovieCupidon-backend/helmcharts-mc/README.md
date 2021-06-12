# Local Deployment

call script

    cd scripts/
    ./microk8s_local_k8s.sh namespace_name

Il est normal qu'il y aie un message d'erreur du type "service already exists..."
Le script a bien marché si à la fin on voit des messages pour l'url (message standard)

view pods
    kubectl get all -o wide -n **nom du namespace**

# Remote Deployment -> VM UNIGE

call script

    cd scripts/
    ./microk8s_local_k8s.sh namespace_name

Il est normal qu'il y aie un message d'erreur du type "service already exists..."
Le script a bien marché si à la fin on voit des messages pour l'url (message standard)

Pour le remote, il faut créer les secrets **PLUS BESOIN**

    cd tls-secrets
    kubectl create secret tls tls-secret      --key movie.graved.ch.key     --cert fullchain.cer -n namespace_name
    kubectl edit  -n ingress daemonset.apps nginx-ingress-microk8s-controller
    ajoutez "--default-ssl-certificate=namespace_name/tls-secret" dans la partie args du containers dans spec
    
