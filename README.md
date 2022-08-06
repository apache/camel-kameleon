# Kameleon - project scaffolding for Apache Camel

This is a project generator for Apache Camel. It generates maven-based Java project with preconfigured Apache Camel runtime and selected components/extensions.

Try [kameleon.dev](https://kameleon.dev)

## Build and run locally 
### Prerequisites
- Java 11
- Maven 3.8
- Git

### Run in development mode
```bash
export MAVEN_HOME=$YOUR_MAVEN_HOME ; mvn quarkus:dev
```

### Build 
```bash
mvn package
```

### Run 
```bash
export MAVEN_HOME=$YOUR_MAVEN_HOME ; java -jar target/kameleon/kameleon-0.3.0-runner.jar
```


## Start locally
Start the latest image locally:
```bash
docker run -i --rm -p 8080:8080 ghcr.io/apache/camel-kameleon:latest
```
