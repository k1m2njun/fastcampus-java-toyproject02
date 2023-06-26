package model.team;

import java.sql.Timestamp;

public class Team {

    private Integer id;
    private Integer stadiumId;
    private String name;
    private Timestamp createdAt;

    public Team (
            Integer id,
            Integer stadiumId,
            String name,
            Timestamp createdAt
    ) {
        this.id = id;
        this.stadiumId = stadiumId;
        this.name = name;
        this.createdAt = createdAt;
    }
}
