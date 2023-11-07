pipeline {
    agent any
    stages {
        stage('Git branche Olfa') {
         steps {
                echo 'Getting Project From Git';
                git branch: 'OlfaAyari',
                url : 'https://github.com/MohamedCherif21/devops-backEnd.git';
                }
         }
        stage('Lancement de la commande Maven') {
            steps {
                sh 'mvn clean compile'
            }
        }
    }
}
