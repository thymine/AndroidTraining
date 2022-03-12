pipeline {
    agent any
    environment {
        CLASS_FILE_PATH = 'app/src/main/java/me/zhang/laboratory/ui/MainActivity.java'
    }
    stages {
        stage('Build') {
            steps {
                sh 'echo "Hello World"'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
                sh "echo '${CLASS_FILE_PATH}'"
            }
        }
        stage('get_commit_msg') {
            steps {
                script {
                    env.GIT_COMMIT_MSG = sh (script: 'git shortlog -sen ${CLASS_FILE_PATH}', returnStdout: true).trim()
                }
//                 sh '"git shortlog -sen ${CLASS_FILE_PATH}"'
                echo "${GIT_COMMIT_MSG}"
//                    echo "${GIT_COMMIT}"
//                    sh 'pwd'
            }
        }
    }
    post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
        }
        failure {
            echo 'This will run only if failed'
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}