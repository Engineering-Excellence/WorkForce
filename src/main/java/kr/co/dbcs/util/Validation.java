package kr.co.dbcs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    static String regExp_symbol = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
    static String regExp_id = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
    static String regExp_birth = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";
    static String regExp_contact = "^\\d{2,3}-\\d{3,4}-\\d{4}$";

    public static boolean validateId(String id) {

        Pattern p3 = Pattern.compile(regExp_id);
        Matcher m_id = p3.matcher(id);

        return m_id.find();
    }

    public static boolean validatePw(String pw) {

        Pattern p1 = Pattern.compile(regExp_symbol);
        Matcher m_pw = p1.matcher(pw);

        return m_pw.find();
    }

    public static boolean validateDate(String birth) {

        Pattern p1 = Pattern.compile(regExp_birth);
        Matcher m_birth = p1.matcher(birth);

        return m_birth.find();
    }

    public static boolean validateContact(String contact) {

        Pattern p4 = Pattern.compile(regExp_contact);
        Matcher matcher = p4.matcher(contact);

        return matcher.find();
    }
}
