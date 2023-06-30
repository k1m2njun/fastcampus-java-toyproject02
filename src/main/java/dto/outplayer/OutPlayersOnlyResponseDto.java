package dto.outplayer;

import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class OutPlayersOnlyResponseDto {

    private Integer playerId;

    public static OutPlayersOnlyResponseDto buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        OutPlayersOnlyResponseDto outPlayerResponseDto = new OutPlayersOnlyResponseDto();

        outPlayerResponseDto.setPlayerId(resultSet.getInt("player_id"));

        return outPlayerResponseDto;
    }
}