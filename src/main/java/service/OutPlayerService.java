package service;

import dto.outplayer.OutPlayerCreateRequestDto;
import dto.outplayer.OutPlayerResponseDto;
import model.outplayer.OutPlayerDao;
import model.player.Player;

import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private OutPlayerDao outPlayerDao;
    private OutPlayerCreateRequestDto outPlayerCreateRequestDto;

    public OutPlayerService(OutPlayerDao outPlayerDao) {
        this.outPlayerDao = outPlayerDao;
        this.outPlayerCreateRequestDto = new OutPlayerCreateRequestDto();
    }

    public void 퇴출선수등록(String requestData) throws SQLException {

        String[] requestDataList = requestData.split("&");
        Integer requestPlayerId = Integer.parseInt(requestDataList[0].split("=")[1]); // teamId=1
        String requestReason = requestDataList[1].split("=")[1]; // name=이대호

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
