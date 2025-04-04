package kr.co.kiosk.adminEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.adminView.MemberManageView;
import kr.co.kiosk.adminView.MenuManageView;
import kr.co.kiosk.service.MemberService;
import kr.co.kiosk.vo.MemberVO;
import kr.co.kiosk.vo.MenuVO;

public class MemberManageEvt implements ActionListener, MouseListener{
	
	private MemberManageView mmv;
//	private MemberService memberService;
	
	public MemberManageEvt(MemberManageView mmv) {
		
		this.mmv=mmv;
//		this.memberService=new MemberService();
		
		
		this.mmv.getJbtnSearch().addActionListener(this);
		this.mmv.getJbtnselect().addActionListener(this);
		this.mmv.getJbtnDelete().addActionListener(this);
		this.mmv.getJbtnReset().addActionListener(this);
		this.mmv.getJbtnPointAdd().addActionListener(this);
		this.mmv.getJbtnPointSubtract().addActionListener(this);
		this.mmv.getJbtnStempAdd().addActionListener(this);
		this.mmv.getJbtnStempSubtract().addActionListener(this);

//		loadMember();
		
		
	}
	
	//DB 연결
	private void loadMember() {

		
		

	}
	


	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == mmv.getJbtnSearch()) {
			
			search();
			
		}else if(e.getSource() == mmv.getJbtnselect()) {
			int result = JOptionPane.showConfirmDialog(mmv, "선택하시겠습니까?", "일괄 선택", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	select();
	            
	        }
			
		}else if(e.getSource() == mmv.getJbtnDelete()) {
			int result = JOptionPane.showConfirmDialog(mmv, "삭제하시겠습니까?", "회원 삭제", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	Delete();
	            
	        }
			
		}else if(e.getSource() == mmv.getJbtnReset()) {
			int result = JOptionPane.showConfirmDialog(mmv, "초기화 하시겠습니까?", "초기화", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	Reset();
	            
	        }
			
		}else if(e.getSource() == mmv.getJbtnPointAdd()) {
			int result = JOptionPane.showConfirmDialog(mmv, "지급 하시겠습니까?", "지급", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	PointAdd();
	            
	        }
			
		}else if(e.getSource() == mmv.getJbtnPointSubtract()) {
			int result = JOptionPane.showConfirmDialog(mmv, "차감 하시겠습니까?", "차감", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	PointSubtract();
	            
	        }
			
		}else if(e.getSource() == mmv.getJbtnStempAdd()) {
			int result = JOptionPane.showConfirmDialog(mmv, "지급 하시겠습니까?", "지급", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	StempAdd();
	            
	        }
			
		}else if(e.getSource() == mmv.getJbtnStempSubtract()) {
			int result = JOptionPane.showConfirmDialog(mmv, "차감하시겠습니까?", "차감", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	        	
	        	StempSubtract();
	            
	        }
			
		}
		
		
	}


	
	private void search() {
		
	}
	
	
	private void select() {

		 // 테이블에 있는 모든 행을 선택
	    int rowCount = mmv.getJtblMember().getRowCount();
	    
	    // JTable의 모든 행을 선택
	    mmv.getJtblMember().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    mmv.getJtblMember().setRowSelectionInterval(0, rowCount - 1);
	      
	}

	private void Delete() {
		
		
	}




	private void Reset() {
		// TODO Auto-generated method stub
		
	}




	private void PointAdd() {
		// TODO Auto-generated method stub
		
	}




	private void PointSubtract() {
		// TODO Auto-generated method stub
		
	}




	private void StempAdd() {
		// TODO Auto-generated method stub
		
	}




	private void StempSubtract() {
		// TODO Auto-generated method stub
		
	}


	
	
	
	

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
////////////////////////////////////////////////////////////
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
