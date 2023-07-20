# Login-Api-with-JWT

-----------------------------------------

## 프로젝트 시작 이유
아무 생각 없이 막연하게 사용하던 기존 Spring Security 인증의 동작 원리를 
JWT 개념과 함께 풀어 보기 위한 프로젝트입니다.


## 프로젝트 시나리오
![projectProcess](https://github.com/tipsyboy/Login-Api-with-JWT/assets/42955111/0ac96ed6-a00d-42f9-a280-f9900a2d9cf4)
1. 최초 인증 과정 - Login
   1) 사용자 정보를 담은 요청으로 로그인 시도
   2) 일련의 과정으로 인증 완료후 토큰 정보를 응답
2. Login 이후의 Api 요청
   1) Api 요청시 토큰 정보를 함께 요청한다.
   2) 서버에서 받은 토큰을 검증하고 유효한 토큰인 경우 Api 요청을 응답한다.
3. 만료된 Token의 재발급
   1) AccessToken 만료시에 만료된 AccessToken과 RefreshToken을 서버로 보낸다.
   2) 받은 토큰을 검증하고 새로운 AccessToken과 RefreshToken을 응답한다.


## Spring Security 동작 원리와 JWT
![springSecurity 동작원리](https://github.com/tipsyboy/Login-Api-with-JWT/assets/42955111/9f253617-4d25-4555-ac95-b3a1174661ed)
- Request에 Jwt가 포함되지 않은 경우
1) 사용자로부터 로그인 정보를 입력받아서 HttpRequest를 보낸다.
2) Http 요청을 받은 Filter가 인증 과정에서 사용할 UsernamePasswordAuthenticationToken을 생성한다 (UsernamePasswordAuthenticationFilter)
3) AuthenticationFilter는 생성한 UsernamePasswordAuthenticationToken를 AuthenticationManager로 처리 위임
4) Authentication Manager는 여러 등록된 여러 Provider들 중에서  위에서 생성한 UsernamePasswordAuthenticationToken을 처리할 수 있는 Provider찾아서 Token을 보낸다
5) AuthenticationProvider는 오버라이드된 authenticate() 메서드를 수행하기 위해서 UserDetailsService를 호출
6) UserDetailsService loadUserByUsername() 메서드를 통해서 DB에서 사용자 정보를 가져온다.
7) 다시 Provider에서 6)에서 반환받은 사용자 정보 UserDetails 객체를 앞서 생성된 UsernamePasswordAuthenticationToken의 정보와 비교해서 일치하면 Authentication 객체를 반환하고 일치하지 않으면 예외처리를 한다.
8) 이후 이 프로젝트에서는 jwt를 사용하므로 인증 완료가 되면 리턴 받은 Authentication 사용해서 정의한 TokenProvider를 통해 jwt를 만들어서 ResponseDto를 통해 반환하고 SecurityContextHolder에 리턴 받은 Authentication을 저장한다.

- Request에 Jwt가 포함된 경우
1) 사용자로부터 jwt가 포함된 Request를 받는다
2) 이 프로젝트에서 구현한 JwtFilter에서 요청을 인터셉트 한다.
3) JwtFilter에서는 요청에 포함된 토큰이 유효한지를 판단해서 유효한 토큰임이 확인되면
   토큰을 통해서 Authentication 객체를 만들고 SecurityContextHolder에 Authentication 객체를 저장함으로 인증이 완료된다.

- 위 과정은 스프링 시큐리티 인증의 일련의 과정이고 
JWT를 사용한다고 해서 특별하게 과정이 변경되는 것은 아니다.
JWT를 사용할 경우엔 
인증 완료후 토큰을 발행하고 
이후 과정에서 AuthenticationFilter에서 JWT에 대한 처리를 하는 과정이 포함된다. 


## 개발환경
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
- Test
  - Postman 


## API 호출 테스트
[API 호출 테스트](https://github.com/tipsyboy/Login-Api-with-JWT/blob/master/src/docs/api.md)

