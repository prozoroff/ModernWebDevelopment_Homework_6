package streams.auction;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by prozorov on 11/09/14.
 */
public class AuctionEmulator {

    public static void main(String[] args)
    {

        User[] users = new User[]{
                new User(0, "Gomer", "gomer@springfield.com", true),
                new User(1, "Lisa", "lisa@springfield.com", true),
                new User(2, "Bart", "bart@springfield.com", true)
        };

        int maximumAmount = 3000;

        ProductsRepository productsRepository = new ProductsRepository();
        BidService bidService = new BidService(productsRepository);


        Timer timer = new Timer();

        TimerTask winnerTask = new TimerTask() {
            @Override
            public void run() {

                List<Bid> winnersBids = bidService.getBids(c->c.isWinning);

                System.out.println("***************************************");

                for(Iterator<Bid> bids = winnersBids.iterator(); bids.hasNext(); ) {
                    Bid winnerBid = bids.next();

                    System.out.println("User: " + winnerBid.user.name + " won product: " + winnerBid.product.title + " with amount: " + winnerBid.amount);
                }

                System.out.println("***************************************");
            }
        };

        timer.schedule(winnerTask, 11000);

        for(int i = 1; i < 100; i++)
        {
            TimerTask userTimerTask = new TimerTask() {
                @Override
                public void run() {
                    int userIndex = new Random().nextInt(3);
                    Product product = productsRepository.getRandomProduct();
                    BigDecimal amount = BigDecimal.valueOf(new Random().nextInt(maximumAmount));
                    String result = bidService.AddBid(product, users[userIndex],amount,2);
                    System.out.println(users[userIndex].name + " " + result );
                }
            };

            timer.schedule(userTimerTask, new Random().nextInt(10000));
        }

        boolean working = true;
        while (working)
        {
            timer.purge();
        }

    }


}
