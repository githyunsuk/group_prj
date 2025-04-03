package kr.co.kiosk.service;

import java.sql.SQLException;

import kr.co.kiosk.dao.MemberDAO;

public class MemberService {

	public MemberService() {
		
	}//MemberService
	
	public int addGuest() {
		int guestId = acquireNextMemberId();
		MemberDAO memDAO = MemberDAO.getInstance();
		
		try {
			memDAO.insertGuest(guestId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return guestId;
	}//addGeust
	
	public int acquireNextMemberId() {
		int guestId = -1;
		MemberDAO memDAO = MemberDAO.getInstance();
		try {
			guestId = memDAO.selectGuest();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return guestId;
	}//acquireCurrMemberId
	

}
