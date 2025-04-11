package kr.co.kiosk.adminEvt;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import kr.co.kiosk.adminView.MenuManageView;
import kr.co.kiosk.service.MenuService;
import kr.co.kiosk.vo.MenuVO;

public class MenuManageEvt implements ActionListener, MouseListener {
    private MenuManageView mv;
    private MenuService menuService;
    
    
    private String imagePath;

    public MenuManageEvt(MenuManageView mv) {
        this.mv = mv;
        this.menuService = new MenuService();

        this.mv.getJbtnAdd().addActionListener(this);
        this.mv.getJbtnEdit().addActionListener(this);
        this.mv.getJbtnReset().addActionListener(this);
        this.mv.getJbtnDelete().addActionListener(this);
        this.mv.getJbtnFind().addActionListener(this);
        this.mv.getJbtnSearch().addActionListener(this);
        this.mv.getJtblMenu().addMouseListener(this);

        loadMenu();
    }

    
    //DB 연결
    private void loadMenu() {
    	
    	DefaultTableModel model=(DefaultTableModel) mv.getJtblMenu().getModel();
    	model.setRowCount(0);
    	
    	List<MenuVO> menuList=menuService.searchAllMenu();
    	
    	for(MenuVO vo:menuList) {
    		model.addRow(new Object[] {
    				 vo.getMenuId(),
    	             categoryIdToName(vo.getCategoryId()),
    	             vo.getMenuName(),
    	             vo.getImgName(),
    	             vo.getWeight(),
    	             vo.getUnitName(),
    	             vo.getCalorie(),
    	             vo.getPrice(),
    	             vo.getNotes()

    		});
    	}
    	
    }
    
    
    

    private String categoryIdToName(int id) {
        switch (id) {
            case 1: return "세트!";
            case 2: return "버거";
            case 3: return "사이드";
            case 4: return "음료";
            case 5: return "재료";
            default: return "기타";
        }
    }

    private int categoryNameToId(String name) {
        switch (name) {
            case "세트": return 1;
            case "버거": return 2;
            case "사이드": return 3;
            case "음료": return 4;
            case "재료": return 5;
            default: return 0;
        }
    }

    
    //메뉴 추가
    private void addMenu() {
    	
    	if (!Input()) return;
       
    try {
    	int categoryId= categoryNameToId((String)  mv.getJcbCategory().getSelectedItem());
    	String name= mv.getJtfName().getText().trim();
        String imgName=mv.getImgName();
        int weight=Integer.parseInt(mv.getJtfWeight().getText().trim());
        int calorie = Integer.parseInt(mv.getJtfCalorie().getText().trim());
        int price = Integer.parseInt(mv.getJtfPrice().getText().trim());
        Timestamp inputDate = new Timestamp(System.currentTimeMillis()); 
        String unitName=(String) mv.getJcbUnitName().getSelectedItem();
        String notes = mv.getJtfExplain().getText().trim();

        MenuVO vo = new MenuVO(0, categoryId, name, unitName, weight, calorie, price, notes,inputDate, imgName);
        boolean result=menuService.addMenu(vo);
        
        if(result) {
        	JOptionPane.showMessageDialog(mv, "성공적으로 추가되었습니다.");
        	loadMenu();
        	resetFields();
        	
        }else {
        	JOptionPane.showMessageDialog(mv, "추가 실패하였습니다.");
        	
        }

    }catch(NumberFormatException ex) {
    	JOptionPane.showMessageDialog(mv, "숫자 입력란(N)을 확인해주세요.");

    }
  }
    
    
    
    //메뉴 수정
    private void editMenu() {
    	if (!Input()) return;
    	
    	int row=mv.getJtblMenu().getSelectedRow();
    	if(row==-1) {
    		JOptionPane.showMessageDialog(mv, "메뉴를 선택해주세요");
    		return;
    		
    	}
        
    try {
    	int MenuId=(int) mv.getJtblMenu().getValueAt(row, 0);
    	int categoryId= categoryNameToId((String)  mv.getJcbCategory().getSelectedItem());
    	String name= mv.getJtfName().getText().trim();
        String imgName=mv.getImgName();
        int weight=Integer.parseInt(mv.getJtfWeight().getText().trim());
        int calorie = Integer.parseInt(mv.getJtfCalorie().getText().trim());
        int price = Integer.parseInt(mv.getJtfPrice().getText().trim());
        Timestamp inputDate = new Timestamp(System.currentTimeMillis()); 
        String notes = mv.getJtfExplain().getText().trim();
    	
        MenuVO vo = new MenuVO(MenuId, categoryId, name, null,  weight, calorie, price, notes, null, imgName);
        
        boolean result=menuService.modifyMenu(vo);
        if (result) {
            JOptionPane.showMessageDialog(mv, "수정 성공");
            loadMenu();
            resetFields();
        } else {
            JOptionPane.showMessageDialog(mv, "수정 실패");
        }
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(mv, "입력값 오류 확인");
    }

    }

    
    //메뉴 삭제
    private void deleteMenu() {
        int row=mv.getJtblMenu().getSelectedRow();
        if(row==-1) {
        	JOptionPane.showMessageDialog(mv, "메뉴를 선택해주세요");
        	return;
        	
        }
        
        int menuId=(int) mv.getJtblMenu().getValueAt(row, 0);
        boolean result=menuService.removeMenu(menuId);
        
        if(result) {
        	JOptionPane.showMessageDialog(mv, "성공적으로 삭제되었습니다.");
        	loadMenu();
        	resetFields();
        }else {
        	JOptionPane.showMessageDialog(mv, "삭제 실패");
        }
    }

    
    
