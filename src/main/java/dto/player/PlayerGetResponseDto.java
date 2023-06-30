package dto.player;

import lombok.*;
import model.player.Player;
import model.player.Player.Status;

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
    private Status status;
    private Timestamp createdAt;

    public static Player buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String position = resultSet.getString("position");
        Status status = (Status) resultSet.getObject("status");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return Player.builder()
                .id(id)
                .name(name)
                .position(position)
                .createdAt(createdAt)
                .build();
    }
}