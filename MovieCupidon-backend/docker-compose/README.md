## Prérequis
Vous devez avoir:
- maven
- docker
- docker-compose

> **_NOTE:_**  Vous devez être dans le dossier docker-compose du projet.

> **_NOTE:_**  Ces scripts ont été conçu et testé sous ubuntu 20.04

## Démarrer le projet
Pour compiler, build les docker images et les lancer exécutez le fichier build_run.sh avec la commande: 

```shell script
./build_run.sh
```

> **_NOTE:_**  Ne fermez pas la console depuis laquelle vous avez run le script!

Ensuite on ajoute les paths à l'API gateway en exécutant le script add-services.sh

```shell script
./add-services.sh
```
> **_NOTE:_**  Attendez que Kong soit prêt avant d'exécuter ce script. 


## Arrêter et supprimer les images
Pour arrêter les programes et supprimer toutes les images et containers docker du système, exécutez:
```shell script
./kill_all.sh
```

> **_ATTENTION:_**  Ce script va tuer et supprimer **TOUTES** les images et containers du système. Si vous voulez éviter cela vous devrez arreter et supprimer manuellement les images/containers.


## Services
L'api gateway se trouve sur http://localhost:8000 . Les requêtes d'ordre admin doivent être faites sur http://localhost:8001 .

Le service lobby-service peut être atteint directement sur http://localhost:8080 , ou à travers l'api gateway sur http://localhost:8000/lobby .

Le service sample-selection peut être atteint directement sur http://localhost:8080 , ou à travers l'api gateway sur http://localhost:8000/lobby/sample-selection .



> **_NOTE:_**  Si vous souhaitez modifier les paths de l'api gateway il faudra modifier les requêtes curl dans build_run.sh et add-services.sh

> **_NOTE:_**  Si vous souhaitez modifier les ports d'accès direct à chaque service il faudra modifier la commande docker run dans build_run.sh . Et dans ce cas modifier aussi les ports associés au service dans l'api gateway.