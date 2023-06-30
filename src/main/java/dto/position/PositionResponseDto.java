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

    public void printPositionList(int teamCount) {


        String list = playerList;
        String[] words = list.split(",");

        System.out.print(position + " \t\t");
        for (int i = 0; i < teamCount; i++) {// 내가 착각한것 : 공백이 있을것으로 예상했으나 없었음.
            if (i>words.length) {
                System.out.print(" [공석] " + "\t\t|\t\t");
            }
            System.out.print(words[i] + "\t\t|\t\t");
        }
        System.out.println();

    }

    private long countChar(String str, char ch) {// stream 사용해서 , 개수 구함.
        return str.chars()
                .filter(c -> c == ch)
                .count();
    }


}
