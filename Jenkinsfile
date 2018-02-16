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
                echo 'Michel'
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
        /*stage ('Code checking & Analysis') {
            parallel(
                Sonar: {

                },
                Pmd: {

                }
            )
        }*/


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