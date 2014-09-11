package streams.auction;

import java.math.BigDecimal;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by prozorov on 11/09/14.
 */
public class AuctionEmulator {

    public static void main(String[] args)
    {

        User[] users = new User[]{
                new User(0, "user1", "user1@gmail.com", true),
                new User(1, "user2", "user2@gmail.com", true),
                new User(2, "user3", "user3@gmail.com", true)
        };

        int maximumIncrease = 20;

        ProductsRepository productsRepository = new ProductsRepository();
        BidService bidService = new BidService(productsRepository);


        Timer timer = new Timer();

        TimerTask user1TimerTask = new TimerTask() {
            @Override
            public void run() {
                Product product = productsRepository.getRandomProduct();
                BigDecimal amount = BigDecimal.valueOf(product.reservedPrice.longValue() + new Random().nextInt(maximumIncrease));
                String result = bidService.AddBid(product, users[0],amount,2);
                System.out.println(users[0].name + " " + result );
            }
        };

        timer.schedule(user1TimerTask,1000);

        boolean working = true;
        while (working)
        {
            timer.purge();
        }





    }


}
