# Beer Stock API Digital Innovation One Project
![APM](https://img.shields.io/apm/l/vim-mode)
![](https://img.shields.io/badge/CodeCoverage-69%25%09-yellowgreen)
<h3>Project created to follow DIO bootcamp, this projects uses Spring Boot and 
unit testing approach to implement an API with Beer Stock CRUD operations
and a repository built using H2.</h3>

Available OpenAPI Documentation with swagger-ui using springdoc-openai-ui

<hr>

This project explores several concepts including:

- MVC pattern
- REST API
- H2 Repository
- JPA and Hibernate for DAO
- CRUD operations
- DTO for Data Conversion and Single Responsibility Principle Compliance
- MapStruct for DTO to Entity and vice-versa for convention over configuration mapping
- Git Flow and Conventional Commits tools for code versioning
- TDD
- Testing main API functionalities with Unit Testing
- API endpoints testing with Postman
- SpringDoc OpenAPIDoc
- etc...

<hr>

## Testing the Project
```
mvn clean test
```
<hr>

## Project Coverage
First run the ``mvn clean test`` command to generate jacoco.exec file then
run the command below to generate the jacoco html report. 
```
mvn jacoco:report
```

The html report can be found at target/site/jacoco/index.html  
<hr>

## Running the Project
To run the project simply run
```
mvn spring-boot:run
```

The api will be served at port 8080.  
In order to access the api path is [localhost:8080/api/v1/beers](http:localhost:8080/api/v1/beers)  

## Swagger-UI Opendoc API Documentation
You can find the swagger-ui OpenAPI Documentation at [localhost:8080/swagger-ui.html](http:localhost:8080/swagger-ui.html)
