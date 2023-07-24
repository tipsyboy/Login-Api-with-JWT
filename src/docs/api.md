# API 호출 테스트

-----------------------------------------


## 회원가입
### Request
```HTTP
POST /api/auth/signup
Content-Type: application/json
```
```json
{
  "memberName": "tester",
  "password": "tester"
}
```
### Response
```json
{
  "memberId": "3"
}
```


## 로그인
### Request
```HTTP
POST /api/auth/login
Content-Type: application/json
```
```json
{
  "memberName": "tester", 
  "password": "tester"
}
```
### Response
```json
{
  "memberName": "tester",
  "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzM1NzYsImV4cCI6MTY4OTY3NTM3Nn0.TJnNMLPjw-cv0v-OJr4bhnKO7iLcAK31oLivApzalLAYxOd8MOFqRaC5SFXwCkRKZDTiYlrOaD_ikyupDSUPTw",
  "refreshToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0ZXIiLCJhdXRoIjoiUk9MRV9NRU1CRVIiLCJpYXQiOjE2ODk2NzM1NzYsImV4cCI6MTY5MDI3ODM3Nn0.LbsZxFKduiYapLsP-pg83POtNRAFzcQVD6T83Cn5DXE1mJ5qORtcydwv3uiufw4T-aEV7hwptPeGFErd54wp2A"
}
```


## 일반 API 호출
### Request
```HTTP
GET /api/member/info
Authorization: Bearer ...Token...
```
### Response
```json
{
  "memberId": 3,
  "memberName": "tester"
}
```


## ADMIN 권한의 API 호출
- 권한이 없는 사용자에 대해서는 403 Forbidden Error
### Request
```HTTP
GET /api/member/info/{member_name}
Authorization: Bearer ...Access_Token...
```
### Response
```json
{
  "memberId": 1,
  "memberName": "{member_name}"
}
```


## 토큰 재발급
### Request
```HTTP
POST /api/auth/reissue 
Content-Type: application/json
```
```json
{
  "accessToken": "...Expired_Access_Token...",
  "refreshToken": "...Refresh_Token..."
}
```
### Response
```json
{
  "memberName": "tester",
  "accessToken": "...Reissued_Access_Token...",
  "refreshToken": "...Reissued_Refresh_Token..."
}
```
