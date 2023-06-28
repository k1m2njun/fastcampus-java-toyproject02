import db.DBConnection;
import model.stadium.StadiumDao;
import service.StadiumService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BaseBallApplication {

    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getInstance();
        //PlayerDao playerDao = new PlayerDao(connection);
        StadiumDao stadiumDao = new StadiumDao(connection);

        Scanner scanner = new Scanner(System.in);
        String request = "";
        String requestData = "";
        String order = "";
        int flag = 0;

        String[] words = null;

        while (true) {
            flag = 0;
            System.out.println("어떤 기능을 요청하시겠습니까?");
            request = scanner.nextLine();
            if (request.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            words = request.split("\\?|=|&");
            order = words[0];//
            for(String s: words){
                System.out.println(s);
            }

            try {
                if (order.equals("야구장등록")) {//1.예시 --요청 : 야구장등록?name=잠실야구장
                    String stadiumName = words[2];
                    connection.setAutoCommit(false);
                    StadiumService stadiumService = new StadiumService(stadiumDao,connection);
                    stadiumService.registerNewStadium(stadiumName);
                    connection.commit();
                    flag++;
                    continue;
                }
                if (order.equals("야구장목록")) {//2.예시 -- 요청 : 야구장목록
                    StadiumService stadiumService = new StadiumService(stadiumDao,connection);
                    stadiumService.getStadiumList();
                    flag++;
                    continue;
                }
                if (order.equals("팀등록")) {//3.예시 -- 요청 : 팀등록?stadiumId=1&name=NC
                    String teamName = words[2];
                    connection.setAutoCommit(false);

                    connection.commit();
                    flag++;
                    continue;
                }
                if (order.equals("팀목록")) {//4.예시 -- 요청 : 팀목록

                    continue;
                }
                if (order.equals("선수등록")) {//요청 : 선수등록?teamId=1&name=이대호&position=1루수

                    continue;
                }
                if (order.equals("선수목록")) {//요청 : 선수목록?teamId=1

                    continue;
                }
                if (order.equals("퇴출등록")) {//요청 : 퇴출등록?playerId=1&reason=도박

                    continue;
                }
                if (order.equals("퇴출목록")) {//요청 : 퇴출목록

                    continue;
                }
                if (order.equals("포지션별목록")) {//요청 : 포지션별목록

                    continue;
                }
            } catch (SQLException e) {
                System.out.println("[ERROR]" + e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
            if (flag == 0) {// 올바른 기능을 요청하지 않았을때.
                System.out.println("올바른 기능이 아닙니다. 다시 시도해주세요");
            }


        }


    }
}
