package dto.player;

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

    public void printPositionList(int teamCount) {

        int teamId = -1;
        String list = playerList;
        String[] words = list.split(",");
        int rowCount = words.length;
        int flag = 0;

        System.out.print(position + " \t\t");
        for (int i = 1; i <= teamCount; i++) { // 팀개수 출력
            flag = 0;
            for (int j = 0; j < rowCount; j++) {
                int startIndex = words[j].indexOf("[") + 1;
                int endIndex = words[j].indexOf("]");
                int number = Integer.parseInt(words[j].substring(startIndex, endIndex));
                if (number == i) {
                    System.out.print(words[j] + "\t\t|\t\t");
                    flag = 1;
                    break;
                }
            }
            if(flag == 0){
                System.out.print("  [공석]  " + "\t\t|\t\t");
            }
        }
        System.out.println();
    }
}
