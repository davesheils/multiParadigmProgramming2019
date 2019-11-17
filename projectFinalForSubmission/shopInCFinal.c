#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <string.h>

struct Product {
	char* name;
	double price;
};

struct ProductStock {
	struct Product product;
	int quantity;
};

struct Shop {
	double cash;
	struct ProductStock stock [20];
	int index;

};

struct Customer {
	char* name;
	double budget;
	struct ProductStock shoppingList [10]; // array with 10 items
	int index; // keeps track of number of items in shoppingList
	};

void printProduct (struct Product p){
	printf("-------------------\n");
	printf("PRODUCT NAME: %s \n PRODUCT PRICE: %.2f\n", p.name, p.price);
	printf("-------------------\n");
}

struct Shop createAndStockShop()
{
	// read stock.csv line by line
	// for each line, create a stuct productStock
	struct Shop shop = {200};
	FILE * fp;
    char * line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen("stock.csv", "r");
    if (fp == NULL)
        exit(EXIT_FAILURE);
	// fp = file pointer
	
	int lineNumber = 1;

    while ((read = getline(&line, &len, fp)) != -1) {
        if (lineNumber == 1)
		{
			double openingBalance = atof(line);
			// printf("OPENING BALANCE IS %.2f\n", openingBalance);
			shop.cash = openingBalance;
			// printf("", read);
		}
		else
		{
			// break line into fields using string tokeniser
			char *n = strtok(line, ",");
		char *p = strtok(NULL, ",");
		char *q = strtok(NULL, ",");
		int quantity = atoi(q);
		double price = atof(p);
		char *name = malloc(sizeof(char) * 50);
		strcpy(name, n);
		struct Product product = { name, price };
		struct ProductStock stockItem = { product, quantity };
		shop.stock[shop.index++] = stockItem;
		// printf("NAME OF PRODUCT %s PRICE %.2f QUANTITY %d\n", name, price, quantity);
		}
		lineNumber = lineNumber + 1;
	}
	return shop;
}




void printShop(struct Shop s)
{
	printf("Shop has %.2f in cash\n", s.cash);
	for (int i = 0; i < s.index; i++)
	{
		printProduct(s.stock[i].product);
		printf("The shop has %d of the above\n", s.stock[i].quantity);
	}
}



struct Customer getCustomerCSV()
{

	struct Customer customer; //  = { 200 };
	FILE * fp;
    char * line = NULL;
    size_t len = 0;
    ssize_t read;

    fp = fopen("orders.csv", "r");
    if (fp == NULL)
        exit(EXIT_FAILURE);
	// fp = file pointer
	
	int lineNumber = 1;

    while ((read = getline(&line, &len, fp)) != -1) {
        if (lineNumber == 1)
		{
			// break line into fields using string tokeniser
			char *n = strtok(line, ",");
			char *b = strtok(NULL, ",");
			char *cname = malloc(sizeof(char) * 50);
			strcpy(cname, n);					
			customer.name = cname;
			double cbudget = atof(b);
			customer.budget = cbudget;
		}
		else
		{
			// break line into fields using string tokeniser
			char *n = strtok(line, ",");
			char *q = strtok(NULL, ",");
			// char *q = strtok(NULL, ",");
			int quantity = atoi(q);
			char *pname = malloc(sizeof(char) * 50);
			strcpy(pname, n);
			struct Product product;
			product.name = pname;
			struct ProductStock stockItem = { product, quantity };
			customer.shoppingList[customer.index++] = stockItem;
			// printf("NAME OF PRODUCT %s PRICE %.2f QUANTITY %d\n", name, price, quantity);
		}
		lineNumber = lineNumber + 1;
	}
	return customer;
}

void printCustomer(struct Customer c){
	
	printf("CUSTOMER NAME: %s \n CUSTOMER BUDGET: %.2f\n", c.name, c.budget);
	
	for (int i = 0; i < c.index; i++)
	{
		printf("Item = %s, Quantity = %d\n",c.shoppingList[i].product.name,  c.shoppingList[i].quantity);
		
		// printProduct(c.shoppingList[i].product);
		// printf("%s ORDERS %d OF ABOVE PRODUCT\n", c.name, c.shoppingList[i].quantity);
		//printf("The cost to %s will be %.2f EUR.\n",c.name, c.shoppingList[i].quantity * c.shoppingList[i].product.price);
	}
	printf("-------------------\n");
	
};

