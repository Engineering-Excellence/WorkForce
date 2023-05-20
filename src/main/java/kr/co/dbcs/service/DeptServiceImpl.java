package kr.co.dbcs.service;

import static kr.co.dbcs.util.JdbcManager.BR;
import static kr.co.dbcs.util.JdbcManager.BW;
import static kr.co.dbcs.util.JdbcManager.MANAGER;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import kr.co.dbcs.domain.DeptDTO;

public class DeptServiceImpl implements DeptService {
	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;

	public DeptServiceImpl() throws SQLException {
	}

	@Override
	public void deptStart() throws IOException, SQLException {
		while (true) {
			showMenu();

			String menu = BR.readLine().trim();

			switch (menu) {
			case "0":
				BW.write("홈 화면으로 돌아갑니다.\n");
				BW.flush();
				return;
			case "1":
				// 부서확인
				selectDept();
				break;
			case "2":
				// 부서추가
				insertDept();
				break;
			case "3":
				// 부서수정
				updateDept();
				break;
			case "4":
				// 부서삭제
				deleteDept();
				break;
			default:
				BW.write("잘못된 입력입니다.\n");
				BW.flush();
				break;
			}
		}
	}

	@Override
	public void showMenu() throws IOException {
		BW.write("\n======================================================================\n");
		BW.write("|\t\t         부서관리 관리자 메뉴\t\t\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t    1. 부서확인\t\t   |\t        2. 부서추가\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t    3. 부서수정\t\t   |\t        4. 부서삭제\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t원하는 기능을 선택하세요.(0번 : 홈화면으로 돌아가기)\t     |\n");
		BW.write("======================================================================\n");
		BW.flush();
	}

	@Override
	public void selectDept() throws SQLException, IOException {
		rs = stmt.executeQuery("SELECT * FROM DEPT");
		ArrayList<DeptDTO> list = new ArrayList<>();

		while (rs.next()) {
			DeptDTO dept = new DeptDTO();
			dept.setDeptCode(rs.getInt("DEPTCODE"));
			dept.setDeptName(rs.getString("DEPTNAME"));
			list.add(dept);
		}

		BW.write("\n======================================================================\n");
		BW.write("|\t     부서번호     \t   |\t          부서명     \t     |");
		BW.write("\n======================================================================\n");
		for (int i = 0; i < list.size(); i++) {
			BW.write("|\t\t " + list.get(i).getDeptCode() + "\t\t   |\t\t   " + list.get(i).getDeptName()
					+ "\t\t     |\n");
		}
		BW.write("\n======================================================================\n");
		BW.flush();
	}

	@Override
	public void insertDept() throws SQLException, IOException {
		selectDept();
		pstmt = conn.prepareStatement("INSERT INTO DEPT VALUES(?, ?)");

		BW.write("추가할 부서번호를 적어주시길 바랍니다.");
		BW.flush();
		int num = Integer.parseInt(BR.readLine());
		if(searchDeptCode(num)) {
			BW.write("중복된 부서번호입니다. 다시 확인해주시길 바랍니다.");
			BW.flush();
			return;
		}
		
		BW.write("추가할 부서명을 적어주시길 바랍니다. 작성하신 부서 번호 : " + num);
		BW.flush();
		String name = BR.readLine();
		
		if(searchDeptName(name)) {
			BW.write("중복된 부서명입니다. 다시 확인해주시길 바랍니다.");
			BW.flush();
			return;
		}
		
		pstmt.setInt(1, num);
		pstmt.setString(2, name);
		pstmt.executeUpdate();

		BW.write("부서 추가를 완료하였습니다.");
		BW.flush();
	}

	@Override
	public void updateDept() throws SQLException, IOException {
		selectDept();
		String changeName = "UPDATE DEPT SET DEPTNAME = ? WHERE DEPTCODE = ?";
		String changeCode = "UPDATE DEPT SET DEPTCODE = ? WHERE DEPTNAME = ?";

		BW.write("부서번호와 부서명 중 무엇을 수정하시겠습니까?");
		BW.write("1. 부서번호, 2. 부서명");
		BW.flush();

		String menu = BR.readLine();

		switch (menu) {
		case "1":
			pstmt = conn.prepareStatement(changeCode);
			BW.write("변경하실 부서명를 입력해주세요.");
			BW.flush();
			String str = BR.readLine();
			if(!searchDeptName(str)) {
				BW.write("없는 부서명입니다. 다시 확인해주시길 바랍니다.");
				BW.flush();
				return;
			}

			BW.write("부서번호를 무엇으로 변경하시겠습니까?");
			BW.flush();
			int num = Integer.parseInt(BR.readLine());
			if(searchDeptCode(num)) {
				BW.write("중복된 부서번호입니다. 다시 확인해주시길 바랍니다.");
				BW.flush();
				break;
			}
			
			pstmt.setInt(1, num);
			pstmt.setString(2, str);
			pstmt.executeUpdate();

			BW.write("부서번호를 변경완료하였습니다.");
			BW.flush();
			break;
		case "2":
			pstmt = conn.prepareStatement(changeName);
			BW.write("변경하실 부서번호를 입력해주세요.");
			BW.flush();
			int num2 = Integer.parseInt(BR.readLine());

			if(!searchDeptCode(num2)) {
				BW.write("없는 부서번호입니다. 다시 확인해주시길 바랍니다.");
				BW.flush();
				return;
			}
			
			BW.write("부서명을 무엇으로 변경하시겠습니까?");
			BW.flush();
			String str2 = BR.readLine();
			if(searchDeptName(str2)) {
				BW.write("중복된 부서명입니다. 다시 확인해주시길 바랍니다.");
				BW.flush();
				break;
			}
			pstmt.setString(1, str2);
			pstmt.setInt(2, num2);
			pstmt.executeUpdate();

			BW.write("부서명을 변경완료하였습니다.");
			BW.flush();
			break;
		default:
			BW.write("잘못 입력하셨습니다.");
			BW.flush();
		}
	}

	@Override
	public void deleteDept() throws SQLException, IOException {
		selectDept();
		pstmt = conn.prepareStatement("DELETE DEPT WHERE DEPTCODE = ?");

		BW.write("삭제할 부서의 번호를 입력하세요.");
		BW.flush();
		int num = Integer.parseInt(BR.readLine());

		if(!searchDeptCode(num)) {
			BW.write("없는 부서번호입니다. 다시 확인해주시길 바랍니다.");
			BW.flush();
			return;
		}
		
		pstmt.setInt(1, num);
		pstmt.executeUpdate();

		BW.write(num + "번 부서를 삭제하였습니다.");
		BW.flush();
	}

	@Override
	public boolean searchDeptName(String name) throws SQLException {
		String sql = "SELECT DEPTNAME FROM DEPT WHERE DEPTNAME = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, name);
		
		rs = pstmt.executeQuery();
		return rs.next();
	}

	@Override
	public boolean searchDeptCode(int num) throws SQLException {
		String sql = "SELECT DEPTCODE FROM DEPT WHERE DEPTCODE = ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, num);
		
		rs = pstmt.executeQuery();
		return rs.next();
	}
	

}