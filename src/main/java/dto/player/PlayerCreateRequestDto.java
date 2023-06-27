package dto.player;

import lombok.Getter;
import lombok.Setter;
import model.player.Player;

@Setter
@Getter
public class PlayerCreateRequestDto {
    private Integer teamId;
    private String name;
    private String position;

    public Player toModel() {
        Player player = Player.builder()
                .teamId(teamId)
                .name(name)
                .position(position)
                .build();
        return player;
    }
}
