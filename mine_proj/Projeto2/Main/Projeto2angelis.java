package Main;
import Config.Config;
import Config.ConfigFactory;
import Config.ConfigFactoryImpl;
import RDBMS.MariaDBConnection;

/**
 *
 * @author v177950
 */
public class Projeto2angelis {

    public static void main(String[] args) {
        /*MariaDBConnection mb = new MariaDBConnection(); //teste da conexão funcionando
        mb.close();*/ //fechando a conexão
        // Decida se está em modo de desenvolvimento ou produção, produção usa o db msm, e desenvolvimento usa a memoria.
        boolean isDevelopment = true;
        
        ConfigFactory configFactory = ConfigFactoryImpl.createConfigFactory(isDevelopment);
        Config config = configFactory.getConfig();
        
        try
         {
         (config.loadConfig()).start(); // A config escolhida retorna um controller configurado pra usar o db ou a memoria
         }
      catch (Exception myException)
         {
         System.out.println("Exception launched. Program aborted.");
         System.out.println(myException.getMessage());
         System.exit(1);
         }
        
        //ta tudo baseado no exemplo de controller que ta no package System, que o angelis disponibilizou
    }
}
