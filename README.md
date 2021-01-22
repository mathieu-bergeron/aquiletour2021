# `aquiletour2021`

## Prérequis

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

* Voir le Wiki
