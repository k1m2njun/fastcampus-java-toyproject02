import db.DBConnection;
import model.outplayer.OutPlayerDao;
import model.player.PlayerDao;
import model.stadium.StadiumDao;
import model.team.TeamDao;
import service.OutPlayerService;
import service.PlayerService;
import service.StadiumService;
import service.TeamService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

public class BaseBallApplication {

    static final PlayerDao playerDao = PlayerDao.getInstance();
    public static void main(String[] args) throws SQLException {
        Connection connection = DBConnection.getInstance();
        //-------------------------------------------------//
        playerDao.connectDB(connection);
        PlayerService playerService = new PlayerService(playerDao);


        //------------------------------------------------//
        StadiumDao stadiumDao = new StadiumDao(connection);
        TeamDao teamDao = new TeamDao(connection);
        OutPlayerDao outPlayerDao = new OutPlayerDao(connection);


        //------------------------------------------------//

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
//            for(String s: words){// 배열 확인
//                System.out.println(s);
//            }

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
                if (order.equals("팀등록")) {//3.예시 -- 요청 : 팀등록?stadiumId=4&name=NC
                    int stadiumId = Integer.parseInt(words[2]);
                    String teamName = words[4];
                    connection.setAutoCommit(false);
                    TeamService teamService = new TeamService(stadiumDao,teamDao,connection);
                    teamService.registerNewTeam(stadiumId, teamName);
                    connection.commit();
                    flag++;
                    continue;
                }
                if (order.equals("팀목록")) {//4.예시 -- 요청 : 팀목록
                    TeamService teamService = new TeamService(stadiumDao,teamDao,connection);
                    teamService.getTeamList();
                    continue;
                }
                if (order.equals("선수등록")) {//5.요청 : 선수등록?teamId=1&name=이대호&position=1루수

                    continue;
                }
                if (order.equals("선수목록")) {//6.요청 : 선수목록?teamId=1

                    continue;
                }
                if (order.equals("퇴출등록")) {//7.요청 : 퇴출등록?playerId=1&reason=도박
                    int outPlayerId = Integer.parseInt(words[2]);
                    String outPlayerReason = words[4];
                    System.out.println(outPlayerId+" "+outPlayerReason);
                    OutPlayerService outPlayerService = new OutPlayerService(outPlayerDao,playerDao,connection);
                    outPlayerService.registerOutPlayer(outPlayerId, outPlayerReason);
                    continue;
                }
                if (order.equals("퇴출목록")) {//8.요청 : 퇴출목록
                    OutPlayerService outPlayerService = new OutPlayerService(outPlayerDao,playerDao,connection);
                    outPlayerService.getOutPlayerList();
                    continue;
                }
                if (order.equals("포지션별목록")) {//10.요청 : 포지션별목록

                    continue;
                }
            } catch (SQLException e) {
                connection.rollback();
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
