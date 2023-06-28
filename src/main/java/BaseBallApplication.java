import db.DBConnection;
import dto.player.PlayerCreateRequestDto;
import model.outplayer.OutPlayerDao;
import model.player.Player;
import model.player.PlayerDao;
import service.OutPlayerService;
import service.PlayerService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BaseBallApplication {

    static final PlayerDao playerDao = PlayerDao.getInstance();
    static final OutPlayerDao outPlayerDao = OutPlayerDao.getInstance();
    static final Connection connection = DBConnection.getInstance();

    public static void main(String[] args) throws SQLException {
        playerDao.connectDB(connection);
        outPlayerDao.connectDB(connection);
        PlayerService playerService = new PlayerService(playerDao);
        OutPlayerService outPlayerService = new OutPlayerService(outPlayerDao);

        Scanner scanner = new Scanner(System.in);
        String request = "";
        String requestData = "";
        String order = "";

        while(true) {
            System.out.println("어떤 기능을 요청하시겠습니까? ");
            request = scanner.nextLine(); // "선수등록?teamId=1&name=이대호&position=1루수"
            if(request.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            if(request.contains("?")) {
                // 기본 파싱 ?
                order = request.split("[?]")[0]; // "선수등록"
                System.out.println(order + "을 요청합니다.");
                requestData = request.split("[?]")[1]; // "teamId=1&name=이대호&position=1루수"

                try {
                    if(order.equals("선수등록")) playerService.선수등록(requestData); // 3.5 - 선수 등록
                    if(order.equals("선수목록")) playerService.선수목록(requestData); // 3.6 - 팀 별 선수 목록 조회

                    if(order.equals("야구장등록")) {
                        // 야구장등록?name=잠실야구장
                    }
                    if(order.equals("팀등록")) {
                        // 팀등록?stadiumId=1&name=NC
                    }
                    // 퇴출등록?playerId=1&reason=도박
                    if(order.equals("퇴출등록")) outPlayerService.퇴출선수등록(requestData);

                } catch (SQLException e){
                    System.out.println("[ERROR]" + e.getMessage());
                    //throw new RuntimeException(e);
                }
            } else System.out.println(request + "을 요청합니다.");

            if(request.equals("야구장목록")) {

            }
            if(request.equals("팀목록")) {

            }
            if(request.equals("퇴출목록")) outPlayerService.퇴출목록();
        }
    }
}
