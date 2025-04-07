package kr.co.kiosk.adminEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.adminView.MemberManageView;
import kr.co.kiosk.service.MemberService;
import kr.co.kiosk.vo.MemberVO;

public class MemberManageEvt implements ActionListener, MouseListener {

    private MemberManageView mmv;
    private MemberService memberService;

    public MemberManageEvt(MemberManageView mmv) {
        this.mmv = mmv;
        this.memberService = new MemberService();

        this.mmv.getJbtnSearch().addActionListener(this);
        this.mmv.getJbtnselect().addActionListener(this);
        this.mmv.getJbtnDelete().addActionListener(this);
        this.mmv.getJbtnPointAdd().addActionListener(this);
        this.mmv.getJbtnPointSubtract().addActionListener(this);
        this.mmv.getJbtnStempAdd().addActionListener(this);
        this.mmv.getJbtnStempSubtract().addActionListener(this);
        this.mmv.getJbtnLevelOk().addActionListener(this); 
        this.mmv.getJtblMember().addMouseListener(this);

        loadMember();
    }

    private void loadMember() {
        DefaultTableModel model = (DefaultTableModel) mmv.getJtblMember().getModel();
        model.setRowCount(0);

        List<MemberVO> member = memberService.searchAllMember();

        for (MemberVO vo : member) {
            model.addRow(new Object[]{
                vo.getMemberId(),
                vo.getPhoneNumber(),
                vo.getTotalAmount(),
                vo.getPoints(),
                vo.getStamps(),
                vo.getLevelId()
//                LevelIdToName(vo.getLevelId())
            });
        }
    }
    
//    private int LevelNameToId(String name) {
//        switch (name) {
//            case "뱀": return 1;
//            case "이무기": return 2;
//            case "용": return 3;
//            case "쌍용": return 4;
//            default: return 0;
//        }
//    }
//
//
//
//    private String LevelIdToName(int id) {
//        switch (id) {
//            case 1: return "뱀";
//            case 2: return "이무기";
//            case 3: return "용";
//            case 4: return "쌍용";
//            default: return "기타";
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == mmv.getJbtnSearch()) {
            search();
        
        }else if(e.getSource()==mmv.getJbtnselect()) {
        	if(JOptionPane.showConfirmDialog(mmv, "선택하시겠습니까?", "일괄 선택", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        		select();
        	}
        } else if (e.getSource() == mmv.getJbtnDelete()) {
            if (JOptionPane.showConfirmDialog(mmv, "삭제하시겠습니까?", "회원 삭제", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                Delete();
            }
        
        } else if (e.getSource() == mmv.getJbtnPointAdd()) {
        	
        	if (JOptionPane.showConfirmDialog(mmv, "포인트 지급하시겠습니까?", "지급", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        		PointAdd();
            }
         
        } else if (e.getSource() == mmv.getJbtnPointSubtract()) {

        	if (JOptionPane.showConfirmDialog(mmv, "포인트 차감하시겠습니까?", "차감", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        		PointSubtract();
            }
         
        } else if (e.getSource() == mmv.getJbtnStempAdd()) {

        	if (JOptionPane.showConfirmDialog(mmv, "스탬프 지급하시겠습니까?", "지급", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        		StempAdd();
            }
         
        } else if (e.getSource() == mmv.getJbtnStempSubtract()) {

        	if (JOptionPane.showConfirmDialog(mmv, "스탬프 차감하시겠습니까?", "차감", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
        		StempSubtract();
            }
         
        }else if(e.getSource()==mmv.getJbtnLevelOk()) {
        	if(JOptionPane.showConfirmDialog(mmv, "확인하시겠습니까?","확인", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
        		LevelOk();
        	}
        }
    }

    private void LevelOk() {
    	  int selectedRow = mmv.getJtblMember().getSelectedRow();
    	  if(selectedRow == -1) {
    		  JOptionPane.showMessageDialog(mmv, "회원을 선택해주세요");
    		  return;
    	  }
    	  
    	  String selectLevel= (String) mmv.getCb().getSelectedItem();
    	  
//    	  switch(selectLevel) {
//    	  case "뱀": return 1;
//    	  
//    	  }
//    	  
    	  
		
	}

	private void search() {
        String keyword = mmv.getJtfSearch().getText().trim().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) mmv.getJtblMember().getModel();

        if (keyword.isEmpty()) {
            loadMember();
            return;
        }

        DefaultTableModel filteredModel = new DefaultTableModel(new String[]{"회원ID", "전화번호", "누적금액", "포인트", "스탬프", "등급"}, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString().toLowerCase();
            if (id.contains(keyword)) {
                filteredModel.addRow(new Object[]{
                    model.getValueAt(i, 0),
                    model.getValueAt(i, 1),
                    model.getValueAt(i, 2),
                    model.getValueAt(i, 3),
                    model.getValueAt(i, 4),
                    model.getValueAt(i, 5)
                });
            }
        }

        mmv.getJtblMember().setModel(filteredModel);
    }

    private void select() {
    	 int rowCount = mmv.getJtblMember().getRowCount();
    	   
    	 if (rowCount == 0) {
    	        JOptionPane.showMessageDialog(mmv, "선택할 회원이 없습니다.");
    	        return;
    	    }

    	    mmv.getJtblMember().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
    	    mmv.getJtblMember().setRowSelectionInterval(0, rowCount - 1);
    }

    private void Delete() {
        int row = mmv.getJtblMember().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(mmv, "삭제할 회원을 선택하세요.");
            return;
        }

        int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);
        if (memberService.removeMember(memberId)) {
            JOptionPane.showMessageDialog(mmv, "삭제 성공");
            loadMember();
        } else {
            JOptionPane.showMessageDialog(mmv, "삭제 실패");
        }
    }

    private void PointAdd() {
    	  int[] rows = mmv.getJtblMember().getSelectedRows();
    	    int point = Integer.parseInt(mmv.getJtfPoint().getText().trim());

    	    for (int row : rows) {
    	        int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);

    	        try {
    	            MemberVO memVO = memberService.searchMember(memberId);
    	            memVO.setPoints(memVO.getPoints() + point);
    	            memberService.modifyMember(memVO);
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    }
    	    loadMember();
    	}
    

    private void PointSubtract() {
    	  int[] rows = mmv.getJtblMember().getSelectedRows();
    	    int point = Integer.parseInt(mmv.getJtfPoint().getText().trim());

    	    for (int row : rows) {
    	        int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);

    	        try {
    	            MemberVO memVO = memberService.searchMember(memberId);
    	            memVO.setPoints(memVO.getPoints() - point);
    	            memberService.modifyMember(memVO);
    	        } catch (Exception e) {
    	            e.printStackTrace();
    	        }
    	    }
    	    loadMember();
        
    }

    private void StempAdd() {
        int[] rows = mmv.getJtblMember().getSelectedRows();
        int stemp = Integer.parseInt(mmv.getJtfStemp().getText().trim());

        for (int row : rows) {
            int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);

            try {
                MemberVO memVO = memberService.searchMember(memberId);
                memVO.setStamps(memVO.getStamps() + stemp);
                memberService.modifyMember(memVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loadMember();
    }

    private void StempSubtract() {
    	int[] rows = mmv.getJtblMember().getSelectedRows();
        int stemp = Integer.parseInt(mmv.getJtfStemp().getText().trim());

        for (int row : rows) {
            int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);

            try {
                MemberVO memVO = memberService.searchMember(memberId);
                memVO.setStamps(memVO.getStamps() - stemp);
                memberService.modifyMember(memVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loadMember();
    }

    
    
    
    
    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
