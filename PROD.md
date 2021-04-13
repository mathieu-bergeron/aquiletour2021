# [FIXME] Ntro.currentSession()

1. On a _un_ Backend pour le serveur, mais une session par thread
    * le Backend peut pas accéder directement à la session
    * plutôt, le Backend doit répondre à un message et le Frontend doit ajuster sa session

## Solution

1. Faire un Bakcend _par thread_. 
    * il faut aussi ajuster pour que MessageService envoi le message sur le Socket s'il existe
    * (soit sur le socket, soit en Frontend local)
    * (ça prend un message service différent sur le serveur que le client)
