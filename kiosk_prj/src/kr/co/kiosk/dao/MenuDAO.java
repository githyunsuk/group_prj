package kr.co.kiosk.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kr.co.kiosk.vo.MenuVO;

public class MenuDAO {

	private static MenuDAO mDAO;

	private MenuDAO() {

	}// MenuDAO

	public static MenuDAO getInstance() {
		if (mDAO == null) {
			mDAO = new MenuDAO();
		}

		return mDAO;
	}// getInstance

	public void insertMenu(MenuVO mVO) throws SQLException {
		Connection con = null;
		PreparedStatement pstmt = null;
		DbConnection dbConn = DbConnection.getInstance();
		FileInputStream fis = null;

		try {
			con = dbConn.getConn();

			StringBuilder insertMenu = new StringBuilder();
			insertMenu.append("insert into menu").append("(category_id,menu_name,unit_name,weight,calorie,price,");
			if (!mVO.getImgName().isEmpty()) {
				insertMenu.append("image, img_name,");
			}
			insertMenu.append("notes)").append("values").append("(seq_menu_id.nextval,?,?,?,?,?,?,");
			if (!mVO.getImgName().isEmpty()) {
				insertMenu.append("?,?,");
			}
			insertMenu.append("?)");

			pstmt = con.prepareStatement(insertMenu.toString());

			int bindIdx = 0;
			pstmt.setInt(++bindIdx, mVO.getCategoryId());
			pstmt.setString(++bindIdx, mVO.getMenuName());
			pstmt.setString(++bindIdx, mVO.getUnitName());
			pstmt.setInt(++bindIdx, mVO.getWeight());
			pstmt.setInt(++bindIdx, mVO.getCalorie());
			pstmt.setInt(++bindIdx, mVO.getPrice());
			if (!mVO.getImgName().isEmpty()) {
				File file = new File(mVO.getImgName());
				if (file.exists()) {
					fis = new FileInputStream(file);
					pstmt.setBinaryStream(++bindIdx, fis, file.length());
					pstmt.setString(++bindIdx, file.getName());
				}
			}
			pstmt.setString(++bindIdx, mVO.getNotes());

			pstmt.executeUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dbConn.closeDB(null, pstmt, con);
			try {
				if (fis != null)
					fis.close();
			} catch (IOException ie) {
				ie.printStackTrace();
			}
		}
	}// insertMenu

	public int updateMenu(MenuVO mVO) throws SQLException {
		int rowCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;
		FileInputStream fis = null;

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder updateMenu = new StringBuilder();
			updateMenu.append("	update menu																	")
					  .append("	set menu_name=?, weight=?, calorie=?, price=?, notes=?, image=?, img_name=?	")
					  .append("	where menu_id=?																");

			pstmt = con.prepareStatement(updateMenu.toString());

			int bindIdx = 0;
			pstmt.setString(++bindIdx, mVO.getMenuName());
			pstmt.setInt(++bindIdx, mVO.getWeight());
			pstmt.setInt(++bindIdx, mVO.getCalorie());
			pstmt.setInt(++bindIdx, mVO.getPrice());
			pstmt.setString(++bindIdx, mVO.getNotes());
			if (!mVO.getImgName().isEmpty()) {
				File file = new File(mVO.getImgName());
				if (file.exists()) {
					fis = new FileInputStream(file);
					pstmt.setBinaryStream(++bindIdx, fis, file.length());
					pstmt.setString(++bindIdx, file.getName());
				}
			}
			pstmt.setInt(++bindIdx, mVO.getMenuId());
			rowCnt = pstmt.executeUpdate();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			dbCon.closeDB(null, pstmt, con);
		}

