package kr.co.kiosk.adminEvt;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import kr.co.kiosk.adminView.AdminLoginView;
import kr.co.kiosk.adminView.AdminMainView;
import kr.co.kiosk.dao.DbConnection;
import kr.co.kiosk.userView.MainPageView;

/**
 * 로그인 이벤트
 */
public class AdminLoginEvt extends WindowAdapter implements ActionListener {
	private AdminLoginView ld;
	private String accessId;
	private String accessPw;
	private JButton login;
	private JButton out;
	private String id;
	private String pass;
	
	
	public AdminLoginEvt(AdminLoginView ld) {
		this.ld = ld;
		login=ld.getJbtnLogin();
		out=ld.getJbtnOut();
	}//LoginEvt
	
	public boolean idChk() {
		boolean flag = false;
		id = ld.getJtfId().getText();
		flag = !id.isEmpty();
		
		if(!flag) {
			ld.getJtfId().requestFocus();
			JOptionPane.showMessageDialog(ld, "아이디 입력");
		}
		
		return flag;
		
	}//idChk
	
	/**
	 * 비밀번호가 입력되었는지 확인하고, 있으면 아이디와 비밀번호 체크 후 로그인
	 * @throws SQLException 
	 */
	private void passChk() throws SQLException {
		if(!idChk()) {
			return;
		}//end if
		pass = new String(ld.getJpfPass().getPassword());
		
		if(pass.isEmpty()) {
			ld.getJpfPass().requestFocus();
			JOptionPane.showMessageDialog(ld, "비밀번호 입력");
			return;
		}
		
		String sql = "select id, pass from admin";
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		DbConnection db = DbConnection.getInstance();
		try {
			conn=db.getConn();
			pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                accessId = rs.getString("id");
                accessPw = rs.getString("pass");
            }
		}finally {
			db.closeDB(rs, pstmt, conn);
		}
		if(id.equals(accessId)&&pass.equals(accessPw)) {
			JOptionPane.showMessageDialog(ld, "로그인 성공");
			ld.dispose(); //기존창 종료
			new AdminMainView().setVisible(true); //AdminMainView 실행 
		}else {
			JOptionPane.showMessageDialog(ld, "로그인 실패");
		}
		
	
	}//passChk
	
	
	@Override
	public void windowClosing(WindowEvent e) {
		ld.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==ld.getJbtnLogin()) {
			if(idChk()) {
				try {
					passChk();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		if(e.getSource()==ld.getJbtnOut()) {
			JOptionPane.showMessageDialog(ld, "나가기");
			ld.dispose();
			new MainPageView().setVisible(true);
		}
	}
	


	
}
