package service;

import dto.player.PlayerCreateRequestDto;
import model.player.Player;
import model.player.PlayerDao;

import java.sql.SQLException;

public class PlayerService {
    private PlayerCreateRequestDto playerCreateRequestDto;
    private PlayerDao playerDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerCreateRequestDto = new PlayerCreateRequestDto();
        this.playerDao = playerDao;
    }

    public void 선수등록(String requestData) throws SQLException {

        String[] requestDataList = requestData.split("&");
        Integer requestTeamId = Integer.parseInt(requestDataList[0].split("=")[1]); // teamId=1
        String requestName = requestDataList[1].split("=")[1]; // name=이대호
        String requestPosition = requestDataList[2].split("=")[1]; // position=1루수

        playerCreateRequestDto.setTeamId(requestTeamId);
        playerCreateRequestDto.setName(requestName);
        playerCreateRequestDto.setPosition(requestPosition);

        Player player = playerCreateRequestDto.toModel();
        System.out.println(playerDao.createPlayer(player).toString());
        System.out.println("성공");
    }

    public void 선수목록() {

    }

}
