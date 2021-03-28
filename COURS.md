# Point de vue étudiant

## Groupe

1. Le calendrier
1. Pour chaque activité on peut:
    * cocher pour dire qu'on a terminé
    * OU: saisir une ligne (p.ex l'URL d'un dépôt Git)

1. Pour les tâches avec dépôt Git, on va afficher un lien 
   vers la page affichant la progression Git

# Point de vue prof

NOTE: pour «faire du ménage» on peut:

1. Effacer les cours-groupes plus vieux que X
1. Effacer les plans de cours inutilisés et plus vieux que X

### Cours

1. La structure du cours
1. Dates abstraites: semaine 10, séance 2
1. Descriptions des activités
    * obligatoire Vs bonus
    * type d'évaluation (formative, théorie, pratique)
    * 

1. Doit tout de suite avoir le code exact du cours
1. Créer un nouveau plan de cours 
    * à partir d'un plan de cours existant (copie)
    * à neuf

1. Importation/exportation au format `.json` ou `.csv`

NOTE:

* On pourrat générer des fragments de plan de cours:
    * calendrier du cours
    * tableau des évaluations

### Groupe

1. Instantiation d'un plan de cours
1. Spécifie:
    * la session, p.ex. A2021 (avec un choix de session existante)
    * la listes des étudiants (obtenur du CSV)
    * les séances:
        * séance 1: mardi 14-16h
        * séance 2: vendredi 8h-12h

NOTE:

* On pourrait générer un plan de cours plus détaillé

### Session

1. Modèle créer automatiquement avec la création d'un cours-groupe
1. Résumé des résultats pour tous les groupes d'un même cours, même session