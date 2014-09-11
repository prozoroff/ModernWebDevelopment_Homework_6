package streams.auction;

import java.math.BigDecimal;
import java.time.*;

/**
 * Created by yfain11 on 4/4/14.
 */
public class Product {
    public int id;
    public String title;
    public String thumb;
    public String description;
    public int quantity;   // How many items the seller has
    public LocalDateTime auctionEndTime;
    public int watchers;
    public BigDecimal minimalPrice;     // Don't sell unless the bid is more than min price
    public BigDecimal reservedPrice;   // If a bidder offers reserved price, the auction is closed

    public Product(int id, String title, String thumb, String description, int quantity, LocalDateTime auctionEndTime,
                   int watchers, BigDecimal minimalPrice, BigDecimal reservedPrice)
    {
        this.id = id;
        this.thumb = thumb;
        this.description = description;
        this.quantity = quantity;
        this.auctionEndTime = auctionEndTime;
        this.watchers = watchers;
        this.minimalPrice = minimalPrice;
        this.reservedPrice = reservedPrice;
    }
}
