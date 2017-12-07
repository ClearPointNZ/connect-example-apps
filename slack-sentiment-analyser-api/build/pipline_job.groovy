pipelineJob('slack-sentiment-analyser-api-build') {
  description('Build sentiment analyser API')
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
      scriptPath('slack-sentiment-analyser-api/build/Jenkinsfile')
    }
  }
}
