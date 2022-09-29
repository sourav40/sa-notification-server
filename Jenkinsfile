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
                         sh 'docker build -t sourav40/notification-email-service .'
                      }
     }
     }

            stage('Push Docker Image To Docker Hub') {
                   steps {
                           script{
//                            sh 'docker tag notification-email-service:latest sourav40/notification-email-service:latest'
                           sh 'docker login -u sourav40 -p dckr_pat_9_M2GkzkEBiGSyUJf4JVjXt_PPE'
                           sh 'docker push sourav40/notification-email-service:latest'
                }
          }
          }
}
}