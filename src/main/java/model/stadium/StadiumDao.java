package model.stadium;


import dto.stadium.StadiumGetResponseDto;
import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StadiumDao {

    private Connection connection;

    private static class singleInstanceHolder {
        private static final StadiumDao INSTANCE = new StadiumDao();
    }

    public static StadiumDao getInstance() {
        return StadiumDao.singleInstanceHolder.INSTANCE;
    }

    public void connectDB(Connection connection) {
        this.connection = connection;
    }

    // insert method
    public Stadium createStadium(String stadiumName) throws SQLException {
        String query = "INSERT INTO stadium(name, created_at) VALUES(?, now())";
        if (getStadiumByName(stadiumName) == null){
            try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, stadiumName);
                int rowCount = statement.executeUpdate();
                if (rowCount > 0) {
                    return getStadiumByName(stadiumName);
                }
            }
        }
        return null;
    }

    public List<Stadium> getAllStadium() throws SQLException {
        List<Stadium> stadiumList = new ArrayList<>();
        String query = "SELECT * FROM stadium";
        try (PreparedStatement statement = connection.prepareStatement(query)){
            try(ResultSet resultSet = statement.executeQuery()){

                while (resultSet.next()) {
                    Stadium stadiumResponse = StadiumGetResponseDto.buildStadiumFromResultSet(resultSet);
                    stadiumList.add(stadiumResponse);
                }
            }
        }
        return stadiumList;
    }


    public Stadium getStadiumById(int id) throws SQLException {
        String query = "SELECT * FROM stadium WHERE id = ?";
        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            try(ResultSet resultSet = statement.executeQuery()){

                if (resultSet.next()) {
                    return StadiumGetResponseDto.buildStadiumFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    public Stadium getStadiumByName(String stadiumName) throws SQLException{
        String query = "SELECT * FROM stadium Where name = ?";

        try(PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, stadiumName);
            try(ResultSet resultSet = statement.executeQuery()){
                if (resultSet.next()) {
                    return StadiumGetResponseDto.buildStadiumFromResultSet(resultSet);
                }
            }
        }
        return null;
    }

    private Stadium buildStadiumFromResultSet(ResultSet rs) throws SQLException {
        int stadiumId = rs.getInt("id");// 경기장 id
        String stadiumName = rs.getString("name");// 경기장 이름
        Timestamp stadiumTimestamp = rs.getTimestamp("created_at");// 경기장 등록 시간

        return Stadium.builder()
                .id(stadiumId)
                .name(stadiumName)
                .createdAt(stadiumTimestamp)
                .build();
    }


}
