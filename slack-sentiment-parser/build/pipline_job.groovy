pipelineJob('slack-sentiment-parser-build') {
  description('Build Slack sentiment parser')
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
      scriptPath('slack-sentiment-parser/build/Jenkinsfile')
    }
  }
}
