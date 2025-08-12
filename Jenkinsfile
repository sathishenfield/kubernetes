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
        stage("Build Docker Image") {
                    steps {
                        script {
                            def imageName = "my-spring-app"
                            def imageTag = "${env.GIT_COMMIT.take(7)}"

                            // Build Docker image
                            sh "docker build -t ${imageName}:${imageTag} ."

                            // Optionally push to Docker registry (uncomment and set credentials)
                            // sh "docker login -u <username> -p <password>"
                            // sh "docker push ${imageName}:${imageTag}"
                        }
                    }
        }
    }
}