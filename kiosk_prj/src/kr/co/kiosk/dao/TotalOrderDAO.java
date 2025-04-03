package kr.co.kiosk.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import kr.co.kiosk.vo.TotalOrderVO;

public class TotalOrderDAO {

	private static TotalOrderDAO toDAO;
	
	private TotalOrderDAO() {
		
	}//TotalOrderDAO
	
	public static TotalOrderDAO getInstance() {
		if(toDAO == null) {
			toDAO = new TotalOrderDAO();
		}
		
		return toDAO;
	}//getInstance
	
	//회원 전용 insert
	public void insertTotalOrderMember(TotalOrderVO toVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
			StringBuilder insertTotalOrder = new StringBuilder();
			insertTotalOrder
			.append("	insert into total_order(order_id, member_id, order_type, order_status)	")
			.append("	values(?,?,?,?)				");
			
			pstmt = con.prepareStatement(insertTotalOrder.toString());
			
			pstmt.setInt(1, toVO.getOrderId());
			pstmt.setInt(2, toVO.getMemberId());
			pstmt.setString(3, toVO.getOrderType());
			pstmt.setString(4, toVO.getOrderStatus());
			
			pstmt.executeUpdate();
			
		} finally{
			dbCon.closeDB(null, pstmt, con);
		}
	}//insertTotalOrderMember
	
	//비회원 전용 insert
	public void insertTotalOrderGuest(TotalOrderVO toVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		DbConnection dbCon = DbConnection.getInstance();
		
		try {
			con = dbCon.getConn();
			StringBuilder insertTotalOrder = new StringBuilder();
			insertTotalOrder
			.append("	insert into total_order(order_id,order_type, order_status, guest_id)	")
			.append("	values(?,?,?,?)				");
			
			pstmt = con.prepareStatement(insertTotalOrder.toString());
			
			pstmt.setInt(1, toVO.getOrderId());
			pstmt.setString(2, toVO.getOrderType());
			pstmt.setString(3, toVO.getOrderStatus());
			pstmt.setInt(4, toVO.getGuestId());
			
			pstmt.executeUpdate();
			
		} finally{
			dbCon.closeDB(null, pstmt, con);
		}
	}//insertTotalOrderGuest
	
	public void updateTotalOrder(int orderId) {
		
	}
	
	//order_id 추가용 시퀀스 미리 얻어오기
	public int getNextOrderId() throws SQLException {
	    Connection con = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    DbConnection dbCon = DbConnection.getInstance();
	    int orderId = -1; // 기본값

	    try {
	        con = dbCon.getConn();
	        String sql = "SELECT seq_total_order_order__id.NEXTVAL FROM dual";
	        
	        pstmt = con.prepareStatement(sql);
	        rs = pstmt.executeQuery();

	        if (rs.next()) {
	            orderId = rs.getInt(1); 
	        }

	    } finally {
	        dbCon.closeDB(rs, pstmt, con);
	    }
	    
	    return orderId;
	}//getNextOrderId
}
