package service;

import dto.player.PlayerCreateRequestDto;
import dto.player.PlayerGetResponseDto;
import dto.position.PositionResponseDto;
import dto.team.TeamResponseDto;
import model.player.Player;
import model.player.PlayerDao;
import model.team.TeamDao;

import java.sql.SQLException;
import java.util.List;

public class PlayerService {
    private PlayerCreateRequestDto playerCreateRequestDto;
    private PlayerGetResponseDto playerGetResponseDto;
    private PlayerDao playerDao;
    private TeamDao teamDao;

    public PlayerService(PlayerDao playerDao) {
        this.playerCreateRequestDto = new PlayerCreateRequestDto();
        this.playerGetResponseDto = new PlayerGetResponseDto();
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

        System.out.println(playerDao.createPlayer(playerCreateRequestDto.toModel()).toString());
        System.out.println("성공");
    }

    public void 선수목록(String requestData) throws SQLException {

        Integer requestTeamId = Integer.parseInt(requestData.split("=")[1]); // teamId=1

        List<Player> playerResponseList = playerDao.getPlayersByTeam(requestTeamId);
        for(Player playerResponse : playerResponseList) {
            System.out.println(playerResponse.toString());
        }
    }

    //
    public void 포지션별목록(){
        List<PositionResponseDto> positionList = playerDao.getPlayerPositionForEachTeam();

        for(PositionResponseDto positionInfo:positionList){
            System.out.println(positionInfo);
        }



    }
}