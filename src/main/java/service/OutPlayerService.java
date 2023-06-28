package service;

import dto.outplayer.OutPlayerCreateRequestDto;
import dto.outplayer.OutPlayerResponseDto;
import model.outplayer.OutPlayerDao;
import model.player.PlayerDao;

import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private PlayerDao playerDao;
    private OutPlayerDao outPlayerDao;
    private OutPlayerCreateRequestDto outPlayerCreateRequestDto;

    public OutPlayerService(OutPlayerDao outPlayerDao) {
        this.outPlayerDao = outPlayerDao;
        this.playerDao = new PlayerDao();
        this.outPlayerCreateRequestDto = new OutPlayerCreateRequestDto();
    }

    public void 퇴출등록(String requestData) throws SQLException {

        String[] requestDataList = requestData.split("&");
        Integer requestPlayerId = Integer.parseInt(requestDataList[0].split("=")[1]);
        String requestReason = requestDataList[1].split("=")[1];

        playerDao.teamOutPlayer(requestPlayerId); // 선수 퇴출

        outPlayerCreateRequestDto.setPlayerId(requestPlayerId);
        outPlayerCreateRequestDto.setReason(requestReason);

        System.out.println(outPlayerDao.createOutPlayer(outPlayerCreateRequestDto.toModel()).toString());
        System.out.println("성공");
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
