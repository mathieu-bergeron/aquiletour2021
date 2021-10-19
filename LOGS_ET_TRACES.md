# Où stoquer les logs

Un répertoire `_dev` qui contient un paquet de logs et de fichier de traçage.

Par défaut, on va écrire dans un fichier:

* `Jj.trace()`: dans le fichier `trace`
* `Jj.here()`: dans le fichier `here`
* `Jj.log()`: dans le fichier `log`

## En JSweet

On va plutôt stoquer dans du LocalStorage et afficher les données dans une page très simple

# Tracer l'exécution du graphe

* Chaque graphe de tâche devrait être sauvegardé dans un répertoire `.mon_graphe` (un paquet de fichier `.dot`)
* En Jdk, un script va transformer en fichier `.png` afin de visualiser la trace

# Enregistrer les messages

* On pourrait aussi automatiquement sauvegarder les messages envoyés et reçus.

# En Prod

* Les traces sont simplement ignorés (même chose pour le traçage du graphe)
