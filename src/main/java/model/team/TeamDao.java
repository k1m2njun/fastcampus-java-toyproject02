package model.team;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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

    public Team getTeamByName(String name){

        return null;
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
