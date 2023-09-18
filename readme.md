### How to use this spring-boot project

- Install packages with `mvn package`
- Run `mvn spring-boot:run` for starting the application (or use your IDE)

Application (with the embedded H2 database) is ready to be used ! You can access the url below for testing it :

- Swagger UI : http://localhost:8080/swagger-ui.html
- H2 UI : http://localhost:8080/h2-console

> Don't forget to set the `JDBC URL` value as `jdbc:h2:mem:testdb` for H2 UI.



### Instructions

- download the zip file of this project
- create a repository in your own github named 'java-challenge'
- clone your repository in a folder on your machine
- extract the zip file in this folder
- commit and push

- Enhance the code in any ways you can see, you are free! Some possibilities:
  - Add tests
  - Change syntax
  - Protect controller end points
  - Add caching logic for database calls
  - Improve doc and comments
  - Fix any bug you might find
- Edit readme.md and add any comments. It can be about what you did, what you would have done if you had more time, etc.
- Send us the link of your repository.

#### Restrictions
- use java 8


#### What we will look for
- Readability of your code
- Documentation
- Comments in your code 
- Appropriate usage of spring boot
- Appropriate usage of packages
- Is the application running as expected
- No performance issues

#### Your experience in Java

Please let us know more about your Java experience in a few sentences. For example:

- I started web development around 4 years ago. I was an Embedded engineer at first, and was curious about the web development tech stack and decided to pursue it. I landed a job using Java with Spring Boot and have been using it until now.
- I have not yet touched on Spring Boot three though, my current experience are limited to Spring Boot 2. 

#### Changes
- Updated the spring version to 2.2.0.RELEASE. I decided to upgrade it to 2.2 to leverage Junit5's parameterized tests.
- Added tests for the controller apis.
- Added request parameter validation for the controller apis.
- Added a custom exception handler to handle exceptions thrown by the controller apis.
- Removed direct use of entities in request param and instead use data transfer objects.
- Added caching for the employee service.
- Added a response body that contains a message and/or a list of employees. (For POST and PUT, it returns a singleton list of employee)
- Added logging instead of using system std output.
- Moved dependency injection to constructor. This will help us limit our number of dependencies per bean.
- Added few swagger documentation.
