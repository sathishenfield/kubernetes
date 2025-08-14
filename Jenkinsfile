pipeline {
    agent any
    tools {
       gradle  "gradle"
    }
    environment {
       IMAGE_NAME = "my-spring-app"
       IMAGE_TAG = "${env.GIT_COMMIT.take(7)}"
//        DOCKER_USER = credentials('dockerhub-creds')
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
//         stage("Build Docker Image") {
//             steps {
//               script {
//                 sh "docker build -t ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} ."
//               }
//             }
//         }
        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                   sh "docker build -t ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG} ."
                   sh "echo ${DOCKER_PASS} | docker login -u ${DOCKER_USER} --password-stdin"
                   sh "docker push ${DOCKER_USER}/${IMAGE_NAME}:${IMAGE_TAG}"
                }
            }
        }
    }
}