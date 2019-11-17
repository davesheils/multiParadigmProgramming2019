import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class NewCustomer {

	private String name;
	private double budget;
	private ArrayList<ProductStock> shoppingList;

    // constructor 
    public NewCustomer() 
    {
		// New Customer can only be initialised in csv mode or live mode
		
		/*
		this.name = cname;
        this.budget = cbudget;
		this.shoppingList = cList;
		*/
    }
    // getters	
	public String getName() {
		return name;
	}

	public double getBudget() {
		return budget;
	}

	public ArrayList<ProductStock> getShoppingList() {
		return shoppingList;
	}
    // setters
    public  void setName(String cname)
    {
        this.name = cname;
    }

    public void setBudget(double cbudget)
    {
        this.budget = cbudget;
    }

    public void  setShoppingList(ArrayList<ProductStock> cList)
    {
        this.shoppingList = cList;
    }

	public void getDetailsCSV()
	{

		shoppingList = new ArrayList<>();
		// https://www.geeksforgeeks.org/different-ways-reading-text-file-java/
		List<String> lines = Collections.emptyList();
		try {
			lines = Files.readAllLines(Paths.get("orders.csv"), StandardCharsets.UTF_8);
			//System.out.println(lines.get(0)); 
			// cash = Double.parseDouble(lines.get(0)); // converts string to double
			String[] firstLine = lines.get(0).split(",");
			name = firstLine[0];
			budget = Double.parseDouble(firstLine[1]);
			
			// I am removing value at index 0 as it is the only one treated differently
			lines.remove(0);
			
			for (String line: lines) {
				// splitting lines using regex
				String[] arr = line.split(",");
				String name = arr[0];
				// double price = Double.parseDouble(arr[1]); // convert string to double
				int quantity = Integer.parseInt(arr[1].trim()); // convert string to int
				Product p = new Product(name, 0);
				ProductStock s = new ProductStock(p, quantity);
				// System.out.println(p);
				shoppingList.add(s);
			}
		}
		catch (IOException e) {

			// do something
			e.printStackTrace();
		}
	}

	public void getDetailsLive()
	{
		// create scanner object
		Scanner input = new Scanner(System.in);
		
		// get name
		System.out.println("Welcome to interactive mode.\n Please enter your first name.");
		name = input.nextLine();

		System.out.println("Hello " + name);
		// get opening balance
		System.out.println("Please enter your budget.");
		budget= input.nextDouble();

		// create shopping list array
		ArrayList<ProductStock> orders = new ArrayList<>();
		// max 10 items
		
		/*
		System.out.println("Please enter product you wish to buy.");
		String pName = input.next();
		System.out.println(pName);
		*/	
				
				for (int i = 0; i < 10;   i++ )
				{
					// user inputs product name
					System.out.println("Please enter product you wish to buy (enter end (lowercase) to complete).");
					String pName = input.next();
					// System.out.println(pName);
					if (pName.equals("end")) 
						{break;}
					else 
					{
						Product p = new Product(pName, 0);
						// user inputs quantity
						System.out.println("Please enter quantity you wish to buy.");
						int qty = input.nextInt();
						// create product and add to shopping list
						ProductStock s = new ProductStock(p, qty);
						orders.add(s);
					} 
				}

			

			// this.name = cname;
			// this.budget = customerBudget;
			this.shoppingList = orders;


	}

	public void printDetails()
	{
		System.out.println(name + " has a a budget of " + budget + "\n");
		System.out.println("This is the list of orders ");
		for (ProductStock order: shoppingList)
		{
			System.out.println(order.getProduct().getName());
			System.out.println(name + " has ordered " + order.getQuantity() + " of the above item.\n");
			System.out.println("---------------------------------------------------------.\n");
		}
	}


	// @Override
	public String toString() {
		String ret = "Customer [name=" + name + ", budget=" + budget + ", shoppingList=\n";
		/*for (ProductStock ProductStock : shoppingList)
		{
			ret += ProductStock.getProduct().getName() + "Quantity:" + ProductStock.getQuantity() + "\n";
		}
		*/
		return ret + "]";
	}

	public static void main(String[] args) {
		// NewCustomer David = new NewCustomer("orders.csv");
		System.out.println("New Customer:");


	}
	
	
}
