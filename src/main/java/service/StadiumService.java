package service;


import exception.CustomException;
import model.stadium.Stadium;
import model.stadium.StadiumDao;

import java.sql.SQLException;
import java.util.List;

public class StadiumService {
    private StadiumDao stadiumDao;

    public StadiumService(StadiumDao stadiumDao) {
        this.stadiumDao = stadiumDao;
    }

    // 야구장등록?name=잠실야구장
    public void 야구장등록(String requestData) throws SQLException, RuntimeException {
        try {
            String requestName = requestData.split("=")[1];

            if(stadiumDao.getStadiumByName(requestName) != null) throw new CustomException("이미 등록된 경기장입니다.");

            System.out.println(stadiumDao.createStadium(requestName).toString());
            System.out.println("성공");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void 야구장목록() throws SQLException, RuntimeException {
        try {
            List<Stadium> stadiumList = stadiumDao.getAllStadium();
            if(stadiumList == null) throw new CustomException("등록된 경기장이 없습니다.");
            for(Stadium s : stadiumList){
                System.out.println(s.toString());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }
}
