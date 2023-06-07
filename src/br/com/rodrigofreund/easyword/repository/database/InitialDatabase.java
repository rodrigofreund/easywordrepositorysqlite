package br.com.rodrigofreund.easyword.repository.database;

import static br.com.rodrigofreund.easyword.repository.database.DatabaseConnection.SCRIPT_FOLDER;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.com.rodrigofreund.easyword.model.exception.ApplicationException;

/**
 * 
 * @author rodri
 * Responsible for the creation of initial database structure
 * If it was executed with a existent database it will be erased and all data will be lost
 */
public final class InitialDatabase {
    
    private DatabaseConnection databaseConnection;
    
    private static InitialDatabase instance;
    
    private InitialDatabase() throws ApplicationException {
        this.databaseConnection = DatabaseConnection.getInstance();
    }

    public static InitialDatabase getInstance() throws ApplicationException {
        if(instance == null) {
            instance = new InitialDatabase();
        }
        return instance;
    }
    
    public void generateDatabase() throws ApplicationException {

        Path path = Paths.get(SCRIPT_FOLDER);

        DatabaseConnection.resetDatabase();

        this.databaseConnection = DatabaseConnection.getInstance();

        try {
            Files.walk(path).forEach(this::execute);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void execute(Path sqlFile) { 
        if(Files.exists(sqlFile) && Files.isRegularFile(sqlFile)) {
            DatabaseScriptFile scriptFile = new DatabaseScriptFile(sqlFile);
            executeScriptFile(scriptFile);
        }
    }
    
    private void executeScriptFile(DatabaseScriptFile scriptFile) {
        scriptFile.forEach(databaseConnection::execute);
    }
}
