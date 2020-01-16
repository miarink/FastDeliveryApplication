import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// This is the subject class of Observer pattern
public class OrderingSystem {
    private List<Order> unassignedOrders = new ArrayList<Order>();
    private List<Driver> unassignedDrivers = new ArrayList<Driver>();


    public void notify(Order order, boolean rushHour) {
        //check available drivers
        List<Driver> availableDrivers = new ArrayList<>();

        for(Driver driver: unassignedDrivers){
            if(driver.status != Driver.DriverStatus.AVAILABLE || driver.distanceToStores[order.shop.id] > 5){
                continue;
            }
            boolean orderHasFrozen= order.products.stream().anyMatch(product -> product.properties.contains(Product.ProductProperty.FROZEN));
            if (orderHasFrozen) {
                boolean driverHasFreezer = driver.hasRefrigerator;
                if (rushHour && !driverHasFreezer) {
                    continue;
                } else if (!rushHour && order.customer.distanceToStores[order.shop.id] > 2 && !driverHasFreezer) {
                    continue;
                }
            }

            availableDrivers.add(driver);
        }

        Map<Driver, Integer> driverDistances = new HashMap<>();
        for (Driver driver : availableDrivers) {
            int driverDistance = driver.update(order);
            driverDistances.put(driver, driverDistance);
        }

        availableDrivers.sort((d1, d2) -> driverDistances.get(d1) - driverDistances.get(d2));
        Driver cloestDriver = availableDrivers.get(0);
        cloestDriver.currentOrder = order;
        cloestDriver.status = Driver.DriverStatus.AVAILABLE;
        cloestDriver.distanceToDestination = cloestDriver.distanceToStores[order.shop.id];

        unassignedDrivers.remove(cloestDriver);
        unassignedOrders.remove(order);

    }

    public void assignOrders(){
        for(Order order: new ArrayList<>(unassignedOrders)){

            //Send order notification to all drivers. when driver receives order notification
            // driver send back status information

            notify(order,true);


        }
    }

    public void add(Order order){
        unassignedOrders.add(order);
    }

    public void driverBecomesAvailable(Driver driver){

        unassignedDrivers.add(driver);
    }
}
