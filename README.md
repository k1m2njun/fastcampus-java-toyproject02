# TOY PROJECT 02
###### 1조 길민준&문준호 @패스트캠퍼스 백엔드 부트캠프 5기, [팀 노션 링크](https://www.notion.so/1-78aa86c31d4842b0ab798de3dfc4dcc9?pvs=4)

---
## - 맡은 업무
>### 길민준
1. 깃 레포지토리, 프로젝트 생성 및 기본 구성
2. 테이블 설계 및 기능 별 쿼리 작성
3. 코드 병합, 컨벤션
4. Player, OutPlayer - Model, DTO, DAO, Service 클래스 작성
   1. PlayerDao
      1. 싱글톤
      2. DBConnection
      3. getAllPlayers() - 전체 선수 목록
      4. teamOutPlayer() - 선수 팀에서 방출
      5. createPlayer() - 3.5 선수 등록
      6. getPlayersByTeam() - 3.6팀 별 선수 목록
      7. getPlayerById() - id로 선수 조회
      8. getPlayerByPosition() - 팀 별 포지션으로 선수 검색
      9. createPlayerTable() - player 테이블 생성
      10. buildPlayerFromResultSet() - Player 결과 객체 빌더
   2. PlayerService
      1. 선수등록()
         1. Exception
            - 입력한 teamId가 존재하는 팀이 아닌 경우
            - 요청 데이터를 덜 입력한 경우
      2. 선수목록()
         1. Exception 
            - 입력한 teamId가 존재하는 팀이 아닌 경우
            - 입력한 팀에 선수가 없는 경우
   3. OutPlayerDao
      1. 싱글톤
      2. DBConnection
      3. getOnlyOutPlayers() - 퇴출 선수 id 검색(퇴출선수 중복 확인용)
      4. createOutPlayer() - 3.7 퇴출선수 등록
      5. getOutPlayerByPlayerId() - playerId로 퇴출선수 검색
      6. getAllOutPlayers() - 3.8 퇴출선수 목록 
      7. buildOutPlayerFromResultSet() - OutPlayer 결과 객체 빌더
   4. OutPlayerService
      1. 퇴출등록()
         1. Exception
            - 요청한 id값의 선수가 DB에 없는 경우
            - 요청 데이터를 덜 입력한 경우
            - 이미 퇴출된 선수인 경우
      2. 퇴출목록()
         1. Exception
            - 퇴출선수가 DB에 하나도 없는 경우
5. BaseBallApplication 작성
   1. Scanner로 입력받은 문자열 데이터 parsing 결과에 따른 메서드 호출

>### 문준호
1. 테이블 설계 및 기능 별 쿼리 작성
2. Team, Stadium - Model, DTO, DAO, Service 클래스 작성
3. 3.9 포지션 별 팀 선수 페이지
---
## - 기타
- Controller가 과제 내용에 빠져있어서 일부러 빼고 만들었음.
- Lombok, 어노테이션 기본적인 것은 사용하였지만 그 외에는 JDBC
- DAO에서 예외는 throw하고 main에서 처리함
---

## - QUERY SET
### 테이블 생성
* player
```sql
CREATE TABLE player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    team_id INT,
    name VARCHAR(20) NOT NULL,
    position VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (team_id) REFERENCES team(id),
    UNIQUE(team_id, position)
);
```
* out_player
```sql
CREATE TABLE out_player (
    id INT AUTO_INCREMENT PRIMARY KEY,
    player_id INT NOT NULL,
    reason VARCHAR(100) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (player_id) REFERENCES player(id)
);
```
* team
```sql
CREATE TABLE team (
    id INT AUTO_INCREMENT PRIMARY KEY,
    stadium_id INT NOT NULL,
    name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (stadium_id) REFERENCES stadium(id)
);
```
* stadium
```sql
CREATE TABLE stadium (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL
);
```