package dto.player;

import lombok.*;
import model.player.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class PlayerGetResponseDto {
    private Integer id;
    private String name;
    private String position;
    private Timestamp createdAt;

//    public Player toModel() {
//        Player player = Player.builder()
//                .id(id)
//                .name(name)
//                .position(position)
//                .createdAt(createdAt)
//                .build();
//        return player;
//    }
    @Builder
    public PlayerGetResponseDto (
            Integer id,
            String name,
            String position,
            Timestamp createdAt
    ) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
    }

    public static PlayerGetResponseDto buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String position = resultSet.getString("position");
        Timestamp createdAt = resultSet.getTimestamp("created_at");

        return PlayerGetResponseDto.builder()
                .id(id)
                .name(name)
                .position(position)
                .createdAt(createdAt)
                .build();
    }
}
