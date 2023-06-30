package service;

import model.stadium.Stadium;
import model.stadium.StadiumDao;

import java.sql.Connection;
import java.util.List;

public class StadiumService {

    private StadiumDao stadiumDao;

    private Connection connection;

    public StadiumService(StadiumDao stadiumDao, Connection connection) {
        this.stadiumDao = stadiumDao;
        this.connection = connection;
    }

    public Stadium registerNewStadium(String stadiumName){
        Stadium stadium = stadiumDao.createStadium(stadiumName);

        //기존에 있는 경기장인지 확인
        if(stadium!=null){
            System.out.println("야구장 등록 성공");
        }else{// 중복된 경우
            System.out.println("이미 등록된 경기장입니다.");
        }

        return stadium;
    }

    public void getStadiumList(){

        List<Stadium> stadiumList = stadiumDao.getAllStadium();
        if(stadiumList == null){
            System.out.println("등록된 경기장이 없습니다.");
            return;
        }
        for(Stadium s : stadiumList){
            System.out.println(s);
        }
        return;
    }

}
