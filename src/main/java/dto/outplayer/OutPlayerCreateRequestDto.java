package dto.outplayer;

import constant.Reason;
import lombok.Getter;
import lombok.Setter;
import model.outplayer.OutPlayer;
import model.player.Player;

import java.sql.Timestamp;

@Setter
@Getter
public class OutPlayerCreateRequestDto {
    private Integer playerId;
//    private String reason;
    private Reason reason;
    private Timestamp createdAt;

    public OutPlayer toModel() {

        return OutPlayer.builder()
                .playerId(playerId)
                .reason(reason)
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
