# Projet-UML-JAVA

Bonjour ce projet est une application Java de clavardage, créé par moi (Anouar Akkar) et Amine Alami deux étudiants de l'INSA Toulouse.

Je me suis chargé de la partie TCP,BDD et implémentation dans l'Interface, Amine s'est chargé d'UDP et la création de l'interface(et l'implémentation d'UDP).

Cette application est décentralisée et permet de discuter dans un réseau local. Vous pourrez récupérer vos anciens messages grâce à une base de données local.

IMPORTANT Il faut créer un dossier DataBase là où se trouve le pom.xml pour le fonctionnement de la base de données.

Les ports 8060 et 1769 doivent être libre dans le cas contraire il vous faudra changer le code et mettre un port libre. (UDP_PORT dans UDP pour 8060 et WelcomeControler ligne 101 ainsi que StartSession ligne 27 pour 1769 TCP)

Pour lancer cette application sous Ubuntu il vous faut les dernières versions de Java (JDK) et Maven (Fonctionnement a vérifier pour les autres versions)

Si vous êtes a l'INSA et/ou Maven n'est pas installés il vous faut le télécharger et l'ajouter dans le PATH.

Pour lancer l'application il vous faut vous placer là où se trouve le pom.xml.

Les commandes qu'on a utilisées afin de le lancer sont les suivantes :
    - export PATH=/home/nomUtilisateur/maven/bin:$PATH (à changer en fonction de l'emplacement de votre maven)
    - mvn compile && mvn exec:java -Dexec.mainClass="Interface.App"
    
Il est aussi possible de lancer avec Java en avec le main qui est dans le dossier Interface App.java cependant il vous faudra installer JavaFX. (A tester).

Une fois dans l'application il vous suffit de choisir un pseudo, cliquer sur un utilisateur pour lancer une conversation et envoyer un message en cliquant sur Send. 

Le pseudo est limité à 15 caractères.
Afin d'éviter des problèmes avec la base de données vous ne pourrait pas utiliser des pseudos avec des caractères spéciaux ni commencer par un chiffre. (C'est géré).
