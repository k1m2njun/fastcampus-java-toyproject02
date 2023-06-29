package service;

import dto.outplayer.OutPlayerCreateRequestDto;
import dto.outplayer.OutPlayerResponseDto;
import dto.outplayer.OutPlayersOnlyResponseDto;
import exception.CustomException;
import model.outplayer.OutPlayerDao;
import model.player.Player;
import model.player.PlayerDao;
import model.team.TeamDao;

import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private PlayerDao playerDao;
    private OutPlayerDao outPlayerDao;
    private OutPlayerCreateRequestDto outPlayerCreateRequestDto;

    public OutPlayerService(OutPlayerDao outPlayerDao, PlayerDao playerDao) {
        this.outPlayerDao = outPlayerDao;
        this.playerDao = playerDao;
        this.outPlayerCreateRequestDto = new OutPlayerCreateRequestDto();
    }

    public void 퇴출등록(String requestData) throws SQLException { // 퇴출등록?playerId=1&reason=도박

        try {
            String[] requestDataList = requestData.split("&");
            Integer requestPlayerId = Integer.parseInt(requestDataList[0].split("=")[1]);
            String requestReason = requestDataList[1].split("=")[1];

            Player player = playerDao.getPlayerById(requestPlayerId);
            if (player == null) throw new CustomException("요청한 id(" + requestPlayerId + ")의 선수가 없습니다.");

            if (requestPlayerId == null) throw new CustomException("선수 id를 입력해주세요.");
            if (requestReason.isBlank() || requestReason == null) throw new CustomException("퇴출 사유를 입력해주세요.");

            for (OutPlayersOnlyResponseDto p : outPlayerDao.getOnlyOutPlayers()) {
                if (requestPlayerId == p.getPlayerId()) throw new CustomException("이미 퇴출된 선수입니다.");
            }

            playerDao.teamOutPlayer(requestPlayerId); // 선수 퇴출

            outPlayerCreateRequestDto.setPlayerId(requestPlayerId);
            outPlayerCreateRequestDto.setReason(requestReason);

            System.out.println(outPlayerDao.createOutPlayer(outPlayerCreateRequestDto.toModel()).toString());
            System.out.println("성공");
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void 퇴출목록() throws SQLException {

        try {
            List<OutPlayerResponseDto> outPlayerResponseList = outPlayerDao.getAllOutPlayers();

            if (outPlayerResponseList.size() == 0) throw new CustomException("퇴출 선수가 없습니다.");

            for(OutPlayerResponseDto outPlayerResponse : outPlayerResponseList) {
                System.out.println(outPlayerResponse.toString());
            }
        } catch (CustomException e) {
            System.out.println(e.getMessage());
        }

    }
}
