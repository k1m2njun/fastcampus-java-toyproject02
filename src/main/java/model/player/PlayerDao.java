package model.player;

import dto.player.PlayerGetResponseDto;
import dto.player.PositionResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
public class PlayerDao {
    private Connection connection;

    private static class singleInstanceHolder {
        private static final PlayerDao INSTANCE = new PlayerDao();
    }

    public static PlayerDao getInstance() {
        return singleInstanceHolder.INSTANCE;
    }

    public void connectDB(Connection connection) {
        this.connection = connection;
    }

    // 전체 선수 목록
    public List<Player> getAllPlayers() throws SQLException {
        List<Player> playerList = new ArrayList<>();
        String query = "SELECT * FROM player";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    Player player = PlayerGetResponseDto.buildPlayerFromResultSetFull(resultSet);
                    playerList.add(player);
                }
            }
        }
        return playerList;
    }

    // 선수 방출
    public void teamOutPlayer(int id) throws SQLException{
        String query = "UPDATE player SET team_id = null WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Player player = getPlayerById(id);

            statement.setInt(1, id);

            int rowCount = statement.executeUpdate();
            if (rowCount > 0) {
                System.out.println(player.getName() + " 선수를 팀에서 퇴출합니다.");
            }
        }
    }

    // 3.5 선수 등록, Service 분리
    public Player createPlayer(Player player) throws SQLException {
        String query = "INSERT INTO player (team_id, name, position, created_at) " +
                "VALUES (?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, player.getTeamId());
            statement.setString(2, player.getName());
            statement.setString(3, player.getPosition());

            int rowCount = statement.executeUpdate();
            if (rowCount > 0) {
                return getPlayerByPosition(player.getPosition(), player.getTeamId());
            }
        }
        return null;
    }

    public List<Player> getPlayersByTeam(int teamId) throws SQLException{
        List<Player> playerResponseList = new ArrayList<>();
        String query = "SELECT id, name, position, created_at FROM player WHERE team_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Player playerResponse = PlayerGetResponseDto.buildPlayerFromResultSet(resultSet);
                playerResponseList.add(playerResponse);
            }
        }
        return playerResponseList;
    }

    // id로 선수 검색
    public Player getPlayerById(int id) throws SQLException {
        String query = "SELECT * FROM player WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return PlayerGetResponseDto.buildPlayerFromResultSetFull(resultSet);
                }
            }
        }
        return null;
    }

    // 팀별 포지션으로 선수 검색
    public Player getPlayerByPosition(String position, int teamId) throws SQLException {
        String query = "SELECT * FROM player WHERE team_id = ? AND position = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, teamId);
            statement.setString(2, position);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return PlayerGetResponseDto.buildPlayerFromResultSetFull(resultSet);
                }
            }
        }
        throw new RuntimeException();
    }

    // player 테이블 생성
//    public void createPlayerTable() throws SQLException {
//
//        String query = "CREATE TABLE player (" +
//                "id INT AUTO_INCREMENT PRIMARY KEY, " +
//                "team_id INT, " +
//                "name VARCHAR(20) NOT NULL, " +
//                "position VARCHAR(20) NOT NULL, " +
//                "created_at TIMESTAMP NOT NULL, " +
//                "FOREIGN KEY (team_id) REFERENCES team(id)), " +
//                "UNIQUE(team_id, position)";
//
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("[ERROR] " + e.getMessage());
//        }
//    }

    // Player response builder

    /// ************************JunHoMun branch 작업 START************************//
    //문 - 포지션별 출력용
    public int getPlayerTeamIdByName(String playerName) throws SQLException{
        String query = "SELECT team_id FROM player WHERE name = ?";
        int playerId = -1;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, playerName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int teamId = resultSet.getInt("name");
                    return teamId;
                }
            }
        }
        return 0;// player not found
    }

    // 문 - 포지션별 출력용
    public List<PositionResponseDto> getPlayerPositionForEachTeam() throws SQLException{
        List<PositionResponseDto> positionList = new ArrayList<>();
        String query = "SELECT position ,GROUP_CONCAT(player.name,'[',player.team_id,']') " +
                "FROM player GROUP BY position";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    PositionResponseDto positionResponseDto = new PositionResponseDto(
                            resultSet.getString("position"),
                            resultSet.getString("GROUP_CONCAT(player.name,'[',player.team_id,']')")
                    );
                    positionList.add(positionResponseDto);
                }
            }
            return positionList;
        }
    }
    // ************************JunHoMun branch 작업 END************************//

}
