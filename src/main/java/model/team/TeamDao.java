package model.team;

import dto.position.PositionResponseDto;
import dto.team.TeamResponseDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TeamDao {

    private Connection connection;

    public TeamDao(Connection connection) {
        this.connection = connection;
    }

    // 1. 해당 경기장이 존재 여부 확인 -> 존재해야함. [TeamDao의 stadiumId로 StadiumDao의 id 검색]
    // 2. 해당 경기장에 팀이 등록된 상태인지 확인  -> 등록이 안된 상태여야함. [TeamDao의 name으로 팀 전체 select해서 검색해서 확인]
    // 3. 등록하는 팀이름이 중복되지 않아야함. -> [TeamDao 사용]
    // 4.. 1, 2, 3을 만족해야 팀으로 등록가능함.
    public Team createTeam(int stadiumId, String teamName) {
        String query = "INSERT INTO team(stadium_id, name, created_at) VALUES(?, ?, now())";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, stadiumId);
            ps.setString(2, teamName);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                return getTeamByStadiumId(stadiumId);
            }
        } catch (SQLException e) {
            System.out.println("팀 등록과정에서 Exception 발생 : " + e.getMessage());
            e.printStackTrace();
        }

        return null;// 경기장 등록 실패
    }

    public Team getTeamByStadiumId(int stadiumId) {
        //1. StadiumDao에서 중복을 확인한다.
        //-> StadiumDao 클래스에서 getStadiumById 메서드 추가
        //-> 다시 생각해보니 TeamDao 에서 해결할수 있는 문제인것 같다. getStadiumById 메서드는 만든채로 놔두기로
        //1.sql
        String query = "SELECT * FROM team WHERE stadium_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setInt(1, stadiumId);
            try (ResultSet rs = ps.executeQuery()) {//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    return buildTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("팀에서 stadiumId 검색중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;// team not found
    }

    public Team getTeamNameById(int teamId){
        //1.sql
        String query = "select *\n" +
                "from team\n" +
                "where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    return buildTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("teamId로 팀 이름 검색중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;// team not found
    }
    public Team getTeamByName(String teamName) {
        //1.sql
        String query = "SELECT * FROM team WHERE name = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setString(1, teamName);
            try (ResultSet rs = ps.executeQuery()) {//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    return buildTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("팀 이름 검색중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;// team not found

    }

    public List<TeamResponseDto> getAllTeam() {
        //0. collection
        List<TeamResponseDto> teamList = new ArrayList<>();

        //1. sql
        String query = "SELECT \n" +
                "t.id,\n" +
                "t.name,\n" +
                "t.created_at,\n" +
                "t.stadium_id,\n" +
                "s.name,\n" +
                "s.created_at\n" +
                "FROM  team t\n" +
                "INNER JOIN stadium s ON t.stadium_id = s.id";
        try (PreparedStatement ps = connection.prepareStatement(query)) { //2. buffer

            try (ResultSet rs = ps.executeQuery()) {//3. send , object type으로 리턴
                //4. cursor while
                while (rs.next()) {
                    //5. mapping (db result -> model)
                    TeamResponseDto teamResponseDto = new TeamResponseDto(
                            rs.getInt("t.id"),
                            rs.getString("t.name"),
                            rs.getTimestamp("t.created_at"),
                            rs.getInt("t.stadium_id"),
                            rs.getString("s.name"),
                            rs.getTimestamp("s.created_at")
                    );
                    // 6. collect
                    teamList.add(teamResponseDto);
                }
            }
            return teamList;

        } catch (SQLException e) {
            System.out.println("전체 경기장 리스트 조회중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Team buildTeamFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int teamStadiumId = rs.getInt("stadium_id");// 팀이 속한 경기장
        String teamName = rs.getString("name");// 팀 이름
        Timestamp teamTimestamp = rs.getTimestamp("created_at");// 팀 등록시간

        return Team.builder()
                .id(id)
                .stadiumId(teamStadiumId)
                .name(teamName)
                .createdAt(teamTimestamp)
                .build();
    }


}
