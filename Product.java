import java.util.Arrays;
import java.util.EnumSet;

public class Product {
    public String name;
    public ProductType type;
    public EnumSet<ProductProperty> properties = EnumSet.noneOf(ProductProperty.class);

    enum ProductType{
        FOOD,FLOWER,CHOCOLATE
    }

    enum ProductProperty {
        WARM, FROZEN
    }

    public Product(String name, ProductType type, ProductProperty... properties) {
        this.name = name;
        this.type = type;
        this.properties.addAll(Arrays.asList(properties));
    }
}
