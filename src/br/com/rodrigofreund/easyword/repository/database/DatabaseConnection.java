package br.com.rodrigofreund.easyword.repository.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.rodrigofreund.easyword.model.exception.ApplicationException;
import br.com.rodrigofreund.easyword.model.exception.BusinessException;

/** It represent a database access point to application
 * 
 * @author Rodrigo Freund de Moraes
 *
 */
public final class DatabaseConnection {

    public static final String DATABASE_FILE = "easyworddb.db";

    public final static String SCRIPT_FOLDER = "resources/";

    final static String DATABASE_URL = "jdbc:sqlite:".concat(DATABASE_FILE);

    private static DatabaseConnection instance;

    private Connection connection;

    public static DatabaseConnection getInstance() throws ApplicationException {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public void execute(DatabaseScriptFileStatement statement) {
        if (statement.isDdl()) {
            executeDdl(statement);
        } else {
            executeDml(statement);
        }
    }

    public boolean isConnected() {
        try {
            return !this.connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    private RawDataOutput executeDml(DatabaseScriptFileStatement statement) {
        try (PreparedStatement pStatement = this.connection.prepareStatement(statement.getStatementCode())) {
            ResultSet resultSet = pStatement.executeQuery();
            return RawDataOutput.of(resultSet);
        } catch (SQLException e) {
            throw new BusinessException("Execute query error");
        }
    }

    private void executeDdl(DatabaseScriptFileStatement statement) {
        try (PreparedStatement pStatement = this.connection.prepareStatement(statement.getStatementCode())) {
            pStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Remove actual database file
     * 
     * @throws ApplicationException
     */
    static void resetDatabase() throws ApplicationException {

        closeDatabaseConnection();
        deleteDatabaseFile();

    }

    private static void closeDatabaseConnection() throws ApplicationException {
        if (instance != null && instance.isConnected()) {
            try {
                instance.disconnect();
                instance = null;
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                throw new ApplicationException("Database reset error");
            }
        }
    }

    private static void deleteDatabaseFile() throws ApplicationException {

        Path dbFile = Paths.get(DATABASE_FILE);

        if (Files.exists(dbFile) && Files.isRegularFile(dbFile)) {
            try {
                Files.delete(dbFile);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                throw new ApplicationException("Database reset error");
            }
        } else {
            System.out.println("Database file does not exists.");
        }
    }

    private void disconnect() throws SQLException {
        this.connection.close();
    }

    private DatabaseConnection() throws ApplicationException {
        try {
            this.connection = DriverManager.getConnection(DATABASE_URL);
        } catch (SQLException e) {
            throw new ApplicationException("Error connecting database!");
        }
    }
}
