import db.DBConnection;
import model.player.Player;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseBallApplication {

    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();

        PlayerDao playerDao = PlayerDao.getInstance();

        playerDao.connectDB(connection);

        try {
//            playerDao.createPlayerTable();
//            for(Player p : playerDao.getPlayersByTeam(2)) {
//                System.out.println(p.toString());
//            }
            System.out.println(playerDao.getPlayerByPosition("1루수", 2).toString());

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
