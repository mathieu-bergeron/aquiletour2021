# Niveaux d'abstractions

1. Graphe de tâches 
    * dependsOn(otherTask)
    * (ré)-exécuter à chaque fois que la tâche précédente est (ré)-exécuter

1. Ajout) messages
    * triggeredBy(UnMessage.class)
    * (ré)-exécuter la tâche quand on reçoit un nouveau message

1. Ajout) notion d'objet permanent
    * observes(UnObjetPermanent.class)
    * (ré)-exécuter la tâche quand la valeur de l'objet change
    * on pousse un Revisions dans le scope pour permetter d'accéder aux modifs à l'objet


## Exemple

### Séquence d'initialisation

* Plusieurs `dependsOn` pour aller chercher les services dont on a besoin

### Un ViewModel

* observes(MonModele.class)
* observes(MaVue.class)

Chaque fois que le modèle change (ou que la vue change), on redéclenche le ViewModel

### Un ShowHandler

Un ShowHandler pourrait faire:

* dependsOn(ParentController.class)
* triggeredBy(ShowMessage.class)

C'est à dire:

* On doit attendre que la contrôleur parent est prêt
* On ré-exécute à chaque fois que le ShowMessage est reçu
