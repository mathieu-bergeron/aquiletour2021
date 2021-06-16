# Graphe de tâches asynchrone

1. On veut exécuter une seule tâche, puis faire un resumeLater()
    * on veut supporter le cas à un seul thread (comme un client JS)
    * ou encore exécuter un graphe de tâche non-bloquant sur Vertx

1. NOTE: la gestion des exceptions ne peut pas se faire avec des throw (à cause des lambda)
    * il faut des .execptionHandler() 

# Niveaux d'abstractions

1. Graphe de tâches 
    * dependsOn(otherTask)
    * (ré)-exécuter à chaque fois que la tâche précédente est (ré)-exécutée

1. Ajout) messages et files de messages
    * triggeredBy(UnMessage.class)
    * (ré)-exécuter la tâche quand on reçoit un nouveau message
    * si une tâche n'est pas prête, on garde le message en file

1. Ajout) notion d'objet permanent         (i.e. l'équivalent du ModelStore)
    * observes(UnObjetPermanent.class)
    * (ré)-exécuter la tâche quand la valeur de l'objet change
    * on pousse un set de Revisions dans le scope pour permetter d'accéder aux modifs apportées à l'objet depuis le dernier appel
    * NOTE: on implante avec un mélange de messages et d'un ObjetStore qui conserve en mémoire les objets permanents

## Exemples

### Séquence d'initialisation

* Plusieurs `dependsOn` pour aller chercher les services dont on a besoin au moment où on en a besoin
* Si un service change, il faut ré-exécuter toutes les tâches dépendantes

### Un ViewModel

* observes(MonModele.class)
* observes(MaVue.class)

Chaque fois que le modèle change (ou que la vue change), on redéclenche le ViewModel

NOTE: on doit prévilégier d'ajouter un Sous-Contrôleur afin d'ajouter une sous-vue.

### Un ShowHandler

Un ShowHandler pourrait faire:

* dependsOn(ParentController.class)
* triggeredBy(ShowMessage.class)

C'est à dire:

* On doit attendre que la contrôleur parent est prêt
* On ré-exécute à chaque fois que le ShowMessage est reçu
