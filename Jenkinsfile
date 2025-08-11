pipeline {
    agent any
    tools {
       gradle  "gradle"
    }
    stages{
        stage("Build Gradle Test"){
            steps {
            checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sathishenfield/kubernetes/']])
            sh 'chmod +x gradlew'
            sh './gradlew test'
            }
        }
        stage("Build Gradle "){
            steps {
            checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sathishenfield/kubernetes/']])
            sh 'chmod +x gradlew'
            sh './gradlew build -x test'
            }
        }
            }
}