package dto.position;

public class PositionResponseDto {
    private String p;// 투수
    private String c; // 포수
    private String b1;// 1루수
    private String b2;// 2루수
    private String b3;// 3루수
    private String ss;// 유격수
    private String lf;// 좌익수
    private String cf;//중견수
    private String rf;//우익수

    public PositionResponseDto(String p, String c, String b1, String b2, String b3, String ss, String lf, String cf, String rf) {
        this.p = p;
        this.c = c;
        this.b1 = b1;
        this.b2 = b2;
        this.b3 = b3;
        this.ss = ss;
        this.lf = lf;
        this.cf = cf;
        this.rf = rf;
    }

    @Override
    public String toString() {
        return "PositionResponseDto{" +
                "투수='" + p + '\'' +
                ",포수='" + c + '\'' +
                ",1루수='" + b1 + '\'' +
                ",2루수='" + b2 + '\'' +
                ",3루수='" + b3 + '\'' +
                ",유격수='" + ss + '\'' +
                ",좌익수='" + lf + '\'' +
                ",중견수='" + cf + '\'' +
                ",우익수='" + rf + '\'' +
                '}';
    }
}
