package model.outplayer;

import dto.outplayer.OutPlayerResponseDto;
import dto.outplayer.OutPlayersOnlyResponseDto;
import exception.CustomException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OutPlayerDao {
    private Connection connection;

    private static class singleInstanceHolder {
        private static final OutPlayerDao INSTANCE = new OutPlayerDao();
    }

    public static OutPlayerDao getInstance() {
        return OutPlayerDao.singleInstanceHolder.INSTANCE;
    }

    public void connectDB(Connection connection) {
        this.connection = connection;
    }

    // 퇴출 선수 id 조회 - 중복 확인용
    public List<OutPlayersOnlyResponseDto> getOnlyOutPlayers() throws SQLException {
        List<OutPlayersOnlyResponseDto> outPlayerList = new ArrayList<>();
        String query = "SELECT player_id FROM out_player";
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    OutPlayersOnlyResponseDto outPlayer = OutPlayersOnlyResponseDto.buildPlayerFromResultSet(resultSet);
                    outPlayerList.add(outPlayer);
                }
            }
        }
        return outPlayerList;
    }

    // 3.7 퇴출 선수 등록
    public OutPlayer createOutPlayer(OutPlayer outPlayer) throws SQLException {
        String query = "INSERT INTO out_player (player_id, reason, created_at) " +
                "VALUES (?, ?, now())";
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, outPlayer.getPlayerId());
            statement.setString(2, outPlayer.getReason());

            int rowCount = statement.executeUpdate();

            if (rowCount > 0) {
                return getOutPlayerByPlayerId(outPlayer.getPlayerId());
            }
        }
        return null;
    }

    private OutPlayer getOutPlayerByPlayerId(int playerId) throws SQLException {
        String query = "SELECT * FROM out_player WHERE player_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, playerId);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return buildOutPlayerFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    // 3.8 퇴출 선수 목록
    public List<OutPlayerResponseDto> getAllOutPlayers() throws SQLException {
        List<OutPlayerResponseDto> outPlayerResponseList = new ArrayList<>();
        String query =
                "SELECT p.id 'p.id', " +
                        "p.name 'p.name', " +
                        "p.position 'p.position', " +
                        "o.reason 'o.reason(이유)', " +
                        "o.created_at 'o.day(퇴출일)' " +
                "FROM player p RIGHT OUTER JOIN out_player o ON p.id = o.player_id";

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    OutPlayerResponseDto outPlayerResponse = OutPlayerResponseDto.buildPlayerFromResultSet(resultSet);
                    outPlayerResponseList.add(outPlayerResponse);
                }
            }
        }
        return outPlayerResponseList;
    }

    // OutPlayer response builder
    private OutPlayer buildOutPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int playerId = resultSet.getInt("player_id");
        String reason = resultSet.getString("reason");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return OutPlayer.builder()
                .id(id)
                .playerId(playerId)
                .reason(reason)
                .createdAt(createdAt)
                .build();
    }
}
