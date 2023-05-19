package kr.co.dbcs.service;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import kr.co.dbcs.domain.EmpDTO;
import kr.co.dbcs.domain.SalDTO;

import static kr.co.dbcs.util.JdbcManager.BR;
import static kr.co.dbcs.util.JdbcManager.BW;
import static kr.co.dbcs.util.JdbcManager.MANAGER;

public class SalServiceImpl implements SalService {
	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;
	private Date payDate;
	private EmpDTO emp;

	public SalServiceImpl(EmpDTO emp) throws SQLException {
		this.emp = emp;
	}

	@Override
	public void salStart() throws IOException, SQLException, ParseException {
		while (true) {
			showMenu();
			String menu = BR.readLine().trim();

			switch (menu) {
			case "0":
				BW.write("홈 화면으로 돌아갑니다.\n");
				BW.flush();
				return;
			case "1":
				// 급여지급
				paySal();
				break;
			case "2":
				// 지급일 변경
				changeSalDate();
				break;
			}
		}
	}

	@Override
	public void showMenu() throws IOException {
		BW.write("\n======================================================================\n");
		BW.write("|\t\t\t임직원급여관리 시스템\t\t\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t  1. 급여지급\t\t |\t     2. 지급일 변경\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t원하는 기능을 선택하세요.(0번 : 이전화면으로 돌아가기)\t     |\n");
		BW.write("======================================================================\n");
		BW.flush();
	}

	@Override
	public void changeSalDate() throws SQLException, IOException, ParseException {
		String sqlSearch = "SELECT PAYDATE FROM SAL WHERE ROWNUM = 1 ORDER BY SALID DESC";
		rs = stmt.executeQuery(sqlSearch);
		
		Date nowDate = null;
		while(rs.next()) {
			SalDTO sal = new SalDTO();
			sal.setPayDate(rs.getDate("PAYDATE"));
			nowDate = sal.getPayDate();
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd");
		String str = sdf.format(nowDate);
		
		BW.write("현재 지급일은 " + str + "입니다.\n");
		BW.write("변경하실 지급일을 입력해주세요 : ");
		BW.flush();
		
		String sqlUpdate = "update sal set paydate = to_date(?, 'DD') where salID = (select max(salID) from sal)";
		
		String date =  BR.readLine();
		
		pstmt = conn.prepareStatement(sqlUpdate);
		pstmt.setString(1, date);
		pstmt.executeUpdate();
	}

	@Override
	public void paySal() {
		// TODO Auto-generated method stub
		
	}
}
