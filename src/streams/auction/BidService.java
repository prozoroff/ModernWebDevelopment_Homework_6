package streams.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;

/**
 * Created by prozorov on 11/09/14.
 */
public class BidService {

    private List<Bid> bids;
    private ProductsRepository productRepository;

    public  BidService( ProductsRepository productRepository)
    {
        bids = new ArrayList<Bid>();
        this.productRepository = productRepository;
    }

    public List<Bid> getBids(Predicate<Bid> predicate)
    {
        return bids.stream().filter(predicate).collect(Collectors.toList());
    }

    public List<Bid> getBids()
    {
        return bids;
    }

    public String AddBid(Product product, User user, BigDecimal amount, int desiredQuantity)
    {
        int id = bids.size();
        LocalDateTime now = LocalDateTime.now();

        List<Bid> productBids = bids.stream().filter(b->b.product.id == product.id).sorted(Comparator.comparing(b->b.amount)).collect(Collectors.toList());

        if(productBids.size()>0 && (productBids.get(productBids.size()-1).amount.compareTo(amount)==-1))
            return "Error: There is at least one amount that larger than your";

        if(product.quantity < desiredQuantity)
            return "Error: Not enough products";

            if(productBids.size()>0)
        productBids.get(productBids.size()-1).isWinning = false;

        Bid newBid = new Bid(bids.size(),product,amount,desiredQuantity,user,LocalDateTime.now(),true);

        bids.add(newBid);

        productRepository.UpdateReservedPrice(product.id,amount);

        return "OK";
    }

}
