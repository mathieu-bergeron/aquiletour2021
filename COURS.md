# Vues

1. Planifier une session
    * ajouter une session (officielle ou personalisée)
    * ajouter des cours et créer des groupes
    * entrer l'horaire du prof

1. Vue «Cours» générique: naviguer le calendrier de cours (et on peut ajouter)
1. Vue «Dashboard» générique: liste d'items et on peut en ajouter

1. Vue «Cours» générique où
    * on a toujours un breadcrumbs
    * on a toujours un sélecteur de groupe
    * le genre de carte affichée est différent

1. Sélecteur dans le calendrier de cours
    aPlan de cours: `/cours/`
    * on a toujours un breadcrumbs
    * on a toujours un breadcrumbs
    * H2021:          `/groupes/?g=*`
    * H2021-groupe01  `/groupes/`
    * H2021-groupe02  ``
    * 1234500         `/etudiants`
    * 1234500-git     `/etudiants`

1. L'usager a l'impression de naviguer un seul calendrier
   de cours, mais en réalité on passe d'un modèle à l'autre


# Modèles

1. On a:
    * CourseModel: plan de cours
    * CourseSemester: plan de cours + info pour une session
    * CourseStudent: plan de cours + info pour un étudiant


# Modèles

1. Prof:
    1. Tableau de bord (CoursSession)
        * résumé pour tous les groupes
        * lien vers le cours
        * lien vers les groupes
        * lien vers la file d'attente
    1. Cours (plan de cours, dates abstraites: samedi de la semaine 10)
    2. Groupe (dates concrètes, 4 oct)
        * choix du groupe avec 
        * liste d'étudiants
        * option: un choix dans le CoursSession (groupe01, groupe02, tous)
    3. CoursSession 
        * (résumé de plusieurs groupes, dates abstraites)

1. Étudiant: CoursGroupe

1. Session: 
    * éditable par un SuperUser
    * calendrier de la session (semaine - jour)


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
