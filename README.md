# BCI CRUD

User registry for BCI.

The system is a simple Rest application to store user information.

#### Available functionalities:
- Insert a user and an optional list of phones.
- Retrieve a user based on its id.

### Requirements
JRE that supports Java 24.
All the dependencies are self contained in the jar artifact, including the 
database (H2).

#### How to Run
A built folder has been commited to facilitate the execution.
From the root of the project execute the jar:

java -jar build/libs/registry-0.0.1-SNAPSHOT.jar

To build it from scratch, just use the "build" phase of Gradle on your platform.


### Tech Stack
Standard Spring Boot / JPA
The persistence is done via JPA in an in memory database JPA
and the migrations are handled via flyway in the default folder
main/java/resources/db.migration

## Architectural Records
### 01 - Packaging structure
A lightweight Domain Driven approach was chosen for the project.
Where the "user" package contains all the components for the provided features.
The "infra" package contains the orthogonal functionalities as security and error handling.

### 02 - Web layer and contracts
When creating the User resource the API contracts follow the REST specification, returning a response with
a 201 http code and the location for the newly created resource.

All the errors are returned as specified by the project requirements: {"message": "text"}
For a production class application we would like to use a more sophisticated contract where the clients
have a more structured error description.

The web layer is also separated from the domain the reason is to provide concise API and avoid domain data leakage
to the clients.

For example, when creating a User, a client does not need to send an Id, having a specific contract for the User creation
provides the client with only the necessary fields for the operation. Also, when returning a specific response, we
do not need to clear not necessary fields as the password or token. 

### 03 - Persistence
The persistence objects can't follow the Good Citizen pattern due to the JPA restrictions (empty constructor, getters / setters requirements).

### 04 - Domain and layers Modeling
Given the system requirements are only data manipulation the models are simple and the only business logic lies
in the Service layer to check the User duplication.

A Custom Email Validator (ConstraintValidator) could have been created where the REGEX for the email could be injected via properties.
However, due to a gap in the integration between Spring Boot and Jakarta Validation, a new ConstraintValidator is always
instantiated via Spring instead of using the suggested managed instance. 

If custom validations via annotations are a common requirement I suggested reserving time to submit a PR to Spring Boot.

A workaround could be to inject a Validator in the Service layer, but in this case we are moving from a declarative approach
to an imperative, for a CRUD application that is not a big tech debt, but could easily become a burden in an enterprise environment.

The email is currently validated with a builtin @Email annotation.

### 05 - Security implementation
The user token has been implemented using Cryptographically Secure Pseudo-Random Number Generator, instead of
a common UUID because it gives us the recommended entropy for secure systems, following AES-256 recommendations.
A JWT was not implemented due to the scope of the task.

Also, to comply with security standards the password and the token should not be stored in plain text in the
database as they are right now.

And finally, to compare the token or the password we must use a Timing-Safe Comparison.

### 06 - Testing Strategy
The application consist mostly of infrastructure to save and retrieve the data. A lightweight integration
testing strategy was applied, with that we ensure that all the parts are communicating well and the main functionalities are in place.

The real business and most valuable logic of validating the email was tested both unitarily and through integrations.  

### 07 - API Docs
Despite not have been implemented here an OpenAPI documentation is highly encouraged.

