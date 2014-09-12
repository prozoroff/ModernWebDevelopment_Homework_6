package streams.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by prozorov on 11/09/14.
 */
public class ProductsRepository {

    private List<Product> products;

    public ProductsRepository()
    {
        products = new ArrayList<Product>();

        products.add( new Product(0, "bicycle", "thumb1", "Jamis", 10, LocalDateTime.now(),5, BigDecimal.valueOf(1000), BigDecimal.valueOf(1000)));
        products.add( new Product(1, "motorbike", "thumb2", "Honda", 15, LocalDateTime.now(),5, BigDecimal.valueOf(1500), BigDecimal.valueOf(1500)));
        products.add( new Product(2, "car", "thumb3", "Landrover", 7, LocalDateTime.now(),5, BigDecimal.valueOf(2000), BigDecimal.valueOf(2000)));
    }


    public List<Product> getProducts()
    {
        return products;
    }

    public Product getRandomProduct()
    {
        int randIndex = new Random().nextInt(products.size());
        return products.get(randIndex);
    }

    public void UpdateReservedPrice(int id, BigDecimal amount)
    {
        Product oldProduct = products.stream().
                filter(p->p.id == id).collect(Collectors.toList()).get(0);
        int index = products.indexOf(oldProduct);

        products.get(index).reservedPrice = amount;
    }
}
