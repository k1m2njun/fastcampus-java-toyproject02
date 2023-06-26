package model.player;

import lombok.Getter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
public class PlayerDao {
    private Connection connection;
    public PlayerDao(Connection connection) {
        this.connection = connection;
    }

    public void createPlayerTable() throws SQLException {
        String query = "CREATE TABLE player(" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "team_id INT NOT NULL, " +
                "name VARCHAR(20) NOT NULL, " +
                "position VARCHAR(20) NOT NULL, " +
                "created_at TIMESTAMP NOT NULL)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }
}
