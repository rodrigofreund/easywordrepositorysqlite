package br.com.rodrigofreund.easyword.repository.database.exception;

import br.com.rodrigofreund.easyword.model.exception.BusinessException;

public class InvalidSqlStatementException extends BusinessException {

    private static final long serialVersionUID = 1L;
    
    public InvalidSqlStatementException(String message) {
        super(message);
    }

}
