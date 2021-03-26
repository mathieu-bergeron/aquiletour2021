# Les id

1. Plan de cours
    * `/cours/mathieu.bergeron/4203C6MO/`                         
    * identifiant unique du prof
    * identifiant unique du cours

1. Cours-groupe (ou Groupe)
    * `/groupes/mathieu.bergeron/A2021/4203C6MO/01`
    * prof
    * cours
    * session
    * groupe
    * Path: `mathieu.bergeron/A2021/4203C6MO/01`
    * Collection: GroupCourse
    * Id `mathieu.bergeron_A2021_4203C6MO_01`     (path.toFileName())

1. Cours (ou Session)
    * `/sessions/mathieu.bergeron/A2021/4203C6MO`
    * Collection: Course
    * Id `mathieu.bergeron_A2021_4203C6MO`

1. Et le matériel de cours?
    * c'est dans le plan de cours
    * `/cours/mathieu.bergeron/4203C6MO/etape1/module1.2/atelier`
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
