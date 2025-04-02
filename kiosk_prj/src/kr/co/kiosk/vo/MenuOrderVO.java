package kr.co.kiosk.vo;

public class MenuOrderVO {

	private int orderId, menuId, categoryId, quantity;

	public MenuOrderVO() {
	
	}

	public MenuOrderVO(int orderId, int menuId, int categoryId, int quantity) {
		this.orderId = orderId;
		this.menuId = menuId;
		this.categoryId = categoryId;
		this.quantity = quantity;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "MenuOrderVO [orderId=" + orderId + ", menuId=" + menuId + ", categoryId=" + categoryId + ", quantity="
				+ quantity + "]";
	}
	
	
	
	
}