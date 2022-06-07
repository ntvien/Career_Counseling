package group12.career_counseling.web_service.utils.Security;

import group12.career_counseling.web_service.vertx.rest.exception.ExceptionMessages;

public class JwtUtil {
    private static String BEARER = "Bearer";

    public static String getTokenBearer(String tokenBearer) {
        if (tokenBearer != null) {
            var list = tokenBearer.split(" ");
            if (list.length == 2 && list[0].equals(BEARER)) {
                return list[1];
            }
        }
        throw new RuntimeException(ExceptionMessages.BadToken);
    }
}
