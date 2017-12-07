pipelineJob('slack-message-reader-api-build') {
  description('Build message reader api')
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
      scriptPath('slack-message-reader-api/build/Jenkinsfile')
    }
  }
}
