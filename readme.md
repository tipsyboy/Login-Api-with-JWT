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
#### - signup
- #### request
  - POST /api/auth/signup
  - Content-Type: application/json
  ```json
  {
    "memberName": "tester",
    "password": "tester"
  }  
  ```
- #### response
  ```json
  {
    "memberId": 3
  }
  ```



#### - login
- #### request
  - POST /api/auth/login
  - Content-Type: application/json
  ```json
  {
    "memberName": "tester", 
    "password": "tester"
  }
  ```
- #### response
  ```json
  {
    "memberName": "tester",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzM1NzYsImV4cCI6MTY4OTY3NTM3Nn0.TJnNMLPjw-cv0v-OJr4bhnKO7iLcAK31oLivApzalLAYxOd8MOFqRaC5SFXwCkRKZDTiYlrOaD_ikyupDSUPTw",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzM1NzYsImV4cCI6MTY5MDI3ODM3Nn0.LbsZxFKduiYapLsP-pg83POtNRAFzcQVD6T83Cn5DXE1mJ5qORtcydwv3uiufw4T-aEV7hwptPeGFErd54wp2A"
  }
  ```
  
#### - 일반 API 호출
- #### request
  - GET /member/info
  - Authorization: Bearer .....
  
- #### response
  ```json
  {
    "memberId": 3,
    "memberName": "tester"
  }
  ```

#### - ADMIN 권한의 API 호출
- 권한이 없는 사용자에 대해서는 403 Forbidden Error
- #### request
  - GET /member/info/{member_name}
  - Authorization: Bearer .....

- #### response
  ```json
  {
    "memberId": 1,
    "memberName": "admin"
  }
  ```

#### - 토큰 재발급
- #### request
  - POST /api/auth/reissue
  - Content-Type: application/json
  ```json
  {
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzUyOTcsImV4cCI6MTY4OTY3NzA5N30.t17cJ3nd6VdTedOO-m74ZdHS_ylMjYs25vdUf3nWKOVVwC99IElOP8HGHrsmLw55_vnHyYeF-ivYxYIs5nS42w",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzUyOTcsImV4cCI6MTY5MDI4MDA5N30.NeJPocgBIPVIdT6NC30ALrKVQiork_D-2KawExrF6-yX-l7hue8dyhyKu5306FaGRc5Osrj9Hq8YitDBm66NGw"
  }
  ```

- #### response
  ```json
  {
    "memberName": "tester",
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzUzMDgsImV4cCI6MTY4OTY3NzEwOH0.WrhaOBT2hj1l9LeoOZCFbyFH8AQsnZYQz-U-XyaoQRhDsCk1p5ulcmFpZo9NHfbk2f4y7fd-OkP5Ry6xEYr_kA",
    "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzUzMDgsImV4cCI6MTY5MDI4MDEwOH0.SqnNy2T10MQpZZzoMc31TsNlb-g0BEvngCIat4w6o4uG_nLUTaiZSXSmf6XaeT15YZrz7oKgZDMVWTEVFlBM8g"
  }
  ```