public class ProductStock {
	private Product product;
	private int quantity;



	public ProductStock(Product product, int quantity) {
		super();
		this.product = product;
		this.quantity = quantity;
	}

	public Product getProduct() {
		return product;
	}

	public int getQuantity() {
		return quantity;
	}
	// setter method to update quantity of items in stock	
	public void setQuantity(int qty) {
		quantity = qty;
	}


	@Override
	public String toString() {
		return "ProductStock [product=" + product + ", quantity=" + quantity + "]";
	}

}
