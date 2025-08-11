pipeline {
    agent any
    tools {
       maven "maven_3_9_6"
    }
    stages{
        stage("Build Maven Test"){
            steps {
            checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sathishenfield/kubernetes/']])
            sh 'mvn test'
            }
        }
        stage("Build Maven"){
            steps {
            checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sathishenfield/kubernetes/']])
            sh 'mvn clean install -DskipTests'
            }
        }
            }
}