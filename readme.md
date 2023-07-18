# Spring Initializr
- Gradle
- Java 11
- Spring Boot 2.7.13
- Jar
- Dependency
  - Spring Web
  - Spring Security
  - Spring Data JPA
  - Lombok
  - H2 Database

# API 호출 테스트 
- POST /api/auth/signup
- Content-Type: application/json
  - request
  ```json
   {
     "memberName": "tester",
     "password": "tester"
   }
  ```
  - response
  ```json
  {
     "memberId": 3
  }
  ```
