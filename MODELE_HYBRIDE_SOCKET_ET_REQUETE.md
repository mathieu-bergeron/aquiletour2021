# Modèle hybride

1. AquiletourMain charge les contrôleurs selon le Path. Il faut recharger l'App Ntro quand on change de page.
1. On installe et désinstalle des formAutoSubmit en utilisant onContextChange:
    1. Quand le socket est actif, on installe des formAutoSubmit
    1. Si le socket meurt, on retire les formAutoSubmit et la page va se recharger au prochain clic
