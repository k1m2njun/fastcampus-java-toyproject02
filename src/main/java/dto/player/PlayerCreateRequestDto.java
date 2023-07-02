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

}