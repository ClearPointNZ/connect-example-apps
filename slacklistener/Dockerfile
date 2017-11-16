FROM frolvlad/alpine-oraclejdk8
MAINTAINER Dharshana Ratnayake <dharshana.ratnayake@clearpoint.co.nz>

ENTRYPOINT ["/usr/bin/java", "-jar", "/usr/share/slacklistener/app.jar", "-P/etc/java-config/slack-listener.properties"]

# Add the service itself
ADD target/app.jar /usr/share/slacklistener/app.jar
