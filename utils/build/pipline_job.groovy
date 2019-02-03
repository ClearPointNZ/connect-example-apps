pipelineJob('utils-build') {
	description('Build utils library')
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
			scriptPath('utils/build/Jenkinsfile')
		}
	}
}
