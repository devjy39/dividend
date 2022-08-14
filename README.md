# dividend
미 회사 주식 배당금 조회 REST API

## 기능
- 회원가입 및 jwt를 이용한 로그인
- 회사명을 추가 조회 검색 삭제
- 회사의 배당금 조회
  - 첫 조회 이후엔 redis cache를 이용한 좀 더 빠른 조회
- 확장성을 위해 Scraper 인터페이스 구현
  - Yahoo 에서 회사정보와 배당금 정보를 스크랩 해오는 구현체 구현
- 회사명 자동완성기능
  - 한 번 쓸 때 자주 호출되기 때문에 인메모리기반 trie 자료구조로 구현, DB로의 로직도 구현
- Spring Scheduler를 이용해 주기마다 캐시 삭제 및 배당금 스크랩 기능
- jwt에 member entity의 roles{"WIRTE","READ"}를 넣고 권한에 따른 API 접근
  

## skills
- spring boot
- spring data jpa
- h2 DB
- spring security
- jwt
- jsoup, lombok

## API
