package model.stadium;

import java.sql.Timestamp;

public class Stadium {

    private Integer id;
    private String name;
    private Timestamp createdAt;

    public Stadium (
            Integer id,
            String name,
            Timestamp createdAt
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
    }

}
