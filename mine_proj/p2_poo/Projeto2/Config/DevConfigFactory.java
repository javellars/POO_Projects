package Config;

/**
 *
 * @author v177950
 */
public class DevConfigFactory implements ConfigFactory {
    @Override
    public Config getConfig() {
        return new DevConfig();
    }
}
