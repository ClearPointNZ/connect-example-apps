pipelineJob('slack-sentiment-e2e-api-build') {
  description('Build end to end tests')
  triggers {
    scm('*/5 * * * *')
  }
  definition {
    cpsScm {
      scm {
        git('git@github.com:ClearPointNZ/connect-sample-apps.git') {
          node -> node / extensions()
        }
      }
      scriptPath('slack-e2e-api-tests/build/Jenkinsfile')
    }
  }
}
