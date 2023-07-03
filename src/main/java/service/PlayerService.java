package service;

import dto.player.PlayerCreateRequestDto;
import dto.player.PlayerGetResponseDto;
import dto.position.PositionResponseDto;
import exception.CustomException;
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

    public PlayerService(
            PlayerDao playerDao,
            TeamDao teamDao
    ) {
        this.playerCreateRequestDto = new PlayerCreateRequestDto();
        this.playerGetResponseDto = new PlayerGetResponseDto();
        this.playerDao = playerDao;
        this.teamDao = teamDao;
    }



    public void 선수등록(String requestData) throws RuntimeException, SQLException{ // 선수등록?teamId=1&name=이대호&position=1루수
        try {
            String[] requestDataList = requestData.split("&");

            Integer requestTeamId = Integer.parseInt(requestDataList[0].split("=")[1]); // teamId=1
            String requestName = requestDataList[1].split("=")[1]; // name=이대호
            String requestPosition = requestDataList[2].split("=")[1]; // position=1루수

            List<Player> playerResponseList = null;
            for(int teamId : teamDao.getAllTeamId()) {
                if(teamId == requestTeamId) {
                    playerResponseList = playerDao.getPlayersByTeam(requestTeamId);
                }
            }
            if (playerResponseList == null) throw new CustomException("해당 팀은 DB에 존재하지 않습니다.");

            if (requestTeamId == null) throw new CustomException("팀 id를 입력해주세요.");
            if (requestName.isBlank() || requestName == null) throw new CustomException("이름을 입력해주세요.");
            if (requestPosition.isBlank() || requestPosition == null) throw new CustomException("포지션을 입력해주세요.");
            for (Player p : playerDao.getAllPlayers()) {
                if (
                    (p.getTeamId() != null) &&
                    (requestTeamId == p.getTeamId()) &&
                    (requestPosition.equals(p.getPosition()))
                ) throw new CustomException("요청한 팀에 이미 해당 포지션의 선수가 존재합니다.");
            }

            playerCreateRequestDto.setTeamId(requestTeamId);
            playerCreateRequestDto.setName(requestName);
            playerCreateRequestDto.setPosition(requestPosition);

            System.out.println(playerDao.createPlayer(playerCreateRequestDto.toModel()).toString());
            System.out.println("성공");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void 선수목록(String requestData) throws RuntimeException, SQLException {

        try {
            Integer requestTeamId = Integer.parseInt(requestData.split("=")[1]);
            List<Player> playerResponseList = null;

            for(int teamId : teamDao.getAllTeamId()) {
                if(teamId == requestTeamId) {
                    playerResponseList = playerDao.getPlayersByTeam(requestTeamId);
                }
            }
            if(playerResponseList == null) throw new CustomException("해당 팀은 DB에 존재하지 않습니다.");
            if(playerResponseList.isEmpty()) throw new CustomException("해당 팀은 선수가 존재하지 않습니다.");

            for(Player playerResponse : playerResponseList) {
                System.out.println(playerResponse.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void 포지션별목록() throws SQLException {
        if (teamDao.getTeamCount() == 0) {
            System.out.println("팀이 존재하지 않아서 출력이 불가능합니다.");
            return;
        }
        System.out.println("이름옆에 숫자는 해당선수 teamId 입니다.");

        List<PositionResponseDto> positionList = playerDao.getPlayerPositionForEachTeam();
        String str = positionColumnNamePrint();
        System.out.println(str);// column 이름 출력 포지션 + 팀 이름

        int teamCount = teamDao.getTeamCount();

        for (PositionResponseDto positionInfo : positionList) {
            positionInfo.printPositionList(teamCount);
        }

    }


    private String positionColumnNamePrint() throws SQLException {

        int teamCount = teamDao.getTeamCount();
        String str = "포지션" + " \t\t";

        for (int i = 1; i <= teamCount; i++) {
            String teamName = teamDao.getTeamNameByTeamId(i);
            str += (teamName + "[" + i + "]" + "\t\t|\t\t");
        }
        return str;

    }

}
