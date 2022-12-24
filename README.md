# Gringotts - Hexagonal Architecture 



## How to run it?

```
mvn spring-boot:run
```

## Swagger

Swagger - http://localhost:8080/swagger-ui/index.html

## How can I test it?
- tbd

## Docker

1. Build image
```
docker build -t gringotts/gringotts-core .
```

2. Run image
```
docker run -p 8080:8080 --add-host=database:127.0.0.1 --name gringotts-core -e "SPRING_PROFILES_ACTIVE=docker" gringotts/gringotts-core
docker run -p 8080:8080 --add-host=database:127.0.0.1 --name gringotts-core gringotts/gringotts-core

```