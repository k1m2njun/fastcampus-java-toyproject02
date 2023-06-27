package model.stadium;

import lombok.Getter;

import java.sql.*;
import java.util.List;

@Getter
public class StadiumDao {

    private Connection connection;
    private StadiumDao(Connection connection){this.connection = connection;}

    public Stadium registerStadium(String stadiumName) throws SQLException {
        String query = "INSERT INTO stadium(name, created_at) values(?, now());";
        //String query2 = "INSERT INTO account_tb (account_number, account_password, account_balance, account_created_at) VALUES (?, ?, ?, now())";

        try(PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, stadiumName);
            int rowCount = statement.executeUpdate();
            if(rowCount>0){
                ResultSet generatedKeys = statement.getGeneratedKeys() ;
            }
        }catch (SQLException e){
            connection.rollback();
            System.out.println("경기장 등록과정에서 Exception 발생 : "+e.getMessage());
        }finally {
            connection.setAutoCommit(true);
        }
        return null;
    }

    public List<Stadium> getAllStadium()throws SQLException{

    }

    public Stadium getStadiumByName(String stadiumName){
        //1.sql
        String query = "select * from stadium where name = ?";
        try{
            //2.buffer에 넣고
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, stadiumName);

            //3.send
            ResultSet rs = statement.executeQuery();// object type으로 리턴

            //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요

            if(rs.next()){// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                return buildStadiumFromResultSet(rs);
            }

        }catch (SQLException e){
            System.out.println("경기장 이름 검색중 에러발생 : "+ e.getMessage());
            System.out.println("에러 추적");
            e.printStackTrace();
        }
        return null;// stadium not found
    }

    private Stadium buildStadiumFromResultSet(ResultSet resultSet) throws SQLException{
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
