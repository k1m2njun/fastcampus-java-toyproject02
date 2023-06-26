package model.player;

import java.sql.Timestamp;

public class Player {
    private Integer id;
    private Integer teamId;
    private String name;
    private String position;
    private Timestamp createdAt;

    public Player(
            Integer id,
            Integer teamId,
            String name,
            String position,
            Timestamp createdAt
    ) {
        this.id = id;
        this.teamId = teamId;
        this.name = name;
        this.position = position;
        this.createdAt = createdAt;
    }
}
