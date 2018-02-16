pipeline {
    agent any

    tools {
        jdk 'JDK 8'
        maven 'Default'
    }

    options {
        buildDiscarder(logRotator(numToKeepStr: '6'))
    }

    stages {
        stage ('Git checkout') {
            steps {
                deleteDir()
                checkout scm
            }
        }

        stage ('Artifactory') {
            steps {
                script {
                    def server = Artifactory.server('1')
                }
            }
        }

        stage ('Maven Package') {
            steps {
                bat 'mvn clean package'
            }

            /*post {
                always {
                    //path du junit
                }
            }*/
        }

        //Partie en parallèle avec Sonar et Pmd
        stage ('Code checking & Analysis') {
            parallel {
                stage ('Sonar') {
                    withSonarQubeEnv('My SonarQube Server') {
                        // requires SonarQube Scanner for Maven 3.2+
                        bat 'mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.2:sonar'
                    }
                }
                stage ('Pmd') {
                    bat 'vendor/bin/phpmd . xml build/phpmd.xml --reportfile build/logs/pmd.xml --exclude vendor/ || exit 0'
                    pmd canRunOnFailed: true, pattern: 'build/logs/pmd.xml'
                }
            }
        }


        stage ('Deploy artifact to Artifactory') {
            steps {
                bat 'mvn clean deploy'
                /*def rtMaven = Artifactory.newMavenBuild()
                rtMaven.deployer server: server, releaseRepo: 'libs-release-local', snapshotRepo: 'libs-snapshot-local'
                rtMaven.resolver server: server, releaseRepo: 'libs-release', snapshotRepo: 'libs-snapshot'*/
            }
        }
    }
}