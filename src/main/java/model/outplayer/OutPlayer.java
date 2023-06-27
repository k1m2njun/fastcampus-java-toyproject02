package model.outplayer;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class OutPlayer {

    private Integer id;
    private Integer playerId;
    private String reason;
    private Timestamp createdAt;

    @Builder
    public OutPlayer(
            Integer id,
            Integer playerId,
            String reason,
            Timestamp createdAt
    ) {
        this.id = id;
        this.playerId = playerId;
        this.reason = reason;
        this.createdAt = createdAt;
    }
}
