package kr.co.dbcs.service;

import kr.co.dbcs.domain.AttRecordDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

import static kr.co.dbcs.util.JdbcManager.*;

@Slf4j
public class AttRecordServiceImpl implements AttRecordService {

    private final Connection conn = MANAGER.getConnection();
    private final Statement stmt = MANAGER.getStatement();
    private PreparedStatement pstmt;
    private ResultSet rs;

    private static String usrID = "jaejin1112";

    public AttRecordServiceImpl() throws SQLException {
    }

    @Override
    public void attRecordMenu() throws IOException, SQLException {

        while (true) {

            BW.write("-=-=-=-=-= JDBC Query =-=-=-=-=-\n" + "\t0. 출퇴근메뉴 종료\n" + "\t1. 출근하기\n" + "\t2. 퇴근하기\n"
                    + "\t3. 출퇴근기록 확인\n" + "\t6. <기능 만드는 중>\n" + "\n원하는 메뉴를 선택하세요: ");
            BW.flush();

            switch (BR.readLine().trim()) {
                case "0":
                    return;
                case "1":
                    goWork();
                    break;
                case "2":
                    leaveWork();
                    break;
                case "3":
                    selectAllAtt();
                    break;
                case "4":
//                    delete(dto.getClassName());
                    break;
                case "5":
//                    selectByGno(dto.getClassName());
                    break;
                case "6":
                    break;
                default:
                    BW.write("잘못된 입력입니다.\n\n");
                    break;
            }
        }
    }

    public void goWork() throws SQLException, IOException {
//		bw.write("usrID를 입력하세요.");
//		bw.flush();
//		usrID = br.readLine();
//		usrID = 

        if (searchUsr(usrID) == true && searchAtt(usrID) == false) {

            pstmt = conn.prepareStatement(
                    "INSERT INTO AttRecord(RECORDID,ATTDATE, STARTTIME, USRID) VALUES (Autorecord.nextval, to_char(sysdate, 'YYYY-MM-DD'), SYSTIMESTAMP,?)");
            pstmt.setString(1, usrID);
//		        pstmt.setTimeStamp(2, TO_SYSTIMESTAMP('YYYY-MM-DD HH24:MI:SS')); systimestamp를 simple date format으로?
            pstmt.executeUpdate();
            log.info("goWork INSERT COMPLETE\n");
        } else if (searchUsr(usrID) == false) {
            BW.write("등록된 ID가 아닙니다.");
            BW.flush();
        } else if (searchAtt(usrID) == true) {
            BW.write("이미 출근 처리된 ID입니다.\n");
            BW.flush();
        }
    }

    public void leaveWork() throws SQLException, IOException {
//		bw.write("usrID를 입력하세요.");
//		bw.flush();
//		usrID = br.readLine();

        if (searchUsr(usrID) == true && searchAtt(usrID) == true) {
            String sql = "UPDATE AttRecord SET endTime = SYSTIMESTAMP Where usrID= ? and ATTDATE >= to_char(sysdate,'yyyymmdd')";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, usrID);
            pstmt.executeUpdate();
            log.info("endTime UPDATE COMPLETE\n");

        } else if (searchUsr(usrID) == false) {
            BW.write("등록된 ID가 아닙니다.");
            BW.flush();
        } else if (searchAtt(usrID) == false) {
            BW.write("출근 처리가 되지 않아, 퇴근처리가 불가능 합니다.");
            BW.flush();
        }
    }

    public boolean searchUsr(String usrID) throws SQLException {

        pstmt = conn.prepareStatement("SELECT * FROM Usr WHERE usrID = ?");
        pstmt.setString(1, usrID);

        rs = pstmt.executeQuery();
        return rs.next();
    }

    public boolean searchAtt(String usrID) throws SQLException {

        pstmt = conn.prepareStatement(
                "SELECT * FROM  AttRecord WHERE usrID = ? and ATTDATE >= to_char(sysdate,'yyyymmdd')");
        pstmt.setString(1, usrID);

        rs = pstmt.executeQuery();
        return rs.next();
    }

    public void selectAllAtt() throws SQLException, IOException {

        rs = stmt.executeQuery("SELECT * FROM AttRecord");
        ArrayList<AttRecordDTO> list = new ArrayList<>();

        while (rs.next()) {
            AttRecordDTO ad = new AttRecordDTO();
            ad.setRecordID(rs.getInt(1));
            ad.setAttDate(rs.getDate(2));
            ad.setStartTime(rs.getTimestamp(3));
            ad.setEndTime(rs.getTimestamp(4));
            ad.setUsrID(rs.getString(5));
            list.add(ad);
        }

        BW.write("=======================================================\n");
        BW.write("<출근날짜>\t<출근시간>\t<퇴근시간>\t<ID>\n");

        for (AttRecordDTO attRecordDTO : list) {
//
            StringBuilder sb = new StringBuilder();
            StringTokenizer st;
            st = new StringTokenizer(String.valueOf(attRecordDTO.getStartTime()));

            st.nextToken();
            String str1 = st.nextToken().substring(0, 8);

            if (attRecordDTO.getEndTime() != null) {
                st = new StringTokenizer(String.valueOf(attRecordDTO.getEndTime()));
                st.nextToken();
                String str2 = st.nextToken().substring(0, 8);
                BW.write(attRecordDTO.getAttDate() + "\t" + str1 + "\t" + str2 + "\t" + attRecordDTO.getUsrID() + "\n");
            } else
                BW.write(attRecordDTO.getAttDate() + "\t" + str1 + "\t" + "\t\t" + attRecordDTO.getUsrID() + "\n");

        }
        BW.write("=======================================================\n");
        BW.flush();
    }
}
