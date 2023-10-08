# Clickbus API

Welcome to the Clickbus API, a simple challenge project for [Quero Ser Clickbus](https://github.com/RocketBus/quero-ser-clickbus/tree/master/testes/backend-developer). This README provides you with essential information to get started. You can find the challenge details [here](challenge.md)
## Table of Contents
- [Quick deploy](#quick-deploy)
- [Developing](#developing)
  - [Prerequisites](#prerequisites)
  - [Running the Project](#running-the-project)
- [Build](#build)
- [Deployment](#deployment)
- [API Documentation](#api-documentation)
- [License](#license)

## Quick deploy

If you want to quickly test the API, make sure you have Docker and Docker Compose installed. Download the `docker-compose.yml` file and run the following command:

```bash
docker compose up -d
```

## Developing

### Prerequisites

Before you start, ensure you have the following prerequisites installed:

- **Java 17**
- **Docker**
- **Postgres 16**

### Running the Project

To run the project, execute the following command:
doc
```bash
./mvnw spring-boot:run
```

## Build

To build the project, run the following command:

```bash
./mvnw clean install
```

## Deployment

To deploy the Clickbus API, follow these steps:

1. Build the Docker image:

   ```bash
   docker build -t clickbus-api .
   ```

2. Run the Docker Compose:

   ```bash
   docker-compose up -d
   ```

## API Documentation

The API uses Swagger to document its endpoints. To access the documentation, follow these steps:

1. Ensure the project is running.

2. Visit the Swagger UI by replacing `localhost` with your host and `8080` with your port:

   ```
   http://localhost:8080/swagger-ui.html
   ```

   This will provide you with detailed information about the API's endpoints, request/response examples, and more.

Alternatively, you can visit https://editor.swagger.io/ and paste the text from [this file](/swagger.json).

## License

This project is licensed under the [MIT License](https://choosealicense.com/licenses/mit/).