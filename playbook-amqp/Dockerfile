FROM openjdk:8-jre-slim
ADD target/*.jar playbook-amqp.jar
EXPOSE 8085
ENV JAVA_OPTS="-Xms200M -Xmx200M -server"
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /playbook-amqp.jar" ]
