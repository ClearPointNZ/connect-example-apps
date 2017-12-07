pipelineJob('slack-sentiment-parser-api') {
  description('Build Slack Sentiment Parser API')
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
      scriptPath('slack-sentiment-parser-api/build/Jenkinsfile')
    }
  }
}
