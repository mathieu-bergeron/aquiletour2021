# TODO

1. `/mescours`:   ajout d'une étape
1. `/cours`:      réparer pour afficher les vraies données
1. `/cours`:      afficher les dates de calendrier
1. Réparer le point de vue étudiant (`/mescours`)
1. Réparer les files d'attente










# Point de vue prof

1. Tableau de bord: `/tableau_de_bord/`
    * résumé pour chaque cours (selon date)
1. Cours: `/mescours/` 
    * liste des cours
    * lien vers les tâches du cours («Éditer le calendrier du cours»)
    * résumé pour la session en cours:
        * groupe / nombre d'étudiants
        * progression
1. Tâches du cours: `/cours/`
1. Groupes: `/mesgroupes`
    * liste des cours-groupes 
    * ajouter un cours-groupe
    * inscrire des étudiants
    * spécifier l'horaire: séance 1: mardi 14h
    * lien vers le cours

1. Files d'attente: `/files_d_attente` (point de vue prof)

# Point de vue étudiant

1. Tableau de bord: `/tableau_de_bord/`
    * résumé pour chaque cours (selon date)
    * lien vers le calendrier de cours plus complet

1. Tâche d'un cours: `/cours` (mais point de vue étudiant) 
    * En fait, on partage pas le modèle
    * Chaque étudiant a son propre calendrier de cours (comme cours-groupe)

1. Files d'attente: `/files_d_attente` (point de vue prof)
    * Modèle partagé


# TODO



1. Identifiants, URLs et navigation
    * mettre toujours au long "?u=&s=" etc.
        * comme ça le prof peut faire un lien et ça marche
        * autre option: un bouton "Partager"
    * cours: "prof/code" (représenté comme un Path??)
        * coursePath Vs courseId (coursePath.toFileName())
2. Modèles Cours/Groupe/Session , Cours vu de l'étudiant
2. Modèle CourseModel vu de l'étudiant
    * aussi: comment spécialiser les tâches pour avoir des GitRepoTask et des GitCourseWorkTask
3. Création d'un cours
    * guider le prof vers un code de cours
