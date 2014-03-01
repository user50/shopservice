package conrtollers;

import org.junit.Test;
import play.mvc.Result;
import play.test.Helpers;

import static org.fest.assertions.Assertions.assertThat;
import static play.test.Helpers.fakeRequest;
import static play.test.Helpers.routeAndCall;

public class PriceListControllerTest {

    @Test
    public void badRoute() {
        Result result = routeAndCall(fakeRequest(Helpers.GET, "/client/client1/groups/1/pricelists/YML"));
        assertThat(result).isNull();
    }
}
