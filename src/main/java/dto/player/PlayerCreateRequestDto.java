package dto.player;

import lombok.Getter;
import lombok.Setter;
import model.player.Player;

import java.sql.Timestamp;

@Setter
@Getter
public class PlayerCreateRequestDto {
    private Integer teamId;
    private String name;
    private String position;
    private Timestamp createdAt;

    public Player toModel() {

        return Player.builder()
                .teamId(teamId)
                .name(name)
                .position(position)
                .createdAt(createdAt)
                .build();
    }

//    @Builder
//    public PlayerCreateRequestDto(Integer teamId, String name, String position) {
//        this.teamId = teamId;
//        this.name = name;
//        this.position = position;
//    }
//
//    public PlayerCreateRequestDto buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
//        Integer teamId = resultSet.getInt("team_id");
//        String name = resultSet.getString("name");
//        String position = resultSet.getString("position");
//
//        return PlayerCreateRequestDto.builder()
//                .teamId(teamId)
//                .name(name)
//                .position(position)
//                .build();
//    }
}