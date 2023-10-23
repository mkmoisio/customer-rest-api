Requirements
* Maven (tested with 3.9.5)
* Java version 17 or later (tested with Amazon Corretto version 21.0.1)

To start
* Clone repostiory ```git clone git@github.com:mkmoisio/customer-rest-api.git```
* Nagivate to project root and run ```./mvnw spring-boot:run```


Swagger ui URL:
* http://localhost:8080/swagger-ui/index.html


List of Sins
* No error handling for non-existsing uris
* Error messages don't follow best practices
* API definition lacks request constraints
* Not all constraints are validated at once i.e. request with missing name and invalid businessId returns error indicating missing name but mentions no businessId
* CustomerService has too many responsibilities
* Kauppalehti API responses lack proper error handling
* Inadequate tests