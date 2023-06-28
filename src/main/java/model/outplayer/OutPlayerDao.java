package model.outplayer;

import dto.outplayer.OutPlayerResponseDto;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static dto.outplayer.OutPlayerResponseDto.buildPlayerFromResultSet;

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

    // 퇴출 선수 목록
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
                    OutPlayerResponseDto outPlayerResponse = buildPlayerFromResultSet(resultSet);
                    outPlayerResponseList.add(outPlayerResponse);
                }
            }
        }
        return outPlayerResponseList;
    }
}
