import java.util.*;

class Stock {
    String symbol;
    String name;
    double price;

    Stock(String symbol, String name, double price) {
        this.symbol = symbol;
        this.name = name;
        this.price = price;
    }
}

class User {
    double balance;
    Map<String, Integer> portfolio = new HashMap<>();

    User(double initialBalance) {
        this.balance = initialBalance;
    }

    void buy(Stock stock, int quantity) {
        double cost = stock.price * quantity;
        if (balance >= cost) {
            balance -= cost;
            portfolio.put(stock.symbol, portfolio.getOrDefault(stock.symbol, 0) + quantity);
            System.out.println("Bought " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough balance.");
        }
    }

    void sell(Stock stock, int quantity) {
        int owned = portfolio.getOrDefault(stock.symbol, 0);
        if (owned >= quantity) {
            balance += stock.price * quantity;
            portfolio.put(stock.symbol, owned - quantity);
            System.out.println("Sold " + quantity + " shares of " + stock.symbol);
        } else {
            System.out.println("Not enough shares.");
        }
    }

    void viewPortfolio(Map<String, Stock> stockList) {
        System.out.println("\nYour Portfolio:");
        for (Map.Entry<String, Integer> entry : portfolio.entrySet()) {
            Stock stock = stockList.get(entry.getKey());
            double value = stock.price * entry.getValue();
            System.out.printf("%s (%s): %d shares | Value: INR %.2f\n",
                    stock.name, stock.symbol, entry.getValue(), value);
        }
        System.out.printf("Available Balance: INR %.2f\n", balance);
    }
}

class StockMarket {
    Map<String, Stock> stockList = new HashMap<>();

    StockMarket() {
        addStock("INFY", "Infosys", 1400);
        addStock("TCS", "TCS", 3600);
        addStock("RELI", "Reliance", 2800);
    }

    void addStock(String symbol, String name, double price) {
        stockList.put(symbol, new Stock(symbol, name, price));
    }

    void displayMarket(){
        System.out.println("\nMarket Data:");
        for (Stock s : stockList.values()) {
            System.out.printf("%s (%s): INR %.2f\n", s.name, s.symbol, s.price);
        }
    }

    Stock getStock(String symbol) {
        return stockList.get(symbol.toUpperCase());
    }

    Map<String, Stock> getAllStocks() {
        return stockList;
    }
}

public class TradingPlatform {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StockMarket market = new StockMarket();
        User user = new User(10000); // Starting balance ₹10,000

        System.out.println("Welcome to the Stock Trading Platform!");

        while (true) {
            System.out.println("\nMenu:\n1. View Market\n2. Buy Stock\n3. Sell Stock\n4. View Portfolio\n5. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // flush

            switch (choice) {
                case 1:
                    market.displayMarket();
                    break;
                case 2:
                    System.out.print("Enter stock symbol to buy: ");
                    String buySym = sc.nextLine().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int buyQty = sc.nextInt();
                    sc.nextLine();
                    Stock buyStock = market.getStock(buySym);
                    if (buyStock != null)
                        user.buy(buyStock, buyQty);
                    else
                        System.out.println("Invalid stock symbol.");
                    break;
                case 3:
                    System.out.print("Enter stock symbol to sell: ");
                    String sellSym = sc.nextLine().toUpperCase();
                    System.out.print("Enter quantity: ");
                    int sellQty = sc.nextInt();
                    sc.nextLine();
                    Stock sellStock = market.getStock(sellSym);
                    if (sellStock != null)
                        user.sell(sellStock, sellQty);
                    else
                        System.out.println("Invalid stock symbol.");
                    break;
                case 4:
                    user.viewPortfolio(market.getAllStocks());
                    break;
                case 5:
                    System.out.println("Exiting... Thank you!");
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
