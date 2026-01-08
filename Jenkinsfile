pipeline {
    agent any
    
    tools {
        maven 'Maven-3.9.5' // Configure this in Jenkins Global Tool Configuration
        jdk 'JDK-21'        // Configure this in Jenkins Global Tool Configuration
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
            description: 'Select environment to run tests against'
        )
        choice(
            name: 'HEADLESS',
            choices: ['false', 'true'],
            description: 'Run browser in headless mode'
        )
        string(
            name: 'TAGS',
            defaultValue: '@admin',
            description: 'Cucumber tags to execute (e.g., @smoke, @regression, @admin, @provider)'
        )
        booleanParam(
            name: 'PARALLEL_EXECUTION',
            defaultValue: false,
            description: 'Enable parallel test execution'
        )
        choice(
            name: 'THREAD_COUNT',
            choices: ['1', '2', '3', '4', '5'],
            description: 'Number of parallel threads (only if parallel execution is enabled)'
        )
    }
    
    environment {
        // Project Configuration
        PROJECT_NAME = 'MTOmics-Automation'
        
        // Browser Configuration
        BROWSER = "${params.BROWSER}"
        HEADLESS = "${params.HEADLESS}"
        
        // Environment Configuration
        ENV = "${params.ENVIRONMENT}"
        BASE_URL = getBaseUrl("${params.ENVIRONMENT}")
        
        // Test Configuration
        CUCUMBER_TAGS = "${params.TAGS}"
        
        // Report Paths
        CUCUMBER_REPORT_PATH = 'target/cucumber-reports'
        EXTENT_REPORT_PATH = 'test-output'
        SCREENSHOT_PATH = 'reports/screenshots'
        
        // Maven Options
        MAVEN_OPTS = '-Xmx1024m -XX:MaxPermSize=256m'
    }
    
    stages {
        stage('Checkout') {
            steps {
                script {
                    echo "=========================================="
                    echo "Checking out code from repository"
                    echo "=========================================="
                }
                checkout scm
            }
        }
        
        stage('Environment Setup') {
            steps {
                script {
                    echo "=========================================="
                    echo "Setting up test environment"
                    echo "=========================================="
                    echo "Browser: ${BROWSER}"
                    echo "Environment: ${ENV}"
                    echo "Base URL: ${BASE_URL}"
                    echo "Headless Mode: ${HEADLESS}"
                    echo "Tags: ${CUCUMBER_TAGS}"
                    echo "=========================================="
                }
                
                // Clean previous build artifacts
                bat '''
                    if exist target rmdir /s /q target
                    if exist test-output rmdir /s /q test-output
                    if exist reports rmdir /s /q reports
                    if exist logs rmdir /s /q logs
                '''
                
                // Create necessary directories
                bat '''
                    mkdir reports\\screenshots
                    mkdir logs
                '''
            }
        }
        
        stage('Dependency Resolution') {
            steps {
                script {
                    echo "=========================================="
                    echo "Resolving Maven dependencies"
                    echo "=========================================="
                }
                bat 'mvn clean dependency:resolve'
            }
        }
        
        stage('Compile') {
            steps {
                script {
                    echo "=========================================="
                    echo "Compiling project"
                    echo "=========================================="
                }
                bat 'mvn compile test-compile'
            }
        }
        
        stage('Run Tests') {
            steps {
                script {
                    echo "=========================================="
                    echo "Executing Cucumber Tests"
                    echo "=========================================="
                    
                    def mvnCommand = "mvn clean test " +
                        "-Dbrowser=${BROWSER} " +
                        "-Dheadless=${HEADLESS} " +
                        "-Denvironment=${ENV} " +
                        "-Dbase.url=${BASE_URL} " +
                        "-Dcucumber.filter.tags=\"${CUCUMBER_TAGS}\""
                    
                    if (params.PARALLEL_EXECUTION) {
                        mvnCommand += " -Dparallel=methods -DthreadCount=${params.THREAD_COUNT}"
                    }
                    
                    // Execute tests and continue even if tests fail
                    bat(script: mvnCommand, returnStatus: true)
                }
            }
        }
        
        stage('Generate Reports') {
            steps {
                script {
                    echo "=========================================="
                    echo "Generating Test Reports"
                    echo "=========================================="
                }
                
                // Generate Cucumber reports
                bat 'mvn verify -DskipTests'
            }
        }
        
        stage('Publish Reports') {
            steps {
                script {
                    echo "=========================================="
                    echo "Publishing Test Reports"
                    echo "=========================================="
                }
                
                // Publish Cucumber reports
                cucumber buildStatus: 'UNSTABLE',
                    reportTitle: 'MTOmics Cucumber Report',
                    fileIncludePattern: '**/cucumber.json',
                    trendsLimit: 10,
                    classifications: [
                        [
                            'key': 'Browser',
                            'value': "${BROWSER}"
                        ],
                        [
                            'key': 'Environment',
                            'value': "${ENV}"
                        ],
                        [
                            'key': 'Tags',
                            'value': "${CUCUMBER_TAGS}"
                        ]
                    ]
                
                // Publish HTML reports
                publishHTML([
                    allowMissing: false,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'cucumber.html',
                    reportName: 'Cucumber HTML Report',
                    reportTitles: 'MTOmics Test Report'
                ])
                
                // Publish Extent Reports if available
                publishHTML([
                    allowMissing: true,
                    alwaysLinkToLastBuild: true,
                    keepAll: true,
                    reportDir: 'test-output',
                    reportFiles: '*.html',
                    reportName: 'Extent Report',
                    reportTitles: 'MTOmics Extent Report'
                ])
                
                // Archive test artifacts
                archiveArtifacts artifacts: '**/cucumber-reports/**/*.*', 
                    allowEmptyArchive: true,
                    fingerprint: true
                
                archiveArtifacts artifacts: '**/test-output/**/*.*',
                    allowEmptyArchive: true,
                    fingerprint: true
                
                archiveArtifacts artifacts: '**/screenshots/**/*.png',
                    allowEmptyArchive: true,
                    fingerprint: true
                
                archiveArtifacts artifacts: '**/logs/**/*.log',
                    allowEmptyArchive: true,
                    fingerprint: true
                
                // Publish JUnit test results
                junit testResults: '**/cucumber-reports/cucumber.xml',
                    allowEmptyResults: true,
                    skipPublishingChecks: true
            }
        }
    }
    
    post {
        always {
            script {
                echo "=========================================="
                echo "Build completed"
                echo "=========================================="
            }
            
            // Clean workspace if needed
            cleanWs(
                deleteDirs: true,
                disableDeferredWipeout: true,
                patterns: [
                    [pattern: 'target/**', type: 'INCLUDE'],
                    [pattern: '.git/**', type: 'EXCLUDE']
                ]
            )
        }
        
        success {
            script {
                echo "=========================================="
                echo "✓ Build SUCCESS"
                echo "=========================================="
            }
            
            emailext(
                subject: "✓ SUCCESS: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Successful</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Browser:</strong> ${BROWSER}</p>
                    <p><strong>Environment:</strong> ${ENV}</p>
                    <p><strong>Tags:</strong> ${CUCUMBER_TAGS}</p>
                    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p><strong>Test Report:</strong> <a href="${env.BUILD_URL}cucumber-html-reports/overview-features.html">View Report</a></p>
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/html'
            )
        }
        
        failure {
            script {
                echo "=========================================="
                echo "✗ Build FAILED"
                echo "=========================================="
            }
            
            emailext(
                subject: "✗ FAILURE: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Failed</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Browser:</strong> ${BROWSER}</p>
                    <p><strong>Environment:</strong> ${ENV}</p>
                    <p><strong>Tags:</strong> ${CUCUMBER_TAGS}</p>
                    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p><strong>Console Output:</strong> <a href="${env.BUILD_URL}console">View Console</a></p>
                    <p>Please check the console output and test reports for details.</p>
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/html'
            )
        }
        
        unstable {
            script {
                echo "=========================================="
                echo "⚠ Build UNSTABLE"
                echo "=========================================="
            }
            
            emailext(
                subject: "⚠ UNSTABLE: ${env.JOB_NAME} - Build #${env.BUILD_NUMBER}",
                body: """
                    <h2>Build Unstable</h2>
                    <p><strong>Job:</strong> ${env.JOB_NAME}</p>
                    <p><strong>Build Number:</strong> ${env.BUILD_NUMBER}</p>
                    <p><strong>Browser:</strong> ${BROWSER}</p>
                    <p><strong>Environment:</strong> ${ENV}</p>
                    <p><strong>Tags:</strong> ${CUCUMBER_TAGS}</p>
                    <p><strong>Build URL:</strong> <a href="${env.BUILD_URL}">${env.BUILD_URL}</a></p>
                    <p><strong>Test Report:</strong> <a href="${env.BUILD_URL}cucumber-html-reports/overview-features.html">View Report</a></p>
                    <p>Some tests may have failed. Please review the test reports.</p>
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/html'
            )
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
            return 'https://mtomics.com'
        default:
            return 'https://dev1.mtomics.com'
    }
}
