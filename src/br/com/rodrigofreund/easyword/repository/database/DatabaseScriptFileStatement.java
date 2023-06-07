package br.com.rodrigofreund.easyword.repository.database;

import br.com.rodrigofreund.easyword.repository.database.exception.InvalidSqlStatementException;

/**
 * It represent a SQL code
 * It purpose is detect if the SQL code is 
 * a DDL or DML code and its not intended to 
 * validate SQL syntax.
 * @author Rodrigo Freund de Moraes
 *
 */
public final class DatabaseScriptFileStatement {
    
    public static final String EMPTY_SQL_STATEMENT = "Empty SQL detected!";

    private final String statement;

    public DatabaseScriptFileStatement(String statement) throws InvalidSqlStatementException{
        if(statement == null || statement.isBlank())
            throw new InvalidSqlStatementException(EMPTY_SQL_STATEMENT);
        this.statement = statement.strip();
    }

    public String getStatementCode() {
        return this.statement;
    }

    public boolean isDdl() {
        return this.statement.toUpperCase().contains("CREATE") || this.statement.toUpperCase().contains("INSERT");
    }

    public boolean isDml() {
        return this.statement.toUpperCase().contains("SELECT");
    }

}
