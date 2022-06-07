package group12.career_counseling.web_service.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class MyStringUtils {
    public static String removeUnicode(String str) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return "";
        }
        str = str.toLowerCase().trim();
        String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        temp = temp.replaceAll("đ", "d");
        return pattern.matcher(temp).replaceAll("").trim();
    }
    public static String formatE164Phone(String phone) {
        String formattedPhone = phone;
        if (phone.charAt(0) == '0') {
            formattedPhone = formattedPhone.replaceFirst("0", "");
        }
        return "+84" + formattedPhone;
    }
}
