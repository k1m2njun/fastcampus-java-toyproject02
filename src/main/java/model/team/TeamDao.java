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

    public Team getTeamByName(String teamName) throws SQLException{
        String query = "SELECT * FROM team WHERE name = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, teamName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildTeamFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public List<TeamResponseDto> getAllTeam() throws SQLException{
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
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    TeamResponseDto teamRespDto = new TeamResponseDto(
                            resultSet.getInt("t.id"),
                            resultSet.getString("t.name"),
                            resultSet.getTimestamp("t.created_at"),
                            resultSet.getInt("t.stadium_id"),
                            resultSet.getString("s.name"),
                            resultSet.getTimestamp("s.created_at")
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


}
