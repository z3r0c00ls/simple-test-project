// -*- mode: groovy -*-
// vim: set filetype=groovy :

pipeline {
    agent any
    tools {
        maven "Maven"
    }
    environment {
        SONNAR_HOME = "/var/jenkins_home/sonar-scanner/sonar-scanner-4.7.0.2747-linux"
    }

    stages {
        stage('Build/Test') {
            steps {
                echo 'Testing - Coverage Test (Unit/IT)'
                sh "mvn clean install"
                echo 'SonarQube'
                def scannerHome = tool 'sonarqubeserver';
                withSonarQubeEnv(credentialsId: 'JenkinsTokenSonar', installationName: 'sonarqubeserver') {
                    sh "$SONNAR_HOME/bin/sonar-scanner -Dproject.settings='sonar-project.properties'"
                }
            }
        }
    }

}