struct Customer getCustomerLive()
{
	// create a customer from a user input
	// get customer details, first name and budget
	struct Customer c;
	double b;
	char *cname = malloc(sizeof(char) * 50);
	// create orders - this will be the customers shopping list
	// struct ProductStock orders [10]; // array with 10 items	
	printf("Hello customer! Please enter your first name:\n");
	scanf("%s", cname);
	printf("Hello %s, please enter your budget:\n", cname);
	scanf("%lf", &b);
	c.name = cname;
	double cbudget  = b;
	c.budget = cbudget;
	c.index = 0;
	 
	for (int i = 0; i < 10; i++)
		{
			// printf("Loop number %d", i);
			struct Product product;
			char *pname = malloc(sizeof(char) * 50);
			
			// prompt customer and recieve input
			printf("Please enter product you wish to buy\n");
			scanf("%s", pname);
						
			if (strcmp(pname, "end")==0)// aahh needs to be strcmp!!!
			{
				break; 
			}
			else
			{
			product.name = pname;
			product.price = 0;
			// product.price = 0;// price = 0 by default
 			
			// user inputs quantity
			printf("Please enter the quantity of %s you wish to purchase:\n", pname);
			int q; // for quantity sought
			scanf("%d", &q); // 
			printf("You ordered %d of %s. This order has been added to your shopping list.\n", q, pname);
			// segmentation fault after this point - issue resolved
			// create product and add to shopping list
			struct ProductStock stockItem = { product, q};
			// printf("%s", stockItem.product.name);
			// printf("%d", stockItem.quantity);
			// add item to shopping list			// 
			c.shoppingList[c.index] = stockItem;
			// c.shoppingList[c.index++];
			c.index++;
			// printf("index is %d", c.index);
			// free(quantity);
			}
		}
		
	return c;
}




int main(void) {
	printf("Welcome to the shop!\n");
	struct Shop shop = createAndStockShop();
	printShop(shop);


	/*

	User chooses CSV mode or Interactive mode

	The following section is commented out.
	This section resulted ina segemtation fault.
	The issue could not be resolved in the timescale for submission.
	Therefore, the program will go straight to interactive mode.

	int option;
	printf("Press 1 to place order by CSV or 2 for interactive mode:\n");
	
	scanf("%d", &option); // 
	
	
	if (option == "1")
	{
		printf("you choose option %s CSV Mode.\n", option);
		struct Customer customer = getCustomerCSV();
	}
	else if (option == 2)
	{
		printf("you choose option %s Interactive Mode. \n", option);
		struct Customer customer = getCustomerLive();
	}

	*/
	struct Customer customer = getCustomerLive();



	// Processing the customers order:
	for (int i = 0; i < customer.index; i++)
		{
			char *order = customer.shoppingList[i].product.name;
			int qtySought = customer.shoppingList[i].quantity;
			// printf("You ordered %d %s.\n", qtySought, order);
			
			for (int i = 0; i < shop.index; i++)
			{
				char *stockItem = shop.stock[i].product.name;
				// printf("Item %d named %s\n", i, stockItem);
				
				if (strcmp(stockItem, order) == 0)
				{
					// get price of item
					double price = shop.stock[i].product.price;
					// get quantity ordered
					int qtyInStock = shop.stock[i].quantity;
					// Total cost of this/these items will be ..
				
					printf("Product %s is stock. You ordered %.d.\n", order, qtySought);
					printf("The unit price will be %.2f", price);
					// if qtySought > qtyInStock, cap qtySought at number of items in stock
					if (qtySought > qtyInStock)
						{
							printf("There are only %d of this item\n", qtyInStock);
							qtySought = qtyInStock;
						}
					double cost = qtySought * price;
					printf("The total cost to you will be %.2f\n", cost);
					// Can the customer afford to purchase this aount of items
					if (cost <= customer.budget)
					{
						// increase shops cash balance
						shop.cash += cost;
						// reduce customers budget
						customer.budget -= cost;
						// reduce number of items in shop stock list
						shop.stock[i].quantity -= qtySought;
						printf("transaction completed\n");
						printf("Your remaining budget is %.2f\n",customer.budget);						
					}
					else
					{
						printf("You have insufficient funds to comlpete this transaction.\n");
					}

				}
			}
		}
	printShop(shop);
	printf("Customer %s has %.2f in cash.", customer.name, customer.budget);
	return 0;
}