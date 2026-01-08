pipeline {
    agent any
    
    tools {
        maven 'Maven'
        jdk 'Java'
    }
    
    parameters {
        choice(
            name: 'BROWSER',
            choices: ['chrome', 'firefox', 'edge'],
            description: 'Select browser for test execution'
        )
        choice(
            name: 'ENVIRONMENT',
            choices: ['dev', 'staging', 'prod'],
            description: 'Select environment to run tests'
        )
        choice(
            name: 'HEADLESS',
            choices: ['false', 'true'],
            description: 'Run browser in headless mode'
        )
        string(
            name: 'TAGS',
            defaultValue: '@adminlogin',
            description: 'Cucumber tags to execute (e.g., @smoke, @regression, @admin, @adminlogin)'
        )
    }
    
    environment {
        BROWSER = "${params.BROWSER}"
        HEADLESS = "${params.HEADLESS}"
        ENV = "${params.ENVIRONMENT}"
        CUCUMBER_TAGS = "${params.TAGS}"
        BASE_URL = getBaseUrl("${params.ENVIRONMENT}")
    }
    
    stages {
        stage('Checkout') {
            steps {
                echo '=========================================='
                echo 'Checking out code from repository'
                echo '=========================================='
                checkout scm
            }
        }
        
        stage('Environment Setup') {
            steps {
                echo '=========================================='
                echo 'Test Configuration'
                echo '=========================================='
                echo "Browser: ${BROWSER}"
                echo "Environment: ${ENV}"
                echo "Base URL: ${BASE_URL}"
                echo "Headless Mode: ${HEADLESS}"
                echo "Tags: ${CUCUMBER_TAGS}"
                echo '=========================================='
                
                // Clean previous build artifacts
                bat '''
                    if exist target rmdir /s /q target
                    if exist test-output rmdir /s /q test-output
                    if exist reports rmdir /s /q reports
                    if exist logs rmdir /s /q logs
                '''
            }
        }
        
        stage('Build') {
            steps {
                echo '=========================================='
                echo 'Building project and resolving dependencies'
                echo '=========================================='
                bat 'mvn clean compile test-compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                echo '=========================================='
                echo 'Executing Cucumber Tests'
                echo '=========================================='
                
                script {
                    bat """
                        mvn clean test ^
                        -Dbrowser=${BROWSER} ^
                        -Dheadless=${HEADLESS} ^
                        -Denvironment=${ENV} ^
                        -Dbase.url=${BASE_URL} ^
                        -Dcucumber.filter.tags="${CUCUMBER_TAGS}"
                    """
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                echo '=========================================='
                echo 'Generating Test Reports'
                echo '=========================================='
                bat 'mvn verify -DskipTests'
            }
        }
    }
    
    post {
        always {
            echo '=========================================='
            echo 'Publishing Test Reports'
            echo '=========================================='
            
            // Publish Cucumber HTML Report
            publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'target/cucumber-reports',
                reportFiles: 'cucumber.html',
                reportName: 'Cucumber HTML Report',
                reportTitles: 'MTOmics Test Report'
            ])
            
            // Publish Extent Report
            publishHTML([
                allowMissing: true,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'test-output/extent-report',
                reportFiles: 'ExtentReport.html',
                reportName: 'Extent Report',
                reportTitles: 'MTOmics Extent Report'
            ])
            
            // Archive test artifacts
            archiveArtifacts artifacts: '**/cucumber-reports/**/*.*', allowEmptyArchive: true
            archiveArtifacts artifacts: '**/extent-report/**/*.*', allowEmptyArchive: true
            archiveArtifacts artifacts: '**/screenshots/**/*.png', allowEmptyArchive: true
            archiveArtifacts artifacts: '**/logs/**/*.log', allowEmptyArchive: true
            
            // Publish JUnit test results
            junit testResults: '**/cucumber-reports/cucumber.xml', allowEmptyResults: true
        }
        
        success {
            echo '=========================================='
            echo '✓ Build and Tests PASSED'
            echo '=========================================='
        }
        
        failure {
            echo '=========================================='
            echo '✗ Build or Tests FAILED'
            echo '=========================================='
        }
        
        unstable {
            echo '=========================================='
            echo '⚠ Build UNSTABLE - Some tests failed'
            echo '=========================================='
        }
    }
}

// Helper function to get base URL based on environment
def getBaseUrl(environment) {
    switch(environment) {
        case 'dev':
            return 'https://dev1.mtomics.com'
        case 'staging':
            return 'https://staging1.mtomics.com'
        case 'prod':
            return 'https://app1.mtomics.com'
        default:
            return 'https://dev1.mtomics.com'
    }
}
