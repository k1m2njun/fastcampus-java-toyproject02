import db.DBConnection;
import dto.player.PlayerCreateRequestDto;
import exception.CustomException;
import model.outplayer.OutPlayerDao;
import model.player.Player;
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
    public static void main(String[] args) throws SQLException {
        PlayerDao playerDao = PlayerDao.getInstance();
        TeamDao teamDao = TeamDao.getInstance();
        OutPlayerDao outPlayerDao = OutPlayerDao.getInstance();
        StadiumDao stadiumDao = StadiumDao.getInstance();

        Connection connection = DBConnection.getInstance();

        playerDao.connectDB(connection);
        outPlayerDao.connectDB(connection);
        teamDao.connectDB(connection);
        stadiumDao.connectDB(connection);

        PlayerService playerService = new PlayerService(playerDao, teamDao);
        OutPlayerService outPlayerService = new OutPlayerService(outPlayerDao, playerDao);
        TeamService teamService = new TeamService(stadiumDao, teamDao);
        StadiumService stadiumService = new StadiumService(stadiumDao);

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.print("\n어떤 기능을 요청하시겠습니까? ");
            String request = scanner.nextLine();
            if(request.equals("종료")) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            try {
                connection.setAutoCommit(false);

                if (request.contains("?")) {
                    String order = request.split("[?]")[0];
                    String requestData = request.split("[?]")[1];

                    if (order.equals("선수등록")) playerService.선수등록(requestData);
                    else if (order.equals("선수목록")) playerService.선수목록(requestData);
                    else if (order.equals("야구장등록")) stadiumService.야구장등록(requestData);
                    else if (order.equals("팀등록")) teamService.팀등록(requestData);
                    else if (order.equals("퇴출등록")) outPlayerService.퇴출등록(requestData);
                    else throw new CustomException("요청을 확인하세요.");
                } else {
                    if (request.equals("야구장목록")) stadiumService.야구장목록();
                    else if (request.equals("팀목록")) teamService.팀목록();
                    else if (request.equals("퇴출목록")) outPlayerService.퇴출목록();
//                    else if (request.equals("포지션별목록")) outPlayerService.포지션별목록();
                    else throw new CustomException("요청을 확인하세요.");

                    connection.commit();
                }
            } catch (CustomException e) {
                connection.rollback();
                System.out.println(e.getMessage());
            } catch (SQLException e) {
                connection.rollback();
                System.out.println(e.getMessage());
                e.printStackTrace();
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            } finally {
                connection.setAutoCommit(true);
            }
        }
    }
}
