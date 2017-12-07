pipelineJob('slack-message-reader') {
  description('Build Slack message reader')
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
      scriptPath('slack-message-reader/build/Jenkinsfile')
    }
  }
}
