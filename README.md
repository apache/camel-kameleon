# Kameleon - project scaffolding for Apache Camel

This is a project generator for Apache Camel. It generates maven-based Java project with preconfigured Apache Camel runtime and selected components/extensions.

## Build and run locally 
### Prerequisites
- Java 11
- Maven 3.6
- Git

### Install camel-kamelets-catalog
Kameleon requires camel-kamelets-catalog that is not in maven central yet.
```bash
cd /tmp/camel
git clone git@github.com:apache/camel-kamelets.git 
mvn install   
```

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
export MAVEN_HOME=$YOUR_MAVEN_HOME ; java -jar target/kameleon/kameleon-0.2.0-runner.jar
```


## Start locally
Start the latest image locally:
```bash
docker run -i --rm -p 8080:8080 ghcr.io/apache/camel-kameleon:latest
```
