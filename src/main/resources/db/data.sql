INSERT INTO stadium(name, created_at) values('고척스카이돔', now());
INSERT INTO stadium(name, created_at) values('잠실야구장', now());
INSERT INTO stadium(name, created_at) values('인천SSG랜더스필드', now());

INSERT INTO team(stadium_id, name, created_at) values(1, '기아타이거즈', now());
INSERT INTO team(stadium_id, name, created_at) values(2, '두산베어스', now());
INSERT INTO team(stadium_id, name, created_at) values(3, '한화이글스', now());

INSERT INTO player(team_id, name, position, created_at) values(1, '황동하', '투수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '신범수', '포수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '박찬호', '1루수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '김규성', '2루수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '오정환', '3루수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '회정용', '유격수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '박정우', '좌익수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '최원준', '중견수', now());
INSERT INTO player(team_id, name, position, created_at) values(1, '이우성', '우익수', now());

INSERT INTO player(team_id, name, position, created_at) values(2, '박치국', '투수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '안승한', '포수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '박지훈', '1루수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '허경민', '2루수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '박계범', '3루수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '서예일', '유격수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '정수빈', '좌익수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '김재환', '중견수', now());
INSERT INTO player(team_id, name, position, created_at) values(2, '김대한', '우익수', now());

INSERT INTO player(team_id, name, position, created_at) values(3, '문동주', '투수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '최재훈', '포수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '하주석', '1루수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '김태연', '2루수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '김인환', '3루수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '정은원', '유격수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '김해찬', '좌익수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '이명기', '중견수', now());
INSERT INTO player(team_id, name, position, created_at) values(3, '노수광', '우익수', now());