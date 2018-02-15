package HelloWorld;

import org.apache.commons.lang3.StringUtils;

import java.util.Properties;

public class Main {

    public static void main(String [] args)
    {
        Properties prop = new Properties();
        try {
            prop.load(Main.class.getClassLoader().getResourceAsStream("config.properties"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Hello World, I'm "+ StringUtils.upperCase(prop.getProperty("devops.config")) + ".");
    }
}
