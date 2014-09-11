package streams.auction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by yfain11 on 4/4/14.
 */
public class Bid {
    public int id;
    public Product product;
    public BigDecimal amount;
    public int desiredQuantity; // How many items the user wants
    public User user;
    public LocalDateTime bidTime;
    public boolean isWinning;

    public Bid(int id, Product product, BigDecimal amount, int desiredQuantity, User user, LocalDateTime bidTime, boolean isWinning)
    {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.desiredQuantity = desiredQuantity;
        this.user = user;
        this.bidTime = bidTime;
        this.isWinning = isWinning;
    }
}
