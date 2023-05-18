package kr.co.dbcs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	static String regExp_symbol = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$";
	static String regExp_id = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
	static String regExp_birth = "^(19[0-9][0-9]|20\\d{2})-(0[0-9]|1[0-2])-(0[1-9]|[1-2][0-9]|3[0-1])$";

	public static boolean ValidateId(String id) {

		Pattern p3 = Pattern.compile(regExp_id);

		Matcher m_id = p3.matcher(id);

		if (m_id.find()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean ValidatePw(String pw) {

		Pattern p1 = Pattern.compile(regExp_symbol);

		Matcher m_symbol = p1.matcher(pw);

		if (m_symbol.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean ValidateDate(String birth) {
		
		Pattern p1 = Pattern.compile(regExp_birth);

		Matcher m_symbol = p1.matcher(birth);

		if (m_symbol.find()) {
			return true;
		} else {
			return false;
		}
	}
}
