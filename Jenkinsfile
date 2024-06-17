pipeline {
    agent any

    environment {
            AWS_DEFAULT_REGION = 'ap-northeast-2'
            S3_BUCKET = 'hyejin-static-website-bucket'
            JAR_FILE = 'build/libs/apidemo-0.0.1-SNAPSHOT.jar'
            APP_NAME = 'apidemo'
            DEPLOY_GROUP = 'apidemo-groupg'
            DEPLOY_ZIP = 'deployment.zip'
        }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/chhyejin/apidemo.git', credentialsId: 'chhyejin'
            }
        }
        stage('Build') {
            steps {
                echo 'Building...'
                sh 'chmod 755 ./gradlew'
                sh './gradlew build'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing...'
                // test steps
            }
        }
        stage('Prepare Deployment Package') {
                    steps {
                        echo 'Preparing deployment package...'
                        sh """
                        cd deployment
                        zip -r ${env.DEPLOY_ZIP} appspec.yml scripts/
                        mv ${env.DEPLOY_ZIP} ../
                        cd ..
                        zip -r ${env.DEPLOY_ZIP} -g build/libs/apidemo-0.0.1-SNAPSHOT.jar
                        """
                    }
                }
                stage('Upload to S3') {
                    steps {
                        withAWS(credentials: 'hyejin') {
                            s3Upload(bucket: env.S3_BUCKET, file: env.DEPLOY_ZIP)
                        }
                    }
                }
        stage('Deploy') {
            steps {
                echo 'Deploying...'
                 withAWS(credentials: 'hyejin') {
                  sh """
                     aws deploy create-deployment \
                         --application-name ${env.APP_NAME} \
                         --deployment-group-name ${env.DEPLOY_GROUP} \
                         --s3-location bucket=${env.S3_BUCKET},key=${env.DEPLOY_ZIP},bundleType=zip
                     """
//                                     sh 'aws s3 cp build/libs/apidemo-0.0.1-SNAPSHOT.jar s3://hyejin-static-website-bucket/'
                 }
            }
        }
    }
}