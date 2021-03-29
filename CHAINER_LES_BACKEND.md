# Chaîner les Backend

        serveur  |  client
        BE0 ------- BE1 ------- FE


1. On veut un Backend sur le client pour :
    * afficher les modifications au modèle AVANT de faire la requête au serveur
    * garder une copie en cache du modèle

1. Il faut:
    * exécuter le même code Backend, mais sur le client
    * le BE1 modifie uniquement les modèles auxquels il a accès

1. Propager et valider les modifications
    * Pour chaque BackendMessageHandler:
        * le BE1 garde en mémoire la liste de message InvokeValueMethodMessage
        * le BE1 propage le message vers le BE0
        * le BE0 exécute son code et envoie des messages InvokeValueMethodMessage au BE1
        * le BE1 valide que le BE0 a fait les mêmes modifications au modèle
        * SI LES MODIFICATIONS DIVERGENT
            * le BE1 demande le modèle au complet
            * on affiche la nouvelle version du modèle (possiblement en avertissant l'usager)


1. Version de modèle
    * chaque modèle a un numéro de version (un long)
    * quand on demande un modèle au BE, on donne aussi le numéro de version du modèle qu'on a en cache
        * si on est à jour, le BE répond tout simplement: garde ton modèle
    * quand on transmet des InvokeValueMethodMessage, on donne aussi le numéro de version
        * si le BE refuse nos InvokeValueMethodMessage, il répond avec un PutModelMessage (i.e. il répond en nous demandant de mettre à jour le modèle au complet)


## Message à l'usager

1. Le serveur contient une version différente de cette page: [Utiliser ma version] [Utiliser la version du serveur]
