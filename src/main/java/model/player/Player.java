package model.player;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class Player {
    private Integer id;
    private Integer teamId;
    private String name;
    private String position;
    private Timestamp createdAt;

    @Builder
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
