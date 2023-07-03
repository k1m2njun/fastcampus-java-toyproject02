package service;


import dto.team.TeamCreateRequestDto;
import dto.team.TeamResponseDto;
import exception.CustomException;
import model.stadium.StadiumDao;
import model.team.TeamDao;

import java.sql.SQLException;
import java.util.List;

public class TeamService {
    private TeamCreateRequestDto teamCreateRequestDto;
    private StadiumDao stadiumDao;
    private TeamDao teamDao;

    public TeamService(
            StadiumDao stadiumDao,
            TeamDao teamDao
    ) {
        this.teamCreateRequestDto = new TeamCreateRequestDto();
        this.stadiumDao = stadiumDao;
        this.teamDao = teamDao;
    }

    // 팀등록?stadiumId=1&name=NC
    public void 팀등록(String requestData) throws SQLException, RuntimeException {
        try {
            String[] requestDataList = requestData.split("&");

            Integer requestStadiumId = Integer.parseInt(requestDataList[0].split("=")[1]);
            String requestName = requestDataList[1].split("=")[1];

            if (requestName.trim().length() == 0) throw new CustomException("팀 이름을 공백(space)으로 설정할 수 없습니다.");
            if (stadiumDao.getAllStadium().isEmpty()) throw new CustomException("등록된 경기장이 없습니다.");
            if (stadiumDao.getStadiumById(requestStadiumId) == null) throw new CustomException("존재하지 않는 경기장입니다.");
            if (teamDao.getTeamByName(requestName) != null) throw new CustomException("이미 존재하는 팀이름입니다.");
            if (teamDao.getTeamByStadiumId(requestStadiumId) != null)
                throw new CustomException("해당 경기장에 이미 배정된 팀이 있습니다.");


            teamCreateRequestDto.setStadiumId(requestStadiumId);
            teamCreateRequestDto.setName(requestName);

            System.out.println(teamDao.createTeam(teamCreateRequestDto.toModel()).toString());
            System.out.println("성공");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("팀 이름을 넣지 않으셨습니다. ");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void 팀목록() throws SQLException, RuntimeException {
        try {
            List<TeamResponseDto> teamList = teamDao.getAllTeam();

            if (teamList.isEmpty() || teamList == null) throw new CustomException("등록된 팀이 없습니다.");

            for (TeamResponseDto trd : teamList) {
                System.out.println(trd.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
