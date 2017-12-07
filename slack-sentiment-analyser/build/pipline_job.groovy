pipelineJob('slack-sentiment-analyser-build') {
  description('Build Slack Sentiment Analyser')
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
      scriptPath('slack-sentiment-analyser/build/Jenkinsfile')
    }
  }
}
