package constant;

import java.util.Arrays;

public enum Reason {
    은퇴, 도박, 마약, 성범죄, 폭력, 사기;

    public static boolean contains(String reason) {
        for(Reason r : Reason.values()) {
            if (r.name().equals(reason)) return true;
        }
        return false;
    }
}
