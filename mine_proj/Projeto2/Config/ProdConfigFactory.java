package Config;

/**
 *
 * @author v177950
 */
public class ProdConfigFactory implements ConfigFactory {
    @Override
    public Config getConfig() {
        return new ProdConfig();
    }
}
