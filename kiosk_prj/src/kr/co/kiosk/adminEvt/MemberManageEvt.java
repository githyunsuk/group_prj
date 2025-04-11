package kr.co.kiosk.adminEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
            });
        }
    }
    


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
        	if(JOptionPane.showConfirmDialog(mmv, "변경하시겠습니까?","확인", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION) {
        		LevelOk();
        	}
        }
    }

    private void LevelOk() {
    	
    	
  	  int selectedRow = mmv.getJtblMember().getSelectedRow();
  	  if(selectedRow == -1) {
  		  JOptionPane.showMessageDialog(mmv, "회원을 선택해주세요");
  	  }
  	  
  	  int memberId = (int) mmv.getTableModel().getValueAt(selectedRow, 0);
  	  
  	  String selectLevel= ((String) mmv.getCb().getSelectedItem()).trim();
  	  int levelId=0;
  	  
  	  switch(selectLevel) {
  	  case "뱀": levelId =1; break;
  	  case "이무기":  levelId=2; break;
  	  case "용": levelId=3;break;
  	  case "쌍용": levelId=4;break;
  	  
  	  }
  
  	  MemberVO memVO = memberService.searchMember(memberId);
  	  memVO.setLevelId(levelId);
  	  
  	    boolean result = memberService.modifyMember(memVO);
  	   
  	    if (result) {
  	        JOptionPane.showMessageDialog(mmv, "등급이 성공적으로 변경되었습니다.");
  	        loadMember(); 
  	    } else {
  	        JOptionPane.showMessageDialog(mmv, "등급 변경에 실패했습니다.");
  	    }
		

	}
    
    
	private void search() {
        String keyword = mmv.getJtfSearch().getText().trim();
        DefaultTableModel model = (DefaultTableModel) mmv.getJtblMember().getModel();

        if (keyword.isEmpty()) {
            loadMember();
            return;
        }

        DefaultTableModel filteredModel = new DefaultTableModel(new String[]{"회원ID", "전화번호", "누적금액", "포인트", "스탬프", "등급"}, 0);

        for (int i = 0; i < model.getRowCount(); i++) {
            String id = model.getValueAt(i, 0).toString();
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

    	   if (rows.length == 0) {
   	        JOptionPane.showMessageDialog(mmv, "회원이 선택되지 않았습니다. 회원을 선택해주세요.");
   	        return; 
   	    }
    	   
    	   String pointText = mmv.getJtfPoint().getText().trim();
    	   if (pointText.isEmpty()) {
    	        JOptionPane.showMessageDialog(mmv, "포인트를 입력해주세요.");
    	        return;
    	    }

    	   int point = Integer.parseInt(pointText);

    	  
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

        if (rows.length == 0) {
            JOptionPane.showMessageDialog(mmv, "회원이 선택되지 않았습니다. 회원을 선택해주세요.");
            return; 
        }

        String pointText = mmv.getJtfPoint().getText().trim();
        if (pointText.isEmpty()) {
            JOptionPane.showMessageDialog(mmv, "포인트를 입력해주세요.");
            return;
        }

        int point = Integer.parseInt(pointText);

        for (int row : rows) {
            int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);

            try {
                MemberVO memVO = memberService.searchMember(memberId);
                int currentPoints = memVO.getPoints();

                if (currentPoints < point) {
                    JOptionPane.showMessageDialog(mmv, "보유 포인트보다 많은 포인트를 차감할 수 없습니다.");
                    continue;
                }

                memVO.setPoints(currentPoints - point);
                memberService.modifyMember(memVO);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        loadMember();
    }

    private void StempAdd() {
        int[] rows = mmv.getJtblMember().getSelectedRows();
      
        if (rows.length == 0) {
	        JOptionPane.showMessageDialog(mmv, "회원이 선택되지 않았습니다. 회원을 선택해주세요.");
	        return; 
	    }
        
	    
	   String StempText = mmv.getJtfStemp().getText().trim();
 	   if (StempText.isEmpty()) {
 	        JOptionPane.showMessageDialog(mmv, "스탬프를 입력해주세요.");
 	        return;
 	    }

 	   int stemp = Integer.parseInt(StempText);
 	   
        
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
       

        if (rows.length == 0) {
	        JOptionPane.showMessageDialog(mmv, "회원이 선택되지 않았습니다. 회원을 선택해주세요.");
	        return; 
	    }
        
        String StempText = mmv.getJtfStemp().getText().trim();

	    if (StempText.isEmpty()) {
	        JOptionPane.showMessageDialog(mmv, "스탬프를 입력해주세요.");
	        return;
	    }
	   

 	   int stemp = Integer.parseInt(StempText);

        
        for (int row : rows) {
            int memberId = (int) mmv.getJtblMember().getValueAt(row, 0);

            try {
                MemberVO memVO = memberService.searchMember(memberId);
                int currentStemp = memVO.getStamps();
                
                if (currentStemp < stemp) {
                	JOptionPane.showMessageDialog(mmv, "보유 스탬프보다 많은 스탬프를 차감할 수 없습니다.");
                	continue;
                }//end if
                
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
