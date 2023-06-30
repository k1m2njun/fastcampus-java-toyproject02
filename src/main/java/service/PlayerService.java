package service;

import dto.player.PlayerCreateRequestDto;
import dto.player.PlayerGetResponseDto;
import dto.position.PositionResponseDto;
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

    public PlayerService(PlayerDao playerDao, TeamDao teamDao) {
        this.playerCreateRequestDto = new PlayerCreateRequestDto();
        this.playerGetResponseDto = new PlayerGetResponseDto();
        this.playerDao = playerDao;
        this.teamDao = teamDao;
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
        for (Player playerResponse : playerResponseList) {
            System.out.println(playerResponse.toString());
        }
    }


    public void 포지션별목록() {
        System.out.println("이름옆에 숫자는 해당선수 teamId 입니다.");

        List<PositionResponseDto> positionList = playerDao.getPlayerPositionForEachTeam();
        String str = positionColumnNamePrint(positionList);
        System.out.println(str);

        for (PositionResponseDto positionInfo : positionList) {
            positionInfo.printPositionList();
        }

    }


    private String positionColumnNamePrint(List<PositionResponseDto> list) {

        String str = "포지션" + " \t\t";

        //1. 팀 번호를 추출한다.
        // 팀 번호를 저장하기 위한 int 배열 필요
        String playerList = list.get(0).getPlayerList();
        String pl = playerList;
        String[] words = pl.split(",");
        int n = words.length; // 팀의 개수

        //2. 팀 번호를 잘라내기 위한 startIndex, endIndex 변수
        int startIndex = -1;
        int endIndex = -1;
        int[] teamNumberArr = new int[n];// n = 팀 개수 의미
        String number = null;
        for (int i = 0; i < n; i++) {
            startIndex = words[i].indexOf("[") + 1;
            endIndex = words[i].indexOf("]");
            number = words[i].substring(startIndex, endIndex);
            teamNumberArr[i] = Integer.parseInt(number);
        }

        //3. 팀 번호를 사용해서 팀 이름 추출
        for (int i = 0; i < n; i++) {
            String teamName = teamDao.getTeamNameByTeamId(teamNumberArr[i]);
            str += (teamName + "[" + teamNumberArr[i] + "]" + "\t\t|\t\t");
        }
        return str;
    }
}