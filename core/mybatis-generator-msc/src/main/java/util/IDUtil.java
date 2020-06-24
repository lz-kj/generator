package util;

import java.util.UUID;

public class IDUtil {

    static String regex = "-";

    static String empty = "";

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll(regex,empty);
    }
}
