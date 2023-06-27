package model.stadium;

import lombok.Getter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Getter
public class StadiumDao {

    private Connection connection;

    private StadiumDao(Connection connection) {
        this.connection = connection;
    }

    // insert method
    public Stadium createStadium(String stadiumName) throws SQLException {
        String query = "INSERT INTO stadium(name, created_at) values(?, now());";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, stadiumName);
            int rowCount = statement.executeUpdate();
            if (rowCount > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
            }
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("경기장 등록과정에서 Exception 발생 : " + e.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }
        return null;
    }

    public List<Stadium> getAllStadium() throws SQLException {
        //0. collection
        List<Stadium> stadiumList = new ArrayList<>();
        //1. sql
        String query = "select * from stadium";
        try (PreparedStatement ps = connection.prepareStatement(query)){ //2. buffer

            try(ResultSet rs = ps.executeQuery()){//3. send , object type으로 리턴
                //4. cursor while
                while (rs.next()) {
                    //5. mapping (db result -> model)
                    Stadium stadium = new Stadium(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getTimestamp("created_at")
                    );
                    // 6. collect
                    stadiumList.add(stadium);
                }
            }
            return stadiumList;

        } catch (SQLException e) {
            System.out.println("전체 경기장 리스트 조회중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return stadiumList;
    }

    public Stadium getStadiumByName(String stadiumName) {
        //1.sql
        String query = "select * from stadium where name = ?";

        try(PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setString(1, stadiumName);
            try(ResultSet rs = ps.executeQuery()){//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    return buildStadiumFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("경기장 이름 검색중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;// stadium not found
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
