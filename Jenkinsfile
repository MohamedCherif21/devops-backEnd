pipeline {
    agent any
    stages {
        stage('Récupération du code de la branche OlfaAyari') {
            steps {
                script {
                    def gitBranch = 'OlfaAyari'
                    checkout([$class: 'GitSCM', branches: [[name: "refs/remotes/origin/${gitBranch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], userRemoteConfigs: [[url: 'https://github.com/MohamedCherif21/devops-backEnd.git']]])
                }
            }
        }
        stage('Lancement de la commande Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }
    }
}
