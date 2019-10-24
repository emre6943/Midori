package selenium;

import org.junit.Test;

public class SeleniumTest {

    @Test
    public void testpublicTrans() {
        assert(VegetarianMeal.publicTransEmission(1,0) == 0);
    }

    @Test
    public void testpublicTrans2() {
        assert(VegetarianMeal.publicTransEmission(2,0) == 0);
    }

}
