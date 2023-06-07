package easywordrepository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.rodrigofreund.easyword.model.exception.ApplicationException;
import br.com.rodrigofreund.easyword.repository.database.DatabaseConnection;

public class DatabaseConnectionTest {

    @Test
    void testConnection() {

        try {
            assertTrue(DatabaseConnection.getInstance().isConnected());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }

    }
    
}
