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
```bash
docker build -t gringotts/gringotts-core:local .
```

2. Run with db
````bash
docker compose up -d
````
