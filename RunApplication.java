import java.util.*;
import java.util.stream.Collectors;

public class Simulation {
    List<Product> products = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();
    List<Shop> shops = new ArrayList<>();
    List<Driver> drivers = new ArrayList<>();
    private final List<Product> flowers;
    private final List<Product> food;
    private final List<Product> chocolates;
    private final Random rnd;

    public Simulation() {


        //create 10 customers
        rnd = new Random(1);
        for (int i = 0; i < 10; i++) {
            int[] distanceToStores = new int[5];
            for (int j = 0; j < 5; j++) {
                distanceToStores[j] = rnd.nextInt(10) + 1;
            }
            Customer customer = new Customer(i, distanceToStores, i);
            customers.add(customer);
        }

        //create products
        Product iceCream = new Product("iceCream", Product.ProductType.FOOD, Product.ProductProperty.FROZEN);
        Product frozenYogurt = new Product("frozenYogurt", Product.ProductType.FOOD, Product.ProductProperty.FROZEN);
        Product eggFlowerSoup = new Product("eggFlowerSoup", Product.ProductType.FOOD, Product.ProductProperty.WARM);
        Product friedRice = new Product("friedRice", Product.ProductType.FOOD, Product.ProductProperty.WARM);
        Product dutchChocolate = new Product("dutchChocolate", Product.ProductType.CHOCOLATE);
        Product cocoaChocolate = new Product("cocoaChocolate", Product.ProductType.CHOCOLATE);
        Product lily = new Product("lily", Product.ProductType.FLOWER);
        Product rose = new Product("rose", Product.ProductType.FLOWER)
        products.add(iceCream);
        products.add(frozenYogurt);
        products.add(eggFlowerSoup);
        products.add(friedRice);
        products.add(dutchChocolate);
        products.add(cocoaChocolate);
        products.add(lily);
        products.add(rose);

        //create 5 shops, each of them has 5 products, which include all three types
        flowers = products.stream().filter(product -> product.type == Product.ProductType.FLOWER).collect(Collectors.toList());
        food = products.stream().filter(product -> product.type == Product.ProductType.FOOD).collect(Collectors.toList());
        chocolates = products.stream().filter(product -> product.type == Product.ProductType.CHOCOLATE).collect(Collectors.toList());

        for (int i = 0; i < 5; i++) {
            List<Product> productsOfShop = new ArrayList<>();
            productsOfShop.add(flowers.get(rnd.nextInt(flowers.size())));
            productsOfShop.add(food.get(rnd.nextInt(food.size())));
            productsOfShop.add(chocolates.get(rnd.nextInt(chocolates.size())));
            productsOfShop.add(products.get(rnd.nextInt(products.size())));
            productsOfShop.add(products.get(rnd.nextInt(products.size())));

            Shop shop = new Shop(i, productsOfShop);
            shops.add(shop);
        }

        //create 10 drivers
        for (int i = 0; i < 10; i++) {
            int[] driverDistanceToStores = new int[5];
            for (int j = 0; j < 5; j++) {
                driverDistanceToStores[j] = rnd.nextInt(10) + 1;
            }
            Driver driver = new Driver(i, i == 0 ? true : rnd.nextBoolean(), driverDistanceToStores, Driver.DriverStatus.AVAILABLE);
            drivers.add(driver);
        }
    }

    OrderingSystem orderingSystem = new OrderingSystem();

    public void runSimulation() {
        //for time == 1, until 10 orders have completed
        createOrders(1);
        for(int time = 1; time < 100; time++  ){
            orderingSystem.assignOrders();
        }

        for(Driver driver: drivers){
            if(driver.status == Driver.DriverStatus.DRIVE_TO_STORE ){
                driver.distanceToDestination--;
                if(driver.distanceToDestination == 0){
                    driver.status = Driver.DriverStatus.DRIVE_TO_CUSTOMER;
                    driver.distanceToDestination = driver.currentOrder.customer.distanceToStores[driver.currentOrder.shop.id];
                }
            }
            else if(driver.status == Driver.DriverStatus.DRIVE_TO_CUSTOMER){
                driver.distanceToDestination--;
                if(driver.distanceToDestination == 0){
                    driver.status = Driver.DriverStatus.AVAILABLE;
                    driver.distanceToStores = driver.currentOrder.customer.distanceToStores;
                    driver.currentOrder = null;
                    orderingSystem.driverBecomesAvailable(driver);
                }
            }
        }
    }

    public static void main(String[] args) {
        new Simulation().runSimulation();
    }

    private void createOrders(int time) {
        //randomly create 20 orders
        for(int i = 0; i < 20; i++){
            List<Product> newProductList = new ArrayList<>(products);
            Collections.shuffle(newProductList);
            int totalItems = rnd.nextInt(Product.ProductType.values().length)+1;
            List<Product> chosenProductList = new ArrayList<>();
            Set<Product.ProductType> productTypes = new HashSet<>();

            for(Product currentProduct: newProductList){
                if(!productTypes.contains(currentProduct.type)) {
                    productTypes.add(currentProduct.type);
                    chosenProductList.add(currentProduct);
                    if(chosenProductList.size() == totalItems){
                        break;
                    }
                }
            }

            //check if customer birthday -> add chocolate and flowers
            Customer customer = customers.get(rnd.nextInt(customers.size()));
            if(customer.birth_date == time){
                if(!productTypes.contains(Product.ProductType.CHOCOLATE)) {
                    chosenProductList.add(chocolates.get(rnd.nextInt(chocolates.size())));
                }
                if(!productTypes.contains(Product.ProductType.FLOWER)){
                    chosenProductList.add(flowers.get(rnd.nextInt(flowers.size())));
                }
            }

            Shop shop = shops.get(rnd.nextInt(shops.size()));
            Order order = new Order(i,shop,customer,chosenProductList);
            orderingSystem.add(order);
        }
    }
}
