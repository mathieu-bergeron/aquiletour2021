# [FIXME] Ntro.currentSession()

1. On a _un_ Backend pour le serveur, mais une session par thread
    * le Backend peut pas accéder directement à la session
    * plutôt, le Backend doit répondre à un message et le Frontend doit ajuster sa session


