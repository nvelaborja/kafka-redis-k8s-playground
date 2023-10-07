# kafka-redis-k8s-playground
Playing around with a basic Spring Boot web service leveraging Kafka, Redis, and Kubernetes

# Architecture
- Producer application -> sends kafka message every 10 seconds
- Web application -> Listens to kafka topic, stores messages in redis, exposes messages via localhost:8080/messages endpoint

# Running Application(s)
1. Build web app
   ```
   cd web && ./gradlew build
   ```
1. Build web docker image
   ```
   docker build -t nvelaborja/kafka-web .
   ```
1. Build kafka producer app
   ```
   cd producer && ./gradlew build
   ```
1. Build kafka producer docker image
   ```
   docker build -t nvelaborja/kafka-producer .
   ```
1. Create and run containers using docker-compose
   ```
   cd docker && docker-compose -f kafka-broker.yml up -d
   ```
