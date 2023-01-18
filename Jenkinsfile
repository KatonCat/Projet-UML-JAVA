pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/KatonCat/Projet-UML-JAVA.git'
                sh './mvnw clean compile'
            }
        }

        stage('Test'){
            steps{
                sh './mvnw test'
            }
            post{
                always{
                    junit '**/target/surefire-reports/TEST-*.xml'
                }
            }
        }

    }
}
