package dto.outplayer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
public class OutPlayerResponseDto {

    private Integer playerId;
    private String playerName;
    private String playerPosition;
    private String outPlayerReason;
    private Timestamp outPlayerCreatedAt;

    public OutPlayerResponseDto(Integer playerId, String playerName, String playerPosition, String outPlayerReason, Timestamp outPlayerCreatedAt) {
        this.playerId = playerId;
        this.playerName = playerName;
        this.playerPosition = playerPosition;
        this.outPlayerReason = outPlayerReason;
        this.outPlayerCreatedAt = outPlayerCreatedAt;
    }

    @Override
    public String toString() {
        return "Player And OutPlayer result{" +
                "playerId=" + playerId +
                ", playerName='" + playerName + '\'' +
                ", playerPosition='" + playerPosition + '\'' +
                ", outPlayerReason(이유)='" + outPlayerReason + '\'' +
                ", outPlayerCreatedAt(퇴출일)=[" + outPlayerCreatedAt +
                "]}";
    }

}