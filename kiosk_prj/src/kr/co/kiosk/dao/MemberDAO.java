package kr.co.kiosk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 회원 db를 처리하는 DAO, 겸사겸사 비회원도 처리한다
 */
public class MemberDAO {

	private static MemberDAO memDAO;

	private MemberDAO() {

	}// MemberDAO

	public static MemberDAO getInstance() {
		if (memDAO == null) {
			memDAO = new MemberDAO();
		}

		return memDAO;
	}// getInstance()

	// 비회원 테이블에 레코드 추가
	public void insertGuest(int guestId) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			String insertGuest = "insert into guest(guest_id) values(?)";

			pstmt = con.prepareStatement(insertGuest);
			pstmt.setInt(1, guestId);
			pstmt.executeUpdate();

		} finally {
			dbCon.closeDB(null, pstmt, con);

		}
	}// insertGuest

	public int selectGuest() throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		DbConnection dbCon = DbConnection.getInstance();
		int guestId = -1;

		try {
			con = dbCon.getConn();
			String sql = "SELECT seq_guest_guest_id.nextval FROM dual";

			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				guestId = rs.getInt(1);
			}

		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}

		return guestId;
	}
	
}
