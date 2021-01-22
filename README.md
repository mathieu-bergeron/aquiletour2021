# aquiletour.ca

## Git et branches

1. Je bascule sur ma branche

        $ git checkout MA_BRANCHE

    * les branches sont `marwane`,`joseph`,`benjamin`,`mroussel`, `nleduc`, `apilon`, `mbergeron`

1. Je vérifie que je suis sur ma branche

        $ git branch

1. Je peux importer les modifications de la branche `main`:

        $ git merge main

1. Mais je ne fais jamais:

        $ git checkout AUTRE_BRANCHE

    * La branche `main` sera gérée **uniquement** par `mbergeron`

## Configuration

1. Installer le JDK **1.8**

1. Configurer `~/.bash_profile`:

        $ EDITEUR ~/.bash_profile

            export JAVA_HOME="/c/Program Files/Java/jdk1.8.XXX"
            export PATH=$JAVA_HOME/bin:$PATH

        # OÙ jdk1.8.XXX est ma version de JDK

1. Doit afficher `java version "1.8.XXX"`

        $ java -version


## Commandes

1. Pour démarrer la version JavaFX

        $ ./gradlew javafx

1. Pour compiler vers javascript

        $ ./gradlew js

1. Pour démarrer le serveur:

        $ ./gradlew server
        
    * visiter <a href="http://localhost:8080">http://localhost:8080</a>
    * <kbd>Entrée</kbd> pour arrêter le serveur


## Documentation

* Voir le [Wiki](https://github.com/mathieu-bergeron/aquiletour2021/wiki/index)
