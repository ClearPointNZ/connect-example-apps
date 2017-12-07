pipelineJob('slacklistener-build') {
  description('Build Slack listener')
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
      scriptPath('slacklistener/build/Jenkinsfile')
    }
  }
}
