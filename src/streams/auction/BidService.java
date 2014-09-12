package streams.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Created by prozorov on 11/09/14.
 */
public class BidService {

    private List<Bid> bids;
    private ProductsRepository productRepository;
    private final StampedLock sl = new StampedLock();

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
        long stamp = sl.writeLock();

        try {
            int id = bids.size();
            LocalDateTime now = LocalDateTime.now();

            List<Bid> productBids = bids.stream().filter(b -> b.product.id == product.id).sorted(Comparator.comparing(b -> b.amount)).collect(Collectors.toList());

            if (productBids.size() > 0 && (productBids.get(productBids.size() - 1).amount.compareTo(amount) == -1))
                return "Email: Sorry, there is at least one amount that larger than your: " +
                        productBids.get(productBids.size() - 1).amount + " by user " + productBids.get(productBids.size() - 1).user.name;

            if (product.minimalPrice.compareTo(amount) == 1)
                return "Email: Sorry, your bid amount must be greater than minimal price: " +
                        product.minimalPrice;

            if (product.quantity < desiredQuantity)
                return "Error: Not enough products";

            Bid newBid = new Bid(bids.size(), product, amount, desiredQuantity, user, LocalDateTime.now(), true);

            CleanUpBids(product.id);
            bids.add(newBid);

            productRepository.UpdateReservedPrice(product.id, amount);

            return "Email: Good job! Your bid are winning now :)";
        }
        finally {
            sl.unlockWrite(stamp);
        }
    }

    private void CleanUpBids(int productId)
    {
        for(Iterator<Bid> allBids = bids.stream().filter(b->b.product.id == productId).iterator(); allBids.hasNext(); ) {
            allBids.next().isWinning = false;
        }
    }


}