		return rowCnt;
	}//updateMenu

	public int deleteMenu(int menuId) throws SQLException {
		int rowCnt = 0;

		Connection con = null;
		PreparedStatement pstmt = null;

		DbConnection dbCon = DbConnection.getInstance();
		try {
			con = dbCon.getConn();

			StringBuilder deleteMenu = new StringBuilder();
			deleteMenu.append("	delete from menu	").append("	where menu_id=?		");

			pstmt = con.prepareStatement(deleteMenu.toString());

			pstmt.setInt(1, menuId);

			rowCnt = pstmt.executeUpdate();

		} finally {
			dbCon.closeDB(null, pstmt, con);
		}
		return rowCnt;
	}// deleteMenu

	// 모든 메뉴 불러오기
	public List<MenuVO> selectAllMenu() throws SQLException, IOException {
		List<MenuVO> list = new ArrayList<MenuVO>();

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		InputStream is = null; // blob
		FileOutputStream fos = null; // blob

		BufferedReader br = null; // clob

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder selectMenu = new StringBuilder();
			selectMenu.append("	select menu_id, category_id, menu_name, unit_name, weight, calorie, price, notes, image, img_name	")
					  .append("	from menu 																							");

			pstmt = con.prepareStatement(selectMenu.toString());

			rs = pstmt.executeQuery();

			MenuVO mVO = null;
			while (rs.next()) {
				mVO = new MenuVO();
				mVO.setMenuId(rs.getInt("menu_id"));
				mVO.setCategoryId(rs.getInt("category_id"));
				mVO.setMenuName(rs.getString("menu_name"));
				mVO.setUnitName(rs.getString("unit_name"));
				mVO.setWeight(rs.getInt("weight"));
				mVO.setCalorie(rs.getInt("calorie"));
				mVO.setPrice(rs.getInt("price"));
				mVO.setImgName(rs.getString("img_name"));

				// clob
				Clob clobNotes = rs.getClob("notes");
				if (clobNotes != null) {
					br = new BufferedReader(clobNotes.getCharacterStream());

					String tempNotes = "";
					StringBuilder sbNotes = new StringBuilder();

					while ((tempNotes = br.readLine()) != null) {
						sbNotes.append(tempNotes).append("\n");
					}
					mVO.setNotes(sbNotes.toString());
				}

				// blob
				is = rs.getBinaryStream("image");
				if (is != null) {
					File dir = new File("c:/dev/img");
					if (!(dir.exists())) {
						dir.mkdirs();
					}
					File saveFile = new File(dir.getAbsoluteFile() + File.separator + mVO.getImgName());
					fos = new FileOutputStream(saveFile);

					byte[] readImg = new byte[512];

					int dataLength = 0;

					while ((dataLength = is.read(readImg)) != -1) {
						fos.write(readImg, 0, dataLength);
					}
					fos.flush();
				}

				list.add(mVO);
			}

		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}

		return list;
	}// selectAllMenu

	public MenuVO selectMenu(int num) throws SQLException, IOException {
		MenuVO mVO = null;

		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		InputStream is = null; // blob
		FileOutputStream fos = null; // blob

		BufferedReader br = null; // clob

		DbConnection dbCon = DbConnection.getInstance();

		try {
			con = dbCon.getConn();
			StringBuilder selectMenu = new StringBuilder();
			selectMenu.append("	select menu_id, category_id, menu_name, unit_name, weight, calorie, price, notes, image, img_name	")
					  .append("	from menu  																							")
					  .append(" where menu_id=? 																					");

			pstmt = con.prepareStatement(selectMenu.toString());
			
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				mVO = new MenuVO();
				mVO.setMenuId(rs.getInt("menu_id"));
				mVO.setCategoryId(rs.getInt("category_id"));
				mVO.setMenuName(rs.getString("menu_name"));
				mVO.setUnitName(rs.getString("unit_name"));
				mVO.setWeight(rs.getInt("weight"));
				mVO.setCalorie(rs.getInt("calorie"));
				mVO.setPrice(rs.getInt("price"));
				mVO.setImgName(rs.getString("img_name"));

				// clob
				Clob clobNotes = rs.getClob("notes");
				if (clobNotes != null) {
					br = new BufferedReader(clobNotes.getCharacterStream());

					String tempNotes = "";
					StringBuilder sbNotes = new StringBuilder();

					while ((tempNotes = br.readLine()) != null) {
						sbNotes.append(tempNotes).append("\n");
					}
					mVO.setNotes(sbNotes.toString());
				}

				// blob
				is = rs.getBinaryStream("image");
				if (is != null) {
					File dir = new File("c:/dev/img");
					if (!(dir.exists())) {
						dir.mkdirs();
					}
					File saveFile = new File(dir.getAbsoluteFile() + File.separator + mVO.getImgName());
					fos = new FileOutputStream(saveFile);

					byte[] readImg = new byte[512];

					int dataLength = 0;

					while ((dataLength = is.read(readImg)) != -1) {
						fos.write(readImg, 0, dataLength);
					}
					fos.flush();
				}

			}

		} finally {
			dbCon.closeDB(rs, pstmt, con);
		}

		return mVO;
	}// selectMenu
}// class
