# Les id

1. Plan de cours
    * `/cours/mathieu.bergeron/3C6/`                         
    * identifiant unique du prof
    * identifiant unique du cours

1. Groupes
    * `/groupes/mathieu.bergeron/3C6/<session>/01`
    * prof
    * cours
    * session (session courante si pas spécifié)
    * groupe
    * Path: `mathieu.bergeron/3C6/01`
    * Collection: Group
    * Id `mathieu.bergeron_3C6_A2021_01`     (path.toFileName())

1. Sessions (tous les groupes)
    * `/sessions/mathieu.bergeron/3C6/<session>`
    * session (session courante si pas spécifié)
    * Collection: Groups
    * Id `mathieu.bergeron_3C6_A2021`

1. Et le matériel de cours?
    * c'est dans le plan de cours (?)
    * `/cours/mathieu.bergeron/3C6/etape1/module1.2/atelier`
    * identifiant du plan de cours
    * le reste est l'id de la tâche: `/etape1/module1.2/atelier`

1. Et le dashboard?
    * Overview (ou Dashboard)
    * `/survol` ou `/apercu`
    * `/survol/mathieu.bergeron/A2021` 
    * OU
    * `/sessions/mathieu.bergeron/A2021` affiche le survol pour la session `A2021`
    * `/sessions/mathieu.bergeron` affiche le survol pour la session en cours
    * OU
    * `/mathieu.bergeron` affiche le dashboard pour `mathieu.bergeron`, pour la session en cours
