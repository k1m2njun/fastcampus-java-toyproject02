package dto.outplayer;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class OutPlayerResponseDto {

    private Integer idOfPlayer;
    private String nameOfPlayer;
    private String positionOfPlayer;
    private String reasonOfOutPlayer;
    private Timestamp createdAtOfOutPlayer;

    public static OutPlayerResponseDto buildPlayerFromResultSet(ResultSet resultSet) throws SQLException {
        OutPlayerResponseDto outPlayerResponseDto = new OutPlayerResponseDto();

        outPlayerResponseDto.setIdOfPlayer(resultSet.getInt("p.id"));
        outPlayerResponseDto.setNameOfPlayer(resultSet.getString("p.name"));
        outPlayerResponseDto.setPositionOfPlayer(resultSet.getString("p.position"));
        outPlayerResponseDto.setReasonOfOutPlayer(resultSet.getString("o.reason(이유)"));
        outPlayerResponseDto.setCreatedAtOfOutPlayer(resultSet.getTimestamp("o.day(퇴출일)"));

        return outPlayerResponseDto;
    }
}