package model.outplayer;

import dto.outplayer.OutPlayerResponseDto;
import dto.team.TeamResponseDto;
import model.team.Team;

import java.sql.*;
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
        String query = "SELECT \n" +
                "p.id,\n" +
                "p.name,\n" +
                "p.position,\n" +
                "o.reason,\n" +
                "o.created_at\n" +
                "FROM  out_player o\n" +
                "RIGHT OUTER JOIN player p ON o.player_id = p.id";

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

    public OutPlayer createOutPlayer(int playerId, String reason){
        String query = "INSERT INTO out_player(player_id, reason, created_at) VALUES(?,?,now())";
        try (PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, playerId);
            ps.setString(2, reason);
            int rowCount = ps.executeUpdate();
            if (rowCount > 0) {
                return getOutPlayerByPlayerId(playerId);
            }
        } catch (SQLException e) {
            System.out.println("퇴출 팀 등록과정에서 Exception 발생 : " + e.getMessage());
            e.printStackTrace();
        }

        return null;// 퇴출 선수 등록 실패
    }

    public OutPlayer getOutPlayerByPlayerId(int playerId){
        //1.sql
        String query = "SELECT * FROM out_player WHERE player_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {//2.buffer에 넣고
            ps.setInt(1, playerId);
            try (ResultSet rs = ps.executeQuery()) {//3.send ->  object type으로 리턴
                //4.mapping( db result -> model) 결과는 테이블 데이터임. 이걸 자바로 매칭해주는것이 필요
                if (rs.next()) {// 커서 내리기 -> data 가 있으면 true 리턴 없으면 , false 리턴
                    return buildOutPlayerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("퇴출선수 목록에서 playerId 검색중 에러발생 : " + e.getMessage());
            e.printStackTrace();
        }
        return null;// outPlayer not found

    }



    private OutPlayer buildOutPlayerFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int playerId = rs.getInt("player_id");// 팀이 속한 경기장
        String reason = rs.getString("reason");// 팀 이름
        Timestamp teamTimestamp = rs.getTimestamp("created_at");// 팀 등록시간

        return OutPlayer.builder()
                .id(id)
                .playerId(playerId)
                .reason(reason)
                .createdAt(teamTimestamp)
                .build();
    }

}