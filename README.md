# Text Transformer

API for transforming texts.

## Running

`mvn clean spring-boot:run`

## Invoke API (curl)

```
curl -X POST http://localhost:8080/api/transform \
    -d '{"value":"Ahoj, jak se máš?"}' \
    --header "Content-Type: application/json" -u root:root
```
