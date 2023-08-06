# Authentication System

This project is a simple authentication system. It demonstrate how to secure your APIs with spring security with JWT.
## Project Structure

```css
├── src/
|   ├── main/
|   |   ├── java/
|   |   |   └── com.assignment.authenticationsystem/
|   |   |       ├── config/
|   |   |       ├── controllers/
|   |   |       ├── dto/
|   |   |       ├── exception/
|   |   |       ├── models/
|   |   |       ├── repository/
|   |   |       ├── security/
|   |   |       └── services/
|   |   |       ├── token/
|   |   └── resources/
|   |       ├── static/
|   |       |── templates/
|   |       └── application.properties/
├── test/
├── pom.xml
└── README.md
```
## Prerequisites

- Java 17
- Maven
- MySQL
- Postman or Talend API Tester
## Installation

- Java Development Kit (JDK) 17 or higher: You can download the JDK from [Oracle](https://www.oracle.com/in/java/technologies/downloads/#:~:text=JDK%20Development%20Kit%2020.0.2%20downloads,x64_bin.rpm%20(sha256)%C2%A0(OL%208%20GPG%20Key))


### Getting the Source Code

Clone the repository using [Git](https://git-scm.com/)

- Run the following commands in your Terminal

```bash
git clone https://github.com/hemantDwivedi/authentication-system.git

cd authentication-system
```

### Configure Database and Environment
1. Create a new database named ```'authentication_system'``` in your MYSQL.

2. Open project folder in IntelliJ IDEA and wait till to download all neccessary dependencies.

3. Open the ```application.properties``` file located in the ```src/main/resources``` directory.

4. Update the database connection properties (e.g., ```spring.datasource.url, spring.datasource.username, spring.datasource.password```) to match your database configuration.

5. Right Click on AuthenticationSystemApplication.java located in the ```src/main/java/com.assignment.authenticationsystem``` and run the applcation.

Once the application is running, you can test APIs in your [Postman](https://www.postman.com/downloads/) or with chrome extention [Talend API Tester](https://chrome.google.com/webstore/detail/talend-api-tester-free-ed/aejoelaoggembcahagimdiliamlcdmfm).

## Use Cases

- To create a new user, make a ```POST``` request to ```/api/v1/register``` with user details (e.g. username, password, email) in the request body.

- To generate JWT Token, make a ```POST``` request to ```/api/v1/login```

- ```GET /api/v1/admin``` only accessible by ADMIN user.

- To logout, make a ```GET``` request to ```/api/v1/logout```. It will logged out user and invalidate the JWT token.
