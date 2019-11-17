import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shop {

	private double cash;
	private ArrayList<ProductStock> stock;
	// no need for index in

	public Shop(String fileName) {
		stock = new ArrayList<>();
		// https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get(fileName), StandardCharsets.UTF_8);
			System.out.println(lines.get(0)); 
			cash = Double.parseDouble(lines.get(0)); // converts string to double
			lines.remove(0);
			// I am removing value at index 0 as it is the only one treated differently
			for (String line: lines) {
				// splitting lines using regex
				String[] arr = line.split(",");
				String name = arr[0];
				double price = Double.parseDouble(arr[1]); // convert string to double
				int quantity = Integer.parseInt(arr[2]); // convert string to int
				Product p = new Product(name, price);
				ProductStock s = new ProductStock(p, quantity);
				// System.out.println(p);
				// System.out.println(s);
				stock.add(s);
				
				
			}
		}
		catch (IOException e) {

			// do something
			e.printStackTrace();
		}

	}

	public double getCash() {
		return cash;
	}

	public ArrayList<ProductStock> getStock() {
		return stock;
	}

	// method to process order

	public void printShop()
	{
		System.out.println("Shop opening balance is " + String.format("%.2f", cash) + "\n");
		for (ProductStock item: stock)
		{
			System.out.println(item.getProduct().getName());
			System.out.println("Shop has " + item.getQuantity() + " of the above in stock.\n");
			System.out.println("---------------------------------------------------------.\n");
		}
	}

	public void processOrder(NewCustomer c) {
		// Get the customer balance/budget
		System.out.println("Shop opening balance is " + String.format("%.2f", cash) + "\n");
		System.out.println("Customer opening balance is " + String.format("%.2f",c.getBudget()) + "\n");
		
		ArrayList<ProductStock> orders =  c.getShoppingList();
		for (ProductStock order: orders)
		{
			double budget = c.getBudget();
			System.out.println("Item Ordered: " + order.getQuantity()  + " "+ order.getProduct().getName() + "\n");
			for (ProductStock item: stock)
			{

				
				// System.out.println(item.getProduct().getName());
				if (order.getProduct().getName().equals(item.getProduct().getName()) )
					{
						// If item is in stock:
						System.out.println("Product: " +order.getProduct().getName() + " is in stock");
						System.out.println("You ordered " + order.getQuantity() + " of this item");
						// System.out.println("Price will be "+ item.getProduct().getPrice());
						int qtySought = order.getQuantity();
						// if qtySought > qty in stock, cap qtySought
						if (qtySought > item.getQuantity())
						{
							qtySought = item.getQuantity();
							System.out.println("There are only " + qtySought + " of this item");
						}
						// can customer afford item(s)?
						double cost = item.getProduct().getPrice() * qtySought;
						if (cost <= budget)
						{
							cash += cost; // increse shop cash balance
							budget -= cost; // reduce budget
							c.setBudget(budget); // update customer budget
							int newQty = item.getQuantity() - qtySought;
							item.setQuantity(newQty); // reduce quantity of item in stock
							System.out.println("Price will be "+ item.getProduct().getPrice());
							System.out.println("The cost to you will be  " + String.format("%.2f", cost));
													
						}						
						else
						{
						
						System.out.println("The cost to you will be  " + String.format("%.2f", cost) + "; however, You have insufficient funds for this transaction.\n");
						}
					}
				}
		}
		}
	


	public void setCash(double c) {
		this.cash = c;
	}

	public void  setStock(ArrayList<ProductStock> PS) {
		this.stock = PS;
	}


	// @Override
	public String toString() {
		return "Shop [cash=" + cash + ", stock=" + stock + "]";
	}

	public static void main(String[] args) {
		Shop shop = new Shop("stock.csv");
	}
}
