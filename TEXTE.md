# Chaque tâche a un Texte qui contient ses données

1. L'utilisateur avisé peut éditer le texte directement

1. Pour débuter, on va supporter uniquement description (pour inclure remise Git)

1. Le texte contient les données pour la tâche, mais
   aussi permet d'ajouter un énoncé 

1. On vise une syntaxe similaire à Markdown, mais avec des sections imbriquables {}

1. Syntaxe
    * {<texte> : <valeur>}         # définition d'une valeur
    * {<valeur>}                   # évaluation d'une valeur
    * {<fonction> <arg1> <arg2>}   # appel de fonction
    * { <texte>}                   # texte inerte à cause de l'espace après {

1. Seule la {structure: } est obligatoire. Le reste est facultatif

{tâche:

    {titre: Travail pratique #1}

    {identifiant: TP1}

    {description:

        SVP copier l'URL de votre dépôt Git {remise dépôtGit}
    }

    {préalables:

        * {lien {Atelier 1} {/Étape 1/Atelier 1}}
        * {lien {Atelier 2} {/Étape 1/Atelier 2}}
    }

    {sous-tâches:

        * {lien Vue {/Étape 1/Atelier 1/Vue}}
        * {lien Vue {/Étape 1/Atelier 1/Modele}}
    }

    {tâches suivantes: 

        * {lien {Étape 2} {/Étape 2}}
    }
}

{texte:

    {en-tête:

        420-3C6-MO {prof}

    }

    {corps:

        # {titre}

        {structure}


        ## Étapes

        {étapes:                                                     {# produit <div id="étapes">CONTENU</div> #}

            {sous-tâches}           
        }

        ## Remise

        1. Je m'assure d'avoir terminer {lien {les étapes} étapes}   {# produit <a href="#étapes">les étapes</a> #}

        {description}

        ## À faire ensuite

        {afficher {tâches suivantes}}                        {# le afficher rend ça plus clair, même si {{tâches suivantes}} ferait la même chose  #}

    }

    {pied de page:

        {afficher {numéro de page}}
    }
}


# Syntaxe additionnelle

1. Commentaire:  {# commentaire #}
1. Pour une fonction
    * {<texte> {<arg1>} {<arg2>} {<arg3>} : <valeur>}    # définition d'une fonction

{ question01 {fichier} {séparateur}:

    1. Ouvrir le {fichier} et chercher chaque {séparateur} avec {kbd {CTRL+F}}.
}

{# Utiliser la fonction #}              # Ceci est un commentaire

{ question01 {`test.csv`} {`#`} }       # Les valeurs sont du texte (qui peuvent comprendre d'autres fonctions)


# Syntaxe pour table (enfin!)

{# ordonné par les rangées #}

{table 

    {rangée 

        {cellule {Bonjour, je suis une *cellule*}}
        {cellule {Deuxième `cellule`}}
    }
    {rangée 

        {cellule { cellule *B1*}}     {# XXX: { cellule ... l'espace clarifie que ce n'est pas un appel à la fonction cellule #}
        {cellule { cellule `B2`}}
    }
}

{# ordonné par les colonnes #}

{table 

    {colonne

        {cellule {Bonjour, je suis une *cellule*}}
        {cellule {Deuxième `cellule`}}
    }

    {colonne

        {cellule { cellule *B1*}}
        {cellule { cellule `B2`}}
    }
}
