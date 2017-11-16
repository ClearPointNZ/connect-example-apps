FROM frolvlad/alpine-oraclejdk8

MAINTAINER Haichang Lai <haichang.lai@clearpoint.co.nz>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/slacksentimentanalyser/app.jar", "-P/etc/java-config/slack-sentiment-analyser.properties"]

# Add the service itself
ADD target/app.jar /usr/share/slacksentimentanalyser/app.jar
