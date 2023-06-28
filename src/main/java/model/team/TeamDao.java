package model.team;

import java.sql.*;

public class TeamDao {

    private Connection connection;

    public TeamDao(Connection connection){this.connection = connection;}

    public Team createTeam(int stadiumId, String name){
        String query = "INSERT INTO team(stadiumId, name, created_at) VALUES(?, ?, now())";
        // 1. stadium_id 가 이미 등록된 팀이 있는 경우 -->

        // 2. team 이름이 중복된 경우 --> team 이름 확인해야함.

        return null;
    }

    public Team getTeamByStadiumId(int stadiumId){

        return null;
    }

    public Team getTeamByName(String teamName){
        //1.sql
        String query = "SELECT * FROM team WHERE name = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setString(1, teamName);
            try(ResultSet rs = ps.executeQuery()){//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    return buildTeamFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("경기장 이름 검색중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;// team not found

    }

    private Team buildTeamFromResultSet(ResultSet rs) throws SQLException{
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
