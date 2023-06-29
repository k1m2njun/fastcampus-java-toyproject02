package dto.stadium;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import model.stadium.Stadium;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
public class StadiumGetResponseDto {
    private Integer id;
    private String name;
    private Timestamp createdAt;

    public static Stadium buildStadiumFromResultSet(ResultSet resultSet) throws SQLException {
        int stadiumId = resultSet.getInt("id");// 경기장 id
        String stadiumName = resultSet.getString("name");// 경기장 이름
        Timestamp stadiumTimestamp = resultSet.getTimestamp("created_at");// 경기장 등록 시간

        return Stadium.builder()
                .id(stadiumId)
                .name(stadiumName)
                .createdAt(stadiumTimestamp)
                .build();
    }
}
