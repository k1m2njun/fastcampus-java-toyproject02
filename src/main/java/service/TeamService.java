package service;

import dto.position.PositionResponseDto;
import model.stadium.StadiumDao;
import model.team.Team;
import model.team.TeamDao;
import dto.team.TeamResponseDto;

import java.sql.Connection;
import java.util.List;

public class TeamService {

    private StadiumDao stadiumDao;
    private TeamDao teamDao;
    private Connection connection;

    public TeamService(StadiumDao stadiumDao, TeamDao teamDao, Connection connection) {
        this.stadiumDao = stadiumDao;
        this.teamDao = teamDao;
        this.connection = connection;
    }

    // 1. 해당 경기장이 존재 여부 확인 -> 존재해야함. [TeamDao의 stadiumId로 StadiumDao의 id 검색]
    // 2. 해당 경기장에 팀이 등록된 상태인지 확인  -> 등록이 안된 상태여야함. [TeamDao의 name으로 팀 전체 select해서 검색해서 확인]
    // 3. 등록하는 팀이름이 중복되지 않아야함. -> [TeamDao 사용]
    // 4.. 1, 2, 3을 만족해야 팀으로 등록가능함.
    public Team registerNewTeam(int stadiumId, String teamName){
        // 1. 해당 경기장이 존재 여부 확인 -> 존재해야함.
        if(stadiumDao.getStadiumById(stadiumId)==null){
            System.out.println("존재하지 않는 경기장입니다.");
            return null;// 해당 경기장이 없으면 null 반환.
        }

        // 2. 해당 경기장에 팀이 등록된 상태인지 확인  -> 등록이 안된 상태여야함.
        if(teamDao.getTeamByStadiumId(stadiumId)!=null){
            System.out.println("해당 경기장에 이미 배정된 팀이 있습니다.");
            return null;
        }

        // 3. 등록하는 팀이름이 중복되지 않아야함.
        if(teamDao.getTeamByName(teamName)!=null){
            System.out.println("이미 존재하는 팀이름입니다.");
            return null;
        }

        Team team = teamDao.createTeam(stadiumId, teamName);
        if(team==null){
            System.out.println("팀 등록 실패");
            return null;
        }else{
            System.out.println("팀 등록 성공");
            return team;
        }

    }

    public void getTeamList() {

        List<TeamResponseDto> teamList = teamDao.getAllTeam();

        if(teamList == null){
            System.out.println("등록된 팀이 없습니다.");
            return;
        }
        for(TeamResponseDto trd : teamList){
            System.out.println(trd);
        }
        return;
    }

}
