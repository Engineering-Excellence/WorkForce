package kr.co.dbcs.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
	String regExp_symbol = "([0-9].*[!,@,#,^,&,*,(,)])|([!,@,#,^,&,*,(,)].*[0-9])";
	String regExp_alpha = "([a-z].*[A-Z])|([A-Z].*[a-z])";
	String regExp_id = "^[a-zA-Z]{1}[a-zA-Z0-9_]{4,11}$";
	
	public boolean Validate(String id, String pw) {
		
		Pattern p1 = Pattern.compile(regExp_symbol);
		Pattern p2 = Pattern.compile(regExp_alpha);
		Pattern p3 = Pattern.compile(regExp_id);
		
		Matcher m_symbol = p1.matcher(pw);
		Matcher m_alpha = p2.matcher(pw);
		Matcher m_id = p3.matcher(id);
		
		if(m_symbol.find() && m_alpha.find() && m_id.find()) {
			return true;
		} else {
			return false;
		}
	}
}
