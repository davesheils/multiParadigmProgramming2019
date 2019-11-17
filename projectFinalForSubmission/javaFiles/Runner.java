import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		Shop shop = new Shop("stock.csv");
		NewCustomer customer = new NewCustomer();
		System.out.println("Welcome to my Shop");
		// menu
		System.out.println("Get Customer details:");
		System.out.println("Please choose option 1 for CSV mode or option 2 for Live Mode");
		Scanner input = new Scanner(System.in);
		int option = input.nextInt();
		if (option == 1)
		{
			customer.getDetailsCSV();
		}
		if (option == 2)
		{
			customer.getDetailsLive();
		}	
		// Process order
		shop.processOrder(customer);
		shop.printShop(); // to show revised stock quantities
		System.out.println("Customer balance is " + customer.getBudget()+ "\n");
		System.out.println("Program completed");
	}
}
