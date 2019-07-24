package utils;

import java.util.Arrays;

public class StringUtils {
    public static String strAppnend(String  ... values){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(values).forEach( val -> {
            sb.append(val);
        });
        return sb.toString();
    }
}
