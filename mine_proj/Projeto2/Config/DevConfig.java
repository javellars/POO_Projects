package Config;

import System.Controller;
import System.DataBaseType;

/**
 *
 * @author v177950
 */
public class DevConfig implements Config{
    @Override
    public Controller loadConfig() {
        return new Controller(DataBaseType.MEMORY);
    }
}
