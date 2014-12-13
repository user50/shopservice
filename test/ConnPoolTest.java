import com.shopservice.HibernateUtil;
import com.shopservice.domain.ClientSettings;
import org.hibernate.Session;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class ConnPoolTest {

    @Test
    public void test() throws Exception {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 0; i < 1000; i++) {
            executorService.execute(new DatabaseOperation());
        }

        Thread.sleep(100000);

    }

    public static class DatabaseOperation implements Runnable
    {

        @Override
        public void run() {
            List<ClientSettings> settings = HibernateUtil.execute(new HibernateUtil.Query() {
                @Override
                public List<ClientSettings> execute(Session session) {
                    return session.createCriteria(ClientSettings.class).list();
                }
            });

            System.out.println(settings.size());
        }
    }
}
