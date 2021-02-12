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

        $ sudo apt-get install openjdk-8-jdk

1. Configurer `~/.bash_profile`:

        $ EDITEUR ~/.bash_profile

            export JAVA_HOME="/c/Program Files/Java/jdk1.8.XXX"
            export PATH=$JAVA_HOME/bin:$PATH

        # OÙ jdk1.8.XXX est ma version de JDK

1. Doit afficher `java version "1.8.XXX"`

        $ java -version
        $ javac -version

1. Installer node.js et npm

        $ sudo apt-get install npm

1. Installer TypeScript

        $ sudo npm install -g typescript

1. Installer Docker

        $ sudo apt-get update
        $ sudo apt-get install \
            apt-transport-https ca-certificates curl \
            gnupg-agent software-properties-common dirmngr wget
        $ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
        $ sudo add-apt-repository \
           "deb [arch=amd64] https://download.docker.com/linux/ubuntu \
           $(lsb_release -cs) stable"
        $ sudo apt-get update
        $ sudo apt-get install docker-ce docker-ce-cli containerd.io
        $ sudo docker run hello-world

1. Installer Docker-compose

        $ sudo curl -L "https://github.com/docker/compose/releases/download/1.28.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
        $ sudo chmod +x /usr/local/bin/docker-compose

1. Optionnel: Installer Gradle

        $ sudo add-apt-repository ppa:cwchien/gradle
        $ sudo apt-get update
        $ sudo apt-get install gradle

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
