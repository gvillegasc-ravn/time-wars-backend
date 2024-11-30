# TimeWars Backend

### Requirements

- Java 17
- Maven 3.8.3
- Docker
- Docker Compose
- Spring Boot 3.2.0
- Spring Jpa
- Spring Web
- Postgres

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/gvillegasc-ravn/time-wars-backend
    ```

2. Clean and install the project
   ```sh
   mvn clean install
   ```

3. You have to connect to the database postgres with the following credentials:
   ```sh
   spring.datasource.url=jdbc:postgresql://localhost:5432/time-wars
   spring.datasource.username=ravn
   spring.datasource.password=timewars-secret
   ```
4. You have to create the schemma manually to run the migrations
   ```sh
   CREATE SCHEMA timewars_db;
   ```
5. Run Spring Boot
   ```sh
   mvn spring-boot:run
   ```
   
