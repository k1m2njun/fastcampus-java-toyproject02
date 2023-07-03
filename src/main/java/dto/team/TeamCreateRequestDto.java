package dto.team;

import lombok.Getter;
import lombok.Setter;
import model.team.Team;

import java.sql.Timestamp;

@Setter
@Getter
public class TeamCreateRequestDto {
    private Integer stadiumId;
    private String name;
    private Timestamp createdAt;

    public Team toModel() {

        return Team.builder()
                .stadiumId(stadiumId)
                .name(name)
                .createdAt(createdAt)
                .build();
    }

}
