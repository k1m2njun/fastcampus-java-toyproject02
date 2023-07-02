package model.team;


import dto.team.TeamResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TeamDao {
    private Connection connection;

    private static class singleInstanceHolder {
        private static final TeamDao INSTANCE = new TeamDao();
    }

    public static TeamDao getInstance() {
        return TeamDao.singleInstanceHolder.INSTANCE;
    }

    public void connectDB(Connection connection) {
        this.connection = connection;
    }

    // 1. 해당 경기장이 존재 여부 확인 -> 존재해야함. [TeamDao의 stadiumId로 StadiumDao의 id 검색]
    // 2. 해당 경기장에 팀이 등록된 상태인지 확인  -> 등록이 안된 상태여야함. [TeamDao의 name으로 팀 전체 select해서 검색해서 확인]
    // 3. 등록하는 팀이름이 중복되지 않아야함. -> [TeamDao 사용]
    // 4.. 1, 2, 3을 만족해야 팀으로 등록가능함.
    public Team createTeam(Team team) throws SQLException {
        String query = "INSERT INTO team(stadium_id, name, created_at) VALUES(?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, team.getStadiumId());
            statement.setString(2, team.getName());

            int rowCount = statement.executeUpdate();
            if (rowCount > 0) {
                return getTeamByStadiumId(team.getStadiumId());
            }
        }
        return null;
    }

    public Team getTeamByStadiumId(int stadiumId) throws SQLException {
        String query = "SELECT * FROM team WHERE stadium_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, stadiumId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildTeamFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public Team getTeamByName(String teamName) throws SQLException {
        String query = "SELECT * FROM team WHERE name = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, teamName);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return buildTeamFromResultSet(rs);
                }
            }
        }
        return null;// team not found
    }

    public List<TeamResponseDto> getAllTeam() throws SQLException {
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
                "INNER JOIN stadium s ON t.stadium_id = s.id;";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    TeamResponseDto teamRespDto = new TeamResponseDto(
                            rs.getInt("t.id"),
                            rs.getString("t.name"),
                            rs.getTimestamp("t.created_at"),
                            rs.getInt("t.stadium_id"),
                            rs.getString("s.name"),
                            rs.getTimestamp("s.created_at")
                    );

                    teamList.add(teamRespDto);
                }
            }
        }
        return teamList;
    }

    private Team buildTeamFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int teamStadiumId = resultSet.getInt("stadium_id");
        String name = resultSet.getString("name");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return Team.builder()
                .id(id)
                .stadiumId(teamStadiumId)
                .name(name)
                .createdAt(createdAt)
                .build();
    }

    public List<Integer> getAllTeamId() throws SQLException {
        List<Integer> teamIdList = new ArrayList<>();
        String query = "SELECT id FROM team";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    teamIdList.add(resultSet.getInt("id"));
                }
            }
        }
        return teamIdList;
    }

    public int getTeamCount() throws SQLException {
        //1.sql
        String query = "SELECT count(*)\n" +
                "FROM team";
        int count = 0;
        try (PreparedStatement ps = connection.prepareStatement(query)) { //2. buffer
            try (ResultSet rs = ps.executeQuery()) {//3. send , object type으로 리턴
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    count = rs.getInt("count(*)");
                    return count;
                }
            }
            return count;

        }
    }




    public String getTeamNameByTeamId(int teamId) throws SQLException {
        //1.sql
        String query = "select *\n" +
                "from team\n" +
                "where id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setInt(1, teamId);
            try (ResultSet rs = ps.executeQuery()) {//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    String teamName = rs.getString("name");// 팀 이름
                    return teamName;
                }
            }
        }
        return null;// team not found
    }


}
