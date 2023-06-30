package model.player;

import dto.player.PlayerCreateRequestDto;
import dto.player.PlayerGetResponseDto;
import dto.position.PositionResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter @NoArgsConstructor
public class PlayerDao {
    private Connection connection;
    private PlayerGetResponseDto playerGetResponseDto;
    private PlayerCreateRequestDto playerCreateRequestDto;


    // PlayerDao Singleton
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
                    Player player = buildPlayerFromResultSet(resultSet);
                    playerList.add(player);
                }
            }
        }
        return playerList;
    }

    // 선수 방출
    public void teamOutPlayer(int id) throws SQLException {
        String query = "UPDATE player SET team_id = null WHERE id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            Player player = getPlayerById(id);

            // null check
            if (player == null) return;

            statement.setInt(1, id);

            int rowCount = statement.executeUpdate();

            if (rowCount > 0) {
                System.out.println(player.getName() + " 선수가 팀에서 퇴출됩니다.");
            }
        }
    }

    // 3.5 선수 등록, Service 분리
    // TODO - 예외처리
    public Player createPlayer(Player player) throws SQLException {
        String query = "INSERT INTO player (team_id, name, position, created_at) " +
                "VALUES (?, ?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            // null check - team_id
            if (player.getTeamId() == 0 || player.getTeamId() == null) return null;
            // null check - name
            if (player.getName().isBlank() || player.getName() == null) return null;
            // null check - position
            if (player.getPosition() == null) return null;

            // duplicate check - position
            for (Player p : getAllPlayers()) {
                if (player.getPosition().equals(p.getPosition())) return null;
            }

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

    // 포지션별 출력용
    public List<PositionResponseDto> getPlayerPositionForEachTeam(){
        //0. collection
        List<PositionResponseDto> positionList = new ArrayList<>();
        //1. sql -> query 파라미터 사용
        String query = "SELECT\n" +
                "position\n" +
                ",GROUP_CONCAT(player.name)\n" +
                "FROM player\n" +
                "GROUP BY position";
        try (PreparedStatement ps = connection.prepareStatement(query)) { //2. buffer

            try (ResultSet rs = ps.executeQuery()) {//3. send , object type으로 리턴
                //4. cursor while
                while (rs.next()) {
                    //5. mapping (db result -> model)
                    PositionResponseDto positionResponseDto = new PositionResponseDto(
                            rs.getString("position"),
                            rs.getString("GROUP_CONCAT(player.name)")
                    );
                    // 6. collect
                    positionList.add(positionResponseDto);
                }
            }
            return positionList;

        } catch (SQLException e) {
            System.out.println("전체 경기장 리스트 조회중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("getPlayerPositionForEachTeam 메서드에서 null 반환");
        return null;


    }

//    public Player createPlayer(
//            Integer teamId,
//            String name,
//            String position
//    ) throws SQLException {
//        String query = "INSERT INTO player (team_id, name, position, created_at) " +
//                "VALUES (?, ?, ?, now())";
//        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
//
//            // null check - team_id
//            if (teamId == 0 || teamId == null) return null;
//            // null check - name
//            if (name.isBlank() || name == null) return null;
//            // null check - position
//            if (position.isBlank() || position == null) return null;
//
//            // duplicate check - position
//            for (Player p : getAllPlayers()) {
//                if (position.equals(p.getPosition())) return null;
//            }
//
//            statement.setInt(1, teamId);
//            statement.setString(2, name);
//            statement.setString(3, position);
//
//            int rowCount = statement.executeUpdate();
//
//            if (rowCount > 0) {
//                System.out.println("성공");
//                return getPlayerByPosition(position, teamId);
//            }
//        }
//        return null;
//    }

    // 3.6 팀별 선수 목록 조회
//    public List<Player> getPlayersByTeam(int teamId) throws SQLException {
//        List<Player> playerList = new ArrayList<>();
//        String query = "SELECT * FROM player WHERE team_id = ?";
//        //id, name, position, created_at
//        try (PreparedStatement statement = connection.prepareStatement(query)) {
//            statement.setInt(1, teamId);
//            try (ResultSet resultSet = statement.executeQuery()) {
//                while (resultSet.next()) {
//                    Player player = buildPlayerFromResultSet(resultSet);
//                    playerList.add(player);
//                }
//            }
//        }
//        return playerList;
//    }

    // 포지션별 팀


    public List<Player> getPlayersByTeam(int teamId) throws SQLException {
        List<Player> playerResponseList = new ArrayList<>();
        String query = "SELECT id, name, position, created_at FROM player WHERE team_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, teamId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Player playerResponse = PlayerGetResponseDto.buildPlayerFromResultSet(resultSet);
                    playerResponseList.add(playerResponse);
                }
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
                    return buildPlayerFromResultSet(resultSet);
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
                    return buildPlayerFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    // player 테이블 생성
    public void createPlayerTable() throws SQLException {

        String query = "CREATE TABLE player (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "team_id INT NOT NULL, " +
                "name VARCHAR(20) NOT NULL, " +
                "position VARCHAR(20) NOT NULL, " +
                "created_at TIMESTAMP NOT NULL, " +
                "FOREIGN KEY (team_id) REFERENCES team(id))";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("[ERROR] " + e.getMessage());
        }
    }

    // Player response builder
    private Player buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int teamId = resultSet.getInt("team_id");
        String name = resultSet.getString("name");
        String position = resultSet.getString("position");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return Player.builder()
                .id(id)
                .teamId(teamId)
                .name(name)
                .position(position)
                .createdAt(createdAt)
                .build();
    }
}