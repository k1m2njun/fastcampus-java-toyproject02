package service;

import dto.outplayer.OutPlayerResponseDto;
import model.outplayer.OutPlayerDao;
import model.player.PlayerDao;

import java.sql.Connection;
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