# Deux onglets

1. Encore mieux que le champ description, deux onglets
    * |Aperçu|Texte|
    * Dans le champ texte, on peut mettre des liens vers les tâches préalable n'importe où

        {préalable /Etape1/Examen1}      # ajout automatique d'un lien

    * Utiliser l'aperçu générer un texte


# Champ description

1. Dans le champ optionnel `description`, on peut ajouter du code pour spécifier les options

1. P.ex. dans une tâche:

        SVP copier l'URL de votre dépôt git: {urlDepotGit}


    *  pour une tâche, chaque paramètres ajoute une sous-tâche que l'étudiant doit
       faire afin de réaliser la tâche. P.ex. copier le dépôt Git

1. Pour une remise:

        {remiseGit}     # valeurs par défaut

        OU

        {remiseGit {/ateliers/atelier1} {atelier1}}   # spécifier le exerciceFolder et le completionKeywords


1. Dans la description d'une billetterie

    {afficherHeures: non}
    {ouvertATous: oui}

