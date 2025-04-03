package kr.co.kiosk.adminEvt;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.adminView.MenuManageView;

public class MenuManageEvt implements ActionListener, MouseListener {

	private MenuManageView mv;
	
	public MenuManageEvt(MenuManageView mv) {
		this.mv=mv;
		this.mv.getJbtnAdd().addActionListener(this);
		this.mv.getJbtnEdit().addActionListener(this);
		this.mv.getJbtnReset().addActionListener(this);
		this.mv.getJbtnDelete().addActionListener(this);
		this.mv.getJbtnFind().addActionListener(this);
		
		mv.getJtblMenu().addMouseListener(this);
		mv.getJbtnSearch().addActionListener(this);
		
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == mv.getJbtnAdd()) {
			int result = JOptionPane.showConfirmDialog(mv, "메뉴를 추가하시겠습니까?", "메뉴 추가", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	            addMenu();
	        }
        } else if (e.getSource() == mv.getJbtnEdit()) {
        	int result = JOptionPane.showConfirmDialog(mv, "메뉴를 수정하시겠습니까?", "메뉴 수정", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	           editMenu();
	        }
        } else if (e.getSource() == mv.getJbtnDelete()) {
        	int result = JOptionPane.showConfirmDialog(mv, "메뉴를 삭제하시겠습니까?", "메뉴 삭제", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	            deleteMenu();
	        }
        } else if (e.getSource() == mv.getJbtnReset()) {
        	int result = JOptionPane.showConfirmDialog(mv, "초기화 하시겠습니까?", "초기화", JOptionPane.YES_NO_OPTION);
	        if (result == JOptionPane.YES_OPTION) {
	           resetFields();
	        }
        } else if (e.getSource() == mv.getJbtnFind()) {
        	
	            findImage();
	        
        }else if(e.getSource()==mv.getJbtnSearch()) {
        	
        	searchMenu();
        }

	}
	


	private void searchMenu() {
		
		 String searchText = mv.getJtfSearch().getText().trim().toLowerCase();
		    DefaultTableModel model = mv.getTableModel();
		    DefaultTableModel filteredModel = new DefaultTableModel(new String[]{"카테고리", "메뉴ID", "메뉴명", "사진 경로", "중량","칼로리","가격"}, 0);

		    if (searchText.isEmpty()) {
		        // 검색어가 없으면 원래 테이블로 복원
		        mv.getJtblMenu().setModel(model);
		        return;
		    }

		    for (int i = 0; i < model.getRowCount(); i++) {
		        String menuName = model.getValueAt(i, 0).toString().toLowerCase();
		        if (menuName.contains(searchText)) {
		            filteredModel.addRow(new Object[]{
		                model.getValueAt(i, 0),
		                model.getValueAt(i, 1),
		                model.getValueAt(i, 2),
		                model.getValueAt(i, 3),
		                model.getValueAt(i, 4),
		                model.getValueAt(i, 5),
		                model.getValueAt(i, 6),
		            });
		        }
		    }

		    mv.getJtblMenu().setModel(filteredModel);
		    
	}

	public void addMenu() {
			
		String category=mv.getJtfCategory().getText();
		String number=mv.getJtfNumber().getText();
		String name=mv.getJtfName().getText();
		String image=mv.getJtfImage().getText();
		String weight=mv.getJtfWeight().getText();
		String calorie=mv.getJtfCalorie().getText();
		String price=mv.getJtfPrice().getText();
		String explain=mv.getJtfExplain().getText();
		
		DefaultTableModel model = (DefaultTableModel) mv.getJtblMenu().getModel();
        model.addRow(new Object[]{category, number, name, image, weight, calorie, price, explain});

		
//		resetFields();  // 필드 초기화

		
	}
	private void editMenu() {
		
	    int selectedRow = mv.getJtblMenu().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(mv, "수정할 메뉴를 선택하세요.");
            return;
        }
        mv.getJtblMenu().setValueAt(mv.getJtfCategory().getText(), selectedRow, 0);
        mv.getJtblMenu().setValueAt(mv.getJtfNumber().getText(), selectedRow, 1);
        mv.getJtblMenu().setValueAt(mv.getJtfName().getText(), selectedRow, 2);
        mv.getJtblMenu().setValueAt(mv.getJtfImage().getText(), selectedRow, 3);
        mv.getJtblMenu().setValueAt(mv.getJtfWeight().getText(), selectedRow, 4);
        mv.getJtblMenu().setValueAt(mv.getJtfCalorie().getText(), selectedRow, 5);
        mv.getJtblMenu().setValueAt(mv.getJtfPrice().getText(), selectedRow, 6);
        mv.getJtblMenu().setValueAt(mv.getJtfExplain().getText(), selectedRow, 7);

        resetFields();  // 필드 초기화

	}
	private void deleteMenu() {
		 int selectedRow = mv.getJtblMenu().getSelectedRow();
	        if (selectedRow == -1) {
	            JOptionPane.showMessageDialog(mv, "삭제할 메뉴를 선택하세요.");
	            return;
	        }

	        DefaultTableModel model = (DefaultTableModel) mv.getJtblMenu().getModel();
	        model.removeRow(selectedRow);


	        resetFields();  // 필드 초기화

	}
	
	
	private void findImage() {

		JFileChooser fileChooser=new JFileChooser();
		fileChooser.setDialogTitle("이미지 파일 선택");
		 fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png", "gif"));

		
		
		int result=fileChooser.showOpenDialog(mv);
		
		if(result == JFileChooser.APPROVE_OPTION) {
			
			String imagePath=fileChooser.getSelectedFile().getAbsolutePath();
			mv.getJtfImage().setText(imagePath);
			
			ImageIcon icon=new ImageIcon(imagePath);
			mv.getJlblImage().setIcon(icon);
			
		}
	}
	
	 private void showMenuDetails() {
	        int selectedRow = mv.getJtblMenu().getSelectedRow();
	        if (selectedRow != -1) {
	            String category = (String) mv.getJtblMenu().getValueAt(selectedRow, 0);
	            String number = (String) mv.getJtblMenu().getValueAt(selectedRow, 1);
	            String name = (String) mv.getJtblMenu().getValueAt(selectedRow, 2);
	            String imagePath = (String) mv.getJtblMenu().getValueAt(selectedRow, 3);
	            String weight = (String) mv.getJtblMenu().getValueAt(selectedRow, 4);
	            String calorie = (String) mv.getJtblMenu().getValueAt(selectedRow, 5);
	            String price = (String) mv.getJtblMenu().getValueAt(selectedRow, 6);
	            String explain = (String) mv.getJtblMenu().getValueAt(selectedRow, 7);

	            // 텍스트 필드에 값 세팅
	            mv.getJtfCategory().setText(category);
	            mv.getJtfNumber().setText(number);
	            mv.getJtfName().setText(name);
	            mv.getJtfImage().setText(imagePath);
	            mv.getJtfWeight().setText(weight);
	            mv.getJtfCalorie().setText(calorie);
	            mv.getJtfPrice().setText(price);
	            mv.getJtfExplain().setText(explain);

	            // 이미지 경로가 비어있지 않으면 JLabel에 이미지 표시
	            if (imagePath != null && !imagePath.trim().isEmpty()) {
	                ImageIcon icon = new ImageIcon(imagePath);
	                mv.getJlblImage().setIcon(icon);
	            } else {
	                mv.getJlblImage().setIcon(null);  // 이미지 없을 경우 기본 상태로
	            }
	        }
	    }

	
	
	private void resetFields() {
			mv.getJtfCategory().setText("");
	        mv.getJtfNumber().setText("");
	        mv.getJtfName().setText("");
	        mv.getJtfImage().setText("");
	        mv.getJtfWeight().setText("");
	        mv.getJtfCalorie().setText("");
	        mv.getJtfPrice().setText("");
	        mv.getJtfExplain().setText("");

		
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {

		  int selectedRow = mv.getJtblMenu().getSelectedRow();
	        if (selectedRow != -1) {
	            // 테이블에서 선택된 행의 데이터를 가져와 텍스트 필드에 설정
	            mv.getJtfCategory().setText(mv.getJtblMenu().getValueAt(selectedRow, 0).toString());
	            mv.getJtfNumber().setText(mv.getJtblMenu().getValueAt(selectedRow, 1).toString());
	            mv.getJtfName().setText(mv.getJtblMenu().getValueAt(selectedRow, 2).toString());
	            mv.getJtfImage().setText(mv.getJtblMenu().getValueAt(selectedRow, 3).toString());
	            mv.getJtfWeight().setText(mv.getJtblMenu().getValueAt(selectedRow, 4).toString());
	            mv.getJtfCalorie().setText(mv.getJtblMenu().getValueAt(selectedRow, 5).toString());
	            mv.getJtfPrice().setText(mv.getJtblMenu().getValueAt(selectedRow, 6).toString());
	            mv.getJtfExplain().setText(mv.getJtblMenu().getValueAt(selectedRow, 7).toString());
	        }
	}

	
	
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