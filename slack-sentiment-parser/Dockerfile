FROM frolvlad/alpine-oraclejdk8

MAINTAINER Haichang Lai <haichang.lai@clearpoint.co.nz>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/slacksentimentparser/app.jar", "-P/etc/java-config/slack-sentiment-parser.properties"]

# Add the service itself
ADD target/app.jar /usr/share/slacksentimentparser/app.jar
