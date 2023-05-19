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

import kr.co.dbcs.domain.PosDTO;

public class PosServiceImpl implements PosService {
	private final Connection conn = MANAGER.getConnection();
	private final Statement stmt = MANAGER.getStatement();
	private PreparedStatement pstmt;
	private ResultSet rs;

	public PosServiceImpl() throws SQLException {
	}

	@Override
	public void posStart() throws IOException, SQLException {
		while (true) {
			showMenu();

			String menu = BR.readLine().trim();

			switch (menu) {
			case "0":
				BW.write("홈 화면으로 돌아갑니다.\n");
				BW.flush();
				return;
			case "1":
				// 직급확인
				selectPos();
				break;
			case "2":
				// 직급추가
				insertPos();
				break;
			case "3":
				// 직급수정
				updatePos();
				break;
			case "4":
				// 직급삭제
				deletePos();
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
		BW.write("|\t\t         직급관리 관리자 메뉴\t\t\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t    1. 직급확인\t\t   |\t        2. 직급추가\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t    3. 직급수정\t\t   |\t        4. 직급삭제\t     |\n");
		BW.write("======================================================================\n");
		BW.write("|\t원하는 기능을 선택하세요.(0번 : 홈화면으로 돌아가기)\t     |\n");
		BW.write("======================================================================\n");
		BW.flush();
	}

	@Override
	public void selectPos() throws SQLException, IOException {
		rs = stmt.executeQuery("SELECT * FROM POS");
		ArrayList<PosDTO> list = new ArrayList<>();

		while (rs.next()) {
			PosDTO pos = new PosDTO();
			pos.setPosCode(rs.getInt("POSCODE"));
			pos.setPosName(rs.getString("POSNAME"));
			list.add(pos);
		}

		BW.write("\n======================================================================\n");
		BW.write("|\t     직급번호     \t   |\t          직급명     \t     |");
		BW.write("\n======================================================================\n");
		for (int i = 0; i < list.size(); i++) {
			BW.write("|\t\t " + list.get(i).getPosCode() + "\t\t   |\t\t   " + list.get(i).getPosName()
					+ "\t\t     |\n");
		}
		BW.write("\n======================================================================\n");
		BW.flush();
	}

	@Override
	public void insertPos() throws SQLException, IOException {
		selectPos();
		pstmt = conn.prepareStatement("INSERT INTO POS VALUES(?, ?)");

		BW.write("추가할 직급번호를 적어주시길 바랍니다.");
		BW.flush();
		int num = Integer.parseInt(BR.readLine());
		BW.write("추가할 직급명을 적어주시길 바랍니다. 작성하신 직급 번호 : " + num);
		BW.flush();
		String name = BR.readLine();

		pstmt.setInt(1, num);
		pstmt.setString(2, name);
		pstmt.executeUpdate();

		BW.write("직급 추가를 완료하였습니다.");
		BW.flush();
	}

	@Override
	public void updatePos() throws SQLException, IOException {
		selectPos();
		String changeName = "UPDATE POS SET POSNAME = ? WHERE POSCODE = ?";
		String changeCode = "UPDATE POS SET POSCODE = ? WHERE POSNAME = ?";

		BW.write("직급번호와 직급명 중 무엇을 수정하시겠습니까?");
		BW.write("1. 직급번호, 2. 직급명");
		BW.flush();

		String menu = BR.readLine();

		switch (menu) {
		case "1":
			pstmt = conn.prepareStatement(changeCode);
			BW.write("변경하실 직급명를 입력해주세요.");
			BW.flush();
			String str = BR.readLine();

			BW.write("직급번호를 무엇으로 변경하시겠습니까?");
			BW.flush();
			int num = Integer.parseInt(BR.readLine());

			pstmt.setInt(1, num);
			pstmt.setString(2, str);
			pstmt.executeUpdate();

			BW.write("직급번호를 변경완료하였습니다.");
			BW.flush();
			break;
		case "2":
			pstmt = conn.prepareStatement(changeName);
			BW.write("변경하실 직급번호를 입력해주세요.");
			BW.flush();
			int num2 = Integer.parseInt(BR.readLine());

			BW.write("직급명을 무엇으로 변경하시겠습니까?");
			BW.flush();
			String str2 = BR.readLine();

			pstmt.setString(1, str2);
			pstmt.setInt(2, num2);
			pstmt.executeUpdate();

			BW.write("직급명을 변경완료하였습니다.");
			BW.flush();
			break;
		default:
			BW.write("잘못 입력하셨습니다.");
			BW.flush();
		}
	}

	@Override
	public void deletePos() throws SQLException, IOException {
		selectPos();
		pstmt = conn.prepareStatement("DELETE POS WHERE POSCODE = ?");
		
		BW.write("삭제할 직급의 번호를 입력하세요.");
		BW.flush();
		int num = Integer.parseInt(BR.readLine());
		
		pstmt.setInt(1, num);
		pstmt.executeUpdate();
		
		BW.write(num + "번 직급을 삭제하였습니다.");
		BW.flush();
	}
}
