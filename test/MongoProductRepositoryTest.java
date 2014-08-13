import com.shopservice.ProductConditions;
import com.shopservice.dao.MongoProductRepository;
import com.shopservice.domain.Product;
import org.junit.Test;

/**
 * Created by user50 on 09.08.2014.
 */
public class MongoProductRepositoryTest {

    @Test
    public void testName() throws Exception {

        MongoProductRepository repo = new MongoProductRepository();

        ProductConditions conditions = new ProductConditions();
//        conditions.productIds.add("1784303933");
//        conditions.productIds.add("294166772");
//        conditions.productIds.add("1511267979");

//        conditions.categoryId = "994546208";

        conditions.words.add("alex");

        repo.find(conditions);

    }
}
