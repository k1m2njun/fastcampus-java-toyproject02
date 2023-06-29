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

//    @Getter
//    @RequiredArgsConstructor
//    public enum Position {
//        PITCHER("투수"),
//        CATCHER("포수"),
//        FIRST_BASEMAN("1루수"),
//        SECOND_BASEMAN("2루수"),
//        THIRD_BASEMAN("3루수"),
//        SHORTSTOP("유격수"),
//        LEFT_FIELDER("좌익수"),
//        CENTER_FIELDER("중견수"),
//        RIGHT_FIELDER("우익수");
//
//        private final String positionName;
//    }
}
