package dto;

import constant.Reason;

public class TestEnum {
    private Reason reason;

    public TestEnum(Reason reason) {
        this.reason = reason;

    }

    public static void main(String[] args) {
        System.out.println(Reason.도박);
    }

}
