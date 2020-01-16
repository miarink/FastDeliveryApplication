import java.util.List;

public class Order {
    public int id;
    public Shop shop;
    public Customer customer;
    public List<Product> products;



    public Order(int id, Shop shop, Customer customer, List<Product> products) {
        this.id = id;
        this.shop = shop;
        this.customer = customer;
        this.products = products;
    }
}
