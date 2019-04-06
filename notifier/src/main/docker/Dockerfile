FROM openjdk:10
MAINTAINER Nikita Shaldenkov <shaldnikita2@yandex.ru>

ADD ./target/notifier.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/notifier.jar"]

EXPOSE 9000