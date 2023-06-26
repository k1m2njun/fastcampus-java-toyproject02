import db.DBConnection;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseBallApplication {

    public static void main(String[] args) {
        Connection connection = DBConnection.getInstance();
        PlayerDao playerDao = new PlayerDao(connection);

        try {
            playerDao.createPlayerTable();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
