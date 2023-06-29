package dto.team;

import lombok.Getter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@ToString
public class TeamResponseDto {
    private Integer teamId;
    private String teamName;
    private Timestamp teamCreatedAt;
    private Integer teamStadiumId;
    private String teamStadiumName;
    private Timestamp teamStadiumCreatedAt;

    public TeamResponseDto(
            Integer teamId,
            String teamName,
            Timestamp teamCreatedAt,
            Integer teamStadiumId,
            String teamStadiumName,
            Timestamp teamStadiumCreatedAt) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCreatedAt = teamCreatedAt;
        this.teamStadiumId = teamStadiumId;
        this.teamStadiumName = teamStadiumName;
        this.teamStadiumCreatedAt = teamStadiumCreatedAt;
    }
}
