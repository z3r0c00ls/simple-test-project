// -*- mode: groovy -*-
// vim: set filetype=groovy :

pipeline {
    agent any
    tools {
        maven "Maven"
    }
    environment {
        scannerHome = tool name: 'sonarqubeserver'
    }

    stages {
        stage('Build/Test') {
            steps {
                echo 'Testing - Coverage Test (Unit/IT)'
                sh "mvn clean install"
                echo 'SonarQube'
                withSonarQubeEnv(credentialsId: 'JenkinsTokenSonar', installationName: 'sonarqubeserver') {
                    // sh "$SONNAR_HOME/bin/sonar-scanner -Dproject.settings='sonar-project.properties'"
                    sh "$scannerHome/bin/sonar-scanner -Dproject.settings='sonar-project.properties'"
                }
            }
        }
    }

}
