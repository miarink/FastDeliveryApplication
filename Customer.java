public class Customer {
    public int id;
    public int[] distanceToStores;
    public int birth_date;

    public Customer(int id, int[] distanceToStores, int birth_date) {
        this.id = id;
        this.distanceToStores = distanceToStores;
        this.birth_date = birth_date;
    }
}
