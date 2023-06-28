package dto.team;

import lombok.Getter;

import java.sql.Timestamp;
@Getter
public class TeamResponseDto {
    private Integer teamId;
    private String teamName;
    private Timestamp teamCreatedAt;
    private Integer teamStadiumId;
    private String teamStadiumName;
    private Timestamp teamStadiumCreatedAt;

    public TeamResponseDto(Integer teamId, String teamName, Timestamp teamCreatedAt, Integer teamStadiumId, String teamStadiumName, Timestamp teamStadiumCreatedAt) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.teamCreatedAt = teamCreatedAt;
        this.teamStadiumId = teamStadiumId;
        this.teamStadiumName = teamStadiumName;
        this.teamStadiumCreatedAt = teamStadiumCreatedAt;
    }

    @Override
    public String toString() {
        return "TeamInfo{" +
                "teamId=" + teamId +
                ", teamName='" + teamName + '\'' +
                ", teamCreatedAt=[" + teamCreatedAt +
                "], teamStadiumId=" + teamStadiumId +
                ", teamStadiumName='" + teamStadiumName + '\'' +
                ", teamStadiumCreatedAt=[" + teamStadiumCreatedAt +
                "]}";
    }


}
