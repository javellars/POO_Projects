package Config;

import System.Controller;
import System.DataBaseType;

/**
 *
 * @author v177950
 */
public class ProdConfig implements Config {
    @Override
    public Controller loadConfig() {
        return new Controller(DataBaseType.MARIADB);
    }
}
