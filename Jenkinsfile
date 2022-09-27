pipeline {

   agent any

      stages {

         stage('Build') {

             steps {
               sh 'echo Build and compiling.'
               sh 'pwd'
               sh './mvnw clean install'
             }
}

          stage('Build Docker Image') {
              steps {
                      script{
                        sh 'docker build -t notification-email-service .'
                      }
     }
     }

            stage('Push Docker Image To Docker Hub') {
                   steps {
                           script{
                           sh 'docker tag notification-email-service:latest sourav40/notification-email-service:latest'
                           sh 'docker login -u sourav40 -p dckr_pat_7bf_hiw7IkCxUP_gDfdg-6OXfw0'
                           sh 'docker push sourav40/notification-email-service:latest'
                }
          }
          }
}
}