package dto.player;

import lombok.*;
import model.player.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class PlayerGetResponseDto {
    private Integer id;
    private String name;
    private String position;
    private Timestamp createdAt;

    public static Player buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String position = resultSet.getString("position");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return Player.builder()
                .id(id)
                .name(name)
                .position(position)
                .createdAt(createdAt)
                .build();
    }

    public static Player buildPlayerFromResultSetFull(ResultSet resultSet) throws SQLException {
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
