package br.com.rodrigofreund.easyword.repository.database;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Queue;

import br.com.rodrigofreund.easyword.model.exception.BusinessException;
import br.com.rodrigofreund.easyword.repository.database.exception.InvalidSqlStatementException;

public final class DatabaseScriptFile implements Iterable<DatabaseScriptFileStatement> {

    private String completeSqlCode;
    private Queue<DatabaseScriptFileStatement> statements;

    public DatabaseScriptFile(Path path) {

        statements = new ArrayDeque<>();

        if (Files.exists(path)) {
            try {
                completeSqlCode = Files.readString(path);
                generateSqlStatements();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (completeSqlCode.isBlank()) {
            throw new BusinessException("Error reading sql file");
        }
    }

    private void generateSqlStatements() {
        for (String sqlcode : this.completeSqlCode.split("/")) {
            try {
                statements.add(new DatabaseScriptFileStatement(sqlcode));
            } catch(InvalidSqlStatementException ex) {
                continue;
            }
        }
    }

    @Override
    public Iterator<DatabaseScriptFileStatement> iterator() {
        return statements.iterator();
    }
}
