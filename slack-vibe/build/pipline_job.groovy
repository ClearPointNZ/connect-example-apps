pipelineJob('slack-vibe-build') {
  description('Build Slack Vibe')
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
      scriptPath('slack-vibe/build/Jenkinsfile')
    }
  }
}
