package service;

import dto.outplayer.OutPlayerResponseDto;
import model.outplayer.OutPlayerDao;
import model.player.Player;

import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private OutPlayerDao outPlayerDao;

    public OutPlayerService(OutPlayerDao outPlayerDao) {
        this.outPlayerDao = outPlayerDao;
    }

    public void 퇴출목록() throws SQLException {

        List<OutPlayerResponseDto> outPlayerResponseList = outPlayerDao.getAllOutPlayers();
        if (outPlayerResponseList.size() == 0) {
            System.out.println("퇴출 선수가 없습니다.");
            return;
        }
        for(OutPlayerResponseDto outPlayerResponse : outPlayerResponseList) {
            System.out.println(outPlayerResponse.toString());
        }
    }
}