    private void resetFields() {
       mv.getJcbCategory().setSelectedIndex(0);
       mv.getJtfName().setText("");
       mv.getJtfImage().setText("");
       mv.getJtfWeight().setText("");
       mv.getJtfCalorie().setText("");
       mv.getJtfPrice().setText("");
       mv.getJtfExplain().setText("");
       mv.getJcbUnitName().setSelectedIndex(0);
       
       
    }
    
  
    private void findImage(){
     JFileChooser chooser=new JFileChooser();
     int result = chooser.showOpenDialog(mv);

     if (result == JFileChooser.APPROVE_OPTION) {
         File selectedFile = chooser.getSelectedFile();
         String fileName = selectedFile.getName();
         String targetPath = "c:/dev/img/" + fileName;

         try {
            
             BufferedImage img = ImageIO.read(selectedFile);
             if (img == null) {
                 throw new IOException("선택한 파일이 이미지 파일이 아닙니다.");
             }
             
             
             Files.copy(selectedFile.toPath(), new File(targetPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
             mv.getJtfImage().setText(fileName);
             
             ImageIcon ic=new ImageIcon(targetPath);
             Image tempImg = ic.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
             ImageIcon realIc = new ImageIcon(tempImg);
             
            
             
             mv.getJlblImage().setIcon(realIc);
             imagePath =fileName;
             mv.setImgName(targetPath);
             
             

         } catch (IOException e) {
             JOptionPane.showMessageDialog(mv, "이미지 저장 오류: " + e.getMessage());
         
         	}
     	}
     
    }

    
   //메뉴명 검색
    private void searchMenu() {
    	String keyword=mv.getJtfSearch().getText().trim().toLowerCase();
    	
    	if(keyword.isEmpty()) {
    		loadMenu();
    		return;
    	}
    	
    	DefaultTableModel model=(DefaultTableModel) mv.getJtblMenu().getModel();
    	DefaultTableModel filteredModel=new DefaultTableModel(new String[] 
    			{"MenuId","카테고리", "메뉴명", "사진 경로", "중량", "단위", "칼로리", "가격","설명"}, 0);
    	for (int i = 0; i < model.getRowCount(); i++) {
            String menuName = model.getValueAt(i, 2).toString().toLowerCase();
            if (menuName.contains(keyword)) {
                filteredModel.addRow(new Object[] {
                    model.getValueAt(i, 0),
                    model.getValueAt(i, 1),
                    model.getValueAt(i, 2),
                    model.getValueAt(i, 3),
                    model.getValueAt(i, 4),
                    model.getValueAt(i, 5),
                    model.getValueAt(i, 6),
                    model.getValueAt(i, 7),
                    model.getValueAt(i, 8)
                });
            }
        }

        mv.getJtblMenu().setModel(filteredModel);
    }
    
    // "필수 입력란을 입력해주세요."
    private boolean Input() {
        if (mv.getJtfName().getText().trim().isEmpty() || mv.getJtfImage().getText().trim().isEmpty()
        	|| mv.getJtfWeight().getText().trim().isEmpty() || mv.getJtfCalorie().getText().trim().isEmpty()
        	|| mv.getJtfPrice().getText().trim().isEmpty()|| mv.getJtfExplain().getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(mv, "필수 입력란(*)을 입력해주세요.");
            return false;
        }
        return true;
    }

    
    //행을 선택하면 그 행을 가져옴
    @Override
    public void mouseClicked(MouseEvent e) {
    	
    	  int row = mv.getJtblMenu().getSelectedRow();
          if (row == -1) return;
          
          mv.getJcbCategory().setSelectedItem(mv.getJtblMenu().getValueAt(row, 1));
          mv.getJtfName().setText((String) mv.getJtblMenu().getValueAt(row, 2));
          
          MenuService ms = new MenuService();
          MenuVO mVO = ms.searchMenuWithName((String) mv.getJtblMenu().getValueAt(row, 2));
          System.out.println(mVO.getImage());
          
          String imgPath = (String) mv.getJtblMenu().getValueAt(row, 3);
          if (!imgPath.startsWith("c:/dev/img/")) {
              imgPath = "c:/dev/img/" + imgPath;
          }
         
          mv.getJtfImage().setText(imgPath); 
          
          ImageIcon ic = mVO.getImage();
          Image tempImg = ic.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
          ImageIcon realIc = new ImageIcon(tempImg);
          mv.getJlblImage().setIcon(realIc);
          
          //이 값은 나중에 DB에 저장하거나 이미지 처리할 때 사용될 수 있어요.
          mv.setImgName(imgPath);

          mv.getJtfWeight().setText(String.valueOf(mv.getJtblMenu().getValueAt(row, 4)));
          mv.getJcbUnitName().setSelectedItem(mv.getJtblMenu().getValueAt(row, 5));
          mv.getJtfCalorie().setText(String.valueOf(mv.getJtblMenu().getValueAt(row, 6)));
          mv.getJtfPrice().setText(String.valueOf(mv.getJtblMenu().getValueAt(row, 7)));
          mv.getJtfExplain().setText((String) mv.getJtblMenu().getValueAt(row, 8));
         
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

    	      } else if (e.getSource() == mv.getJbtnSearch()) {

    	         searchMenu();
    	      }

    	   }



    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}


}