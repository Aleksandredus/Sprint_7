package courier;

import org.apache.commons.lang3.RandomStringUtils;

public class CourierMaker {
    public static Courier generic() {
        return new Courier("ninja", "1234", "saske");

    }

    public static Courier random() {
        return new Courier(RandomStringUtils.randomAlphabetic(10), "1234", "syske");
    }
}
