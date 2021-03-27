# Chaque tâche a un Texte qui contient ses données

1. L'utilisateur avisé peut éditer le texte directement

1. Pour débuter, on va supporter uniquement description (pour inclure remise Git)

1. Le texte contient les données pour la tâche, mais
   aussi permet d'ajouter un énoncé 

1. On vise une syntaxe similaire à Markdown, mais avec des sections imbriquables {}

1. Syntaxe
    * {<texte> : <valeur>}    # définition d'une valeur
    * {<valeur>}              # évaluation d'une valeur

1. Seule la {structure: } est obligatoire. Le reste est facultatif

{ tâche:

    { titre: Travail pratique #1 }

    { identifiant: TP1 }

    { description:

        SVP copier l'URL de votre dépôt Git {remise: {dépôtGit}}

    }

    { préalables:

        * { /Étape 1/Atelier 1 }
        * { /Étape 1/Atelier 2 }
    }

    { sous-tâches:

        * { /Étape 1/Atelier 1/Vue }
        * { /Étape 1/Atelier 1/Modele }
    }

    { tâches suivantes: 

        * { /Étape 2 }
    }
}

{ texte:

    { en-tête:

        420-3C6-MO { prof }

    }

    { corps:

        # { titre }

        { structure }


        ## Étapes

        { étapes:                                                     # produit <div id="étapes">LE TEXTE</div>

            { sous-tâches }           
        }

        ## Remise

        1. Je m'assure d'avoir terminer {lien: étapes {les étapes}}   # produit <a href="#étapes">les étapes</a>

        { description }


    }

    { pied de page:

        { numéro de page }
    }
}
