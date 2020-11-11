FROM tomcat:9.0-jre8-alpine

RUN ["rm", "-fr", "/usr/local/tomcat/webapps/ROOT"]

COPY ./target/spring-mvc-webapp.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh", "run"]
