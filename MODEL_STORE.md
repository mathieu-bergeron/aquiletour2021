# 1 seul modelStore() par thread pour éviter que deux threads accède au même objet modèle

1. Comme pour messageService()
1. Et comme pour messageService(), on va faire un reset() sur le modelStore à la fin de la requête


# Le modelStore mémorise les modèles

1. Il faut mémoriser le documentPath du modèle si on veut le sauvegarder
1. Il faut mémoriser le modèle pour appeler InvokeValueMethod
    * quand on reçoit un InvokeValueMethod message il faut avoir des références
      vers les modèles «actifs»



# Un modelStore qui sert de cache à un autre

<pre>
ModelStore localStore = new LocalStoreFiles();

localStore.actAsCacheFor(networkStore);

</pre>




