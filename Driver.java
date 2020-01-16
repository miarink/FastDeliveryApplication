// This is the observer class of Observer pattern
public class Driver {
    public int id;
    public OrderingSystem orderingSystem;
    public Order currentOrder;
    public boolean hasRefrigerator;
    public int[] distanceToStores;
    public DriverStatus status;
    public int distanceToDestination;

    enum DriverStatus{
        AVAILABLE,DRIVE_TO_STORE,DRIVE_TO_CUSTOMER
    }

    public Driver(int id, boolean hasRefrigerator, int[] distanceToStores,DriverStatus status) {
        this.id = id;
        this.hasRefrigerator = hasRefrigerator;
        this.distanceToStores = distanceToStores;
        this.status = status;
    }

    public void setDistanceToStores(int[] distanceToStores) {
        this.distanceToStores = distanceToStores;
    }

    public int[] getDistanceToStores() {
        return distanceToStores;
    }

    public DriverStatus getStatus() {
        return status;
    }

    public void setStatus(DriverStatus status) {
        this.status = status;
    }

    public int getDistanceToDestination() {
        return distanceToDestination;
    }

    public void setDistanceToDestination(int distanceToDestination) {
        this.distanceToDestination = distanceToDestination;
    }

    public int update(Order order){
        return this.distanceToStores[order.shop.id];
    }

}
