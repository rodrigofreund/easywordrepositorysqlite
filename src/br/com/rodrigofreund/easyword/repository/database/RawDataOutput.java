package br.com.rodrigofreund.easyword.repository.database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.IntStream;

public final class RawDataOutput {
    
    private final Map<Long, List<RawDataOutputRow>> rows;
    
    private RawDataOutput() {
        rows = new TreeMap<>();
    }
    
    public boolean isEmpty() {
        return rows.isEmpty();
    }

    public static RawDataOutput of(ResultSet resultSet) throws SQLException {
        
        RawDataOutput instance = new RawDataOutput();
        
        long line = 0;
        
        while(resultSet.next()) {
            ++line;
            for(int column : IntStream.range(1, 100).toArray()) {
                try {
                    Object value = resultSet.getObject(column);
                    instance.addRow(line, column, value);
                } catch (SQLException e) {
                    System.out.println("Final column reach.");
                    break;
                }
            }
        }
        
        System.out.println(instance.toString());
        
        return instance;
    }
    
    private void addRow(Long row, int column, Object value) {
        rows.merge(row, List.of(new RawDataOutputRow(column, value)), (values, elem) -> {
            List<RawDataOutputRow> nList = new ArrayList<>();
            nList.addAll(elem);
            nList.addAll(values);
            return nList;
        });
    }
    
    class RawDataOutputRow {
        int column;
        Object value;
        RawDataOutputRow(int column, Object object){
            this.column = column;
            this.value = object;
        }
        
        @Override
        public String toString() {
            return "Field:".concat(column+"").concat(" value:").concat(value.toString());
        }
    }

    public static RawDataOutput empty() {
        return new RawDataOutput();
    }
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(Long i : rows.keySet()) {
            sb.append("\n");
            sb.append(i);
            sb.append(" - ");
            sb.append(rows.get(i).stream().map(RawDataOutputRow::toString).reduce(String::concat));
        }
        return sb.toString();
    }
}
