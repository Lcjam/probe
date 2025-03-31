pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'ijamiei779@gmail.com'
        DOCKER_CREDENTIALS = 'lcjam'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Backend Build & Test') {
            steps {
                dir('.') {
                    sh './gradlew clean build test'
                }
            }
            post {
                always {
                    junit '**/build/test-results/test/*.xml'
                }
            }
        }

        stage('Frontend Build & Test') {
            steps {
                dir('my-probe') {
                    sh 'npm install'
                    sh 'npm run test -- --watchAll=false'
                    sh 'npm run build'
                }
            }
        }

        stage('Build Docker Images') {
            steps {
                script {
                    // 백엔드 이미지 빌드
                    docker.build("${DOCKER_REGISTRY}/probe-backend:${BUILD_NUMBER}", "-f Dockerfile .")
                    
                    // 프론트엔드 이미지 빌드
                    docker.build("${DOCKER_REGISTRY}/probe-frontend:${BUILD_NUMBER}", "-f my-probe/Dockerfile my-probe")
                }
            }
        }

        stage('Push Docker Images') {
            steps {
                script {
                    docker.withRegistry("https://${DOCKER_REGISTRY}", DOCKER_CREDENTIALS) {
                        // 백엔드 이미지 푸시
                        docker.image("${DOCKER_REGISTRY}/probe-backend:${BUILD_NUMBER}").push()
                        docker.image("${DOCKER_REGISTRY}/probe-backend:${BUILD_NUMBER}").push('latest')
                        
                        // 프론트엔드 이미지 푸시
                        docker.image("${DOCKER_REGISTRY}/probe-frontend:${BUILD_NUMBER}").push()
                        docker.image("${DOCKER_REGISTRY}/probe-frontend:${BUILD_NUMBER}").push('latest')
                    }
                }
            }
        }
    }

    post {
        always {
            // 작업 완료 후 정리
            cleanWs()
        }
        success {
            // 빌드 성공시 알림 (선택사항)
            echo 'Build succeeded!'
        }
        failure {
            // 빌드 실패시 알림 (선택사항)
            echo 'Build failed!'
        }
    }
} 