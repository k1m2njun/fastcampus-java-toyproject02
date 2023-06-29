package model.outplayer;

import dto.outplayer.OutPlayerResponseDto;
import dto.team.TeamResponseDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OutPlayerDao {

    private Connection connection;

    public OutPlayerDao(Connection connection) {
        this.connection = connection;
    }

    public List<OutPlayerResponseDto> getAllPlayerAndOutPlayerList(){
        //0. collection
        List<OutPlayerResponseDto> outPlayerList = new ArrayList<>();

        //1. sql
        String query = "select \n" +
                "p.id,\n" +
                "p.name,\n" +
                "p.position,\n" +
                "o.reason,\n" +
                "o.created_at\n" +
                "from  out_player o\n" +
                "right outer join player p on o.player_id = p.id";

        try (PreparedStatement ps = connection.prepareStatement(query)) { //2. buffer

            try (ResultSet rs = ps.executeQuery()) {//3. send , object type으로 리턴
                //4. cursor while
                while (rs.next()) {
                    //5. mapping (db result -> model)
                    OutPlayerResponseDto OutPlayerResponseDto = new OutPlayerResponseDto(
                            rs.getInt("p.id"),
                            rs.getString("p.name"),
                            rs.getString("p.position"),
                            rs.getString("o.reason"),
                            rs.getTimestamp("o.created_at")
                    );
                    // 6. collect
                    outPlayerList.add(OutPlayerResponseDto);
                }
            }
            return outPlayerList;

        } catch (SQLException e) {
            System.out.println("전체 경기장 리스트 조회중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return outPlayerList;
    }
}