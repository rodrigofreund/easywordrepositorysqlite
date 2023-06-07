package br.com.rodrigofreund.easyword.repository.database;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import br.com.rodrigofreund.easyword.repository.database.exception.InvalidSqlStatementException;

public class DatabaseScriptFileStatementTest {

    
    @Test
    void instanceCreateDdlSqlStatement() {
        String sql = "CREATE TABLE PESSOA(int code);/";
        
        DatabaseScriptFileStatement dbStm = new DatabaseScriptFileStatement(sql);
        
        assertTrue(dbStm.isDdl());
    }
    
    @Test
    void instanceCreateDmlSqlStatement() {
        String sql = "SELECT * FROM PESSOA;/";
        
        DatabaseScriptFileStatement dbStm = new DatabaseScriptFileStatement(sql);
        
        assertTrue(dbStm.isDml());
    }
    
    @Test
    void instanceErrorCreatingEmptyStatement() {
        String sql = "            \n   \t  ";
        try {
            new DatabaseScriptFileStatement(sql);
        } catch(InvalidSqlStatementException ex) {
            assertTrue(ex.getMessage().equals(DatabaseScriptFileStatement.EMPTY_SQL_STATEMENT));
        }
    }
}
