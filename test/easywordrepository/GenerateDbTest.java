package easywordrepository;

import org.junit.jupiter.api.Test;

import br.com.rodrigofreund.easyword.model.exception.ApplicationException;
import br.com.rodrigofreund.easyword.repository.database.InitialDatabase;

public class GenerateDbTest {

    @Test
    void generateDbTest() {
        
        InitialDatabase initialDatabase;
        try {
            initialDatabase = InitialDatabase.getInstance();
            initialDatabase.generateDatabase();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        
    }

}
