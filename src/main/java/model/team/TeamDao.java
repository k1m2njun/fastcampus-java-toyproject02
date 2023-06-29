package model.team;

import dto.player.PlayerCreateRequestDto;
import dto.player.PlayerGetResponseDto;
import exception.CustomException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import model.player.Player;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class TeamDao {
    private Connection connection;

    // PlayerDao Singleton
    private static class singleInstanceHolder {
        private static final TeamDao INSTANCE = new TeamDao();
    }

    public static TeamDao getInstance() {
        return TeamDao.singleInstanceHolder.INSTANCE;
    }

    public void connectDB(Connection connection) {
        this.connection = connection;
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
