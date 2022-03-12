pipeline {
    agent any
    environment {
        CLASS_FILE_PATH = 'app/src/main/java/me/zhang/laboratory/ui/MainActivity.java'
    }
    stages {
        stage('get_commit_msg') {
            steps {
                script {
                    env.GIT_COMMIT_MSG = sh (script: 'git shortlog HEAD -sen ${CLASS_FILE_PATH}', returnStdout: true).trim()
                }
                echo "${GIT_COMMIT_MSG}"
            }
        }
    }
}