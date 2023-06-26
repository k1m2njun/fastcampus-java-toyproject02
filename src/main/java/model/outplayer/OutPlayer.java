package model.outplayer;

import java.sql.Timestamp;

public class OutPlayer {

    private Integer id;
    private Integer playerId;
    private String reason;
    private Timestamp createdAt;

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
