pipeline {
    agent any
    tools {
       gradle  "gradle"
    }
    environment {
       IMAGE_NAME = "my-spring-app"
       IMAGE_TAG = "${env.GIT_COMMIT.take(7)}"
       DOCKER_CREDS = credentials('dockerhub-creds')
    }
    stages{
        stage("Build Gradle Test"){
            steps {
            checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sathishenfield/kubernetes/']])
            sh 'chmod +x gradlew'
            sh './gradlew clean test jacocoTestReport check'
            }
        }
        stage("Build Gradle "){
            steps {
            checkout scmGit(branches: [[name: '*/dev']], extensions: [], userRemoteConfigs: [[url: 'https://github.com/sathishenfield/kubernetes/']])
            sh 'chmod +x gradlew'
            sh './gradlew build -x test'
            }
        }
        stage('Docker Login & Push') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'dockerhub-creds', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                   sh '''
                        docker build -t $DOCKER_USER/${IMAGE_NAME}:${IMAGE_TAG} .
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push $DOCKER_USER/${IMAGE_NAME}:${IMAGE_TAG}
                   '''
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: 'kubeconfig', variable: 'KUBECONFIG')]) {
                   sh '''
                        kubectl set image deployment/javawebappdeployment javawebappcontainer=$DOCKER_CREDS_USR/${IMAGE_NAME}:${IMAGE_TAG}
                        kubectl rollout status deployment/javawebappdeployment
                   '''
                }
            }
        }
    }
}