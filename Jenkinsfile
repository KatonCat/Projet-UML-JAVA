pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/KatonCat/Projet-UML-JAVA.git'
                sh './mvnw clean compile'
            }
        }
    }
}
