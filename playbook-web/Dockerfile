FROM openjdk:8-jre-slim
ADD target/*.jar playbook-web.jar
EXPOSE 8081 5005
ENV JAVA_OPTS="-Xms200M -Xmx200M -server -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /playbook-web.jar"]
