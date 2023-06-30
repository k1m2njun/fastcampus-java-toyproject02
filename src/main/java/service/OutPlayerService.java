package service;

import dto.outplayer.OutPlayerResponseDto;
import model.outplayer.OutPlayerDao;
import model.player.Player;
import model.player.PlayerDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OutPlayerService {

    private OutPlayerDao outPlayerDao;
    private PlayerDao playerDao;
    private Connection connection;

    public OutPlayerService(OutPlayerDao outPlayerDao, PlayerDao playerDao, Connection connection) {
        this.outPlayerDao = outPlayerDao;
        this.playerDao = playerDao;
        this.connection = connection;
    }


    public void registerOutPlayer(int playerId, String reason){
        try{
            Player p = playerDao.getPlayerById(playerId);
            if(p==null){// 플레이어를 찾지 못함.
                System.out.println("playerId를 통해 플레이어를 찾지 못했습니다.");
                return;
            }
            if(p.getTeamId()==null){// 이미 방출된 선수입니다.
                System.out.println("이미 방출된 선수입니다.");
                return;
            }
            //팀 아웃되는 선수 team_id = null 처리
            playerDao.teamOutPlayer(playerId);

            //팀 아웃 선수를 out_player 목록에 넣기
            outPlayerDao.createOutPlayer(playerId, reason);

        }catch (SQLException e){
            e.printStackTrace();
        }
    }


    public void getOutPlayerList(){

        List<OutPlayerResponseDto> outPlayerResponseDtoList = outPlayerDao.getAllPlayerAndOutPlayerList();
        for(OutPlayerResponseDto oprd : outPlayerResponseDtoList){
            System.out.print(
                    " playerId = " + oprd.getPlayerId()
                     +" playerName = "+ oprd.getPlayerName()
                     +" playerPosition = "+ oprd.getPlayerPosition()
            );
            if(oprd.getOutPlayerReason()==null){
                System.out.println();
                continue;
            }
            System.out.println(
                    " outPlayerReason(이유) = " + oprd.getOutPlayerReason()
                            +" outPlayerCreatedAt(퇴출일) = "+ oprd.getOutPlayerCreatedAt()
            );
        }
        return;
    }
}