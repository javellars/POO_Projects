package Config;

/**
 *
 * @author v177950
 */
public class ConfigFactoryImpl {
    public static ConfigFactory createConfigFactory(boolean isDevelopment) {
        if (isDevelopment) {
            return new DevConfigFactory();
        } else {
            return new ProdConfigFactory();
        }
    }
}
