package kr.co.kiosk.adminEvt;

import java.awt.event.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.List;
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
        DefaultTableModel model = (DefaultTableModel) mv.getJtblMenu().getModel();
        model.setRowCount(0);

        List<MenuVO> menuList = menuService.searchAllMenu();

        for (MenuVO vo : menuList) {
            model.addRow(new Object[] {
                vo.getMenuId(),
                categoryIdToName(vo.getCategoryId()),
                vo.getMenuName(),
                vo.getImage(),
                vo.getWeight(),
                vo.getUnitName(),
                vo.getCalorie(),
                vo.getPrice()
            });
        }
    }
    
    
    

    private String categoryIdToName(int id) {
        switch (id) {
            case 1: return "세트";
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
        try {
            String name = mv.getJtfName().getText().trim();
            int categoryId = categoryNameToId((String) mv.getJcbCategory().getSelectedItem());
            int weight = Integer.parseInt(mv.getJtfWeight().getText().trim());
            int calorie = Integer.parseInt(mv.getJtfCalorie().getText().trim());
            int price = Integer.parseInt(mv.getJtfPrice().getText().trim());
            String unitName = (String) mv.getJcbUnitName().getSelectedItem();
            String image = mv.getJtfImage().getText().trim();
            String notes = mv.getJtfExplain().getText().trim();
            String imgName=mv.getImgName();
            Timestamp inputDate = new Timestamp(System.currentTimeMillis()); 

            MenuVO vo = new MenuVO(0, categoryId, name, unitName, image, weight, calorie, price, notes,inputDate, imgName);

            boolean result = menuService.addMenu(vo);
            if (result) {
                JOptionPane.showMessageDialog(mv, "메뉴가 성공적으로 추가되었습니다.");
                loadMenu();
                resetFields();
            } else {
                JOptionPane.showMessageDialog(mv, "메뉴 추가 실패.");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(mv, "숫자 입력란을 확인해주세요.");
        }
    }
    
    
    
    //메뉴 수정
    private void editMenu() {
        int row = mv.getJtblMenu().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(mv, "수정할 메뉴를 선택하세요.");
            return;
        }

        try {
            int menuId = (int) mv.getJtblMenu().getValueAt(row, 0);
            int categoryId = categoryNameToId((String) mv.getJcbCategory().getSelectedItem());
            String name = mv.getJtfName().getText().trim();
            int weight = Integer.parseInt(mv.getJtfWeight().getText().trim());
            int calorie = Integer.parseInt(mv.getJtfCalorie().getText().trim());
            int price = Integer.parseInt(mv.getJtfPrice().getText().trim());
            String unitName = (String) mv.getJcbUnitName().getSelectedItem();
            String image = mv.getJtfImage().getText().trim();
            String notes = mv.getJtfExplain().getText().trim();
            String imgName=mv.getImgName();

            MenuVO vo = new MenuVO(menuId, categoryId, name, unitName, image, weight, calorie, price, notes, null ,imgName);

            boolean result = menuService.modifyMenu(vo);
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
        int row = mv.getJtblMenu().getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(mv, "삭제할 메뉴를 선택하세요.");
            return;
        }

        int menuId = (int) mv.getJtblMenu().getValueAt(row, 0);
        boolean result = menuService.removeMenu(menuId);

        if (result) {
            JOptionPane.showMessageDialog(mv, "삭제 성공");
            loadMenu();
            resetFields();
        } else {
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

    
    //이미지 찾기
    private void findImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("이미지 파일 선택");
        chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("이미지 파일", "jpg", "jpeg", "png"));

        int result = chooser.showOpenDialog(mv);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            String fileName = selectedFile.getName();
            String targetPath = "c:/dev/img/" + fileName;

            try {
                Files.copy(selectedFile.toPath(), new File(targetPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
                mv.getJtfImage().setText(fileName);
                mv.getJlblImage().setIcon(new ImageIcon(targetPath));
                imagePath = fileName;
                
                mv.setImgName(targetPath); // 이미지 전체 경로 저장
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(mv, "이미지 저장 오류: " + e.getMessage());
            }
        }
    }

    
   //메뉴명 검색
    private void searchMenu() {
        String keyword = mv.getJtfSearch().getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            loadMenu();  // 전체 메뉴 다시 불러오기
            return;
        }
        
        
        
        DefaultTableModel model = (DefaultTableModel) mv.getJtblMenu().getModel();
        DefaultTableModel filteredModel = new DefaultTableModel(new String[] {
            "menuId", "카테고리", "메뉴명", "사진 경로", "중량", "unitname", "칼로리", "가격"
        }, 0);

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
                    model.getValueAt(i, 7)
                });
            }
        }

        mv.getJtblMenu().setModel(filteredModel);
    }

    
    //행을 선택하면 그 행을 가져옴
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = mv.getJtblMenu().getSelectedRow();
        if (row == -1) return;

        mv.getJcbCategory().setSelectedItem(mv.getJtblMenu().getValueAt(row, 1));
        mv.getJtfName().setText((String) mv.getJtblMenu().getValueAt(row, 2));
        mv.getJtfImage().setText((String) mv.getJtblMenu().getValueAt(row, 3));
        mv.getJtfWeight().setText(String.valueOf(mv.getJtblMenu().getValueAt(row, 4)));
        mv.getJcbUnitName().setSelectedItem(mv.getJtblMenu().getValueAt(row, 5));
        mv.getJtfCalorie().setText(String.valueOf(mv.getJtblMenu().getValueAt(row, 6)));
        mv.getJtfPrice().setText(String.valueOf(mv.getJtblMenu().getValueAt(row, 7)));
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