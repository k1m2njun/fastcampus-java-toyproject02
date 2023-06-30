package dto.position;

public class PositionResponseDto {
    private String position;
    private String playerList;

    public PositionResponseDto(String position, String playerList) {
        this.position = position;
        this.playerList = playerList;
    }

    public String getPosition() {
        return position;
    }

    public String getPlayerList() {
        return playerList;
    }

    @Override
    public String toString() { // 여기서 파싱 할건지 정해야함.
        return "[position='" + position + '\'' +
                ", playerList='" + playerList + '\'' +
                ']';
    }

    public void printPositionList(){


        String p = position;
        String list = playerList;
        String[] words = list.split(",");

        System.out.print(position +" \t\t");
        for (String s: words){// 내가 착각한것 : 공백이 있을것으로 예상했으나 없었음.
            if(s.equals(" ")){
                s=" [공석] ";
            }
            System.out.print(s+"\t\t|\t\t");
        }
        System.out.println();

    }
}
