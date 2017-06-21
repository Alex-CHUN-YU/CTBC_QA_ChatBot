package databaseUtil;

import java.util.ArrayList;

/**
 * Created by roye on 2017/4/26.
 */
public class SqlObject {
    private int size;
    private ArrayList<String> columnName;
    private ArrayList<Object> columnValue;
    public int size()
    {
        return size;
    }
    public SqlObject()
    {
        columnName=new ArrayList<>();
        columnValue=new ArrayList<>();
        size=0;
    }
    public void addSqlObject(String column,Object value)
    {
        if(columnName.contains(column))
        {
            columnValue.add(columnName.indexOf(column),value);
            columnValue.remove(columnName.indexOf(column)+1);
        }
        else
        {
            columnName.add(column);
            columnValue.add(value);
            size=columnName.size();
        }

    }
    public int getSize()
    {
        return size;
    }
    public String getColumnNameIndexOf(int i)
    {
        return columnName.get(i);
    }
    public Object getColumnValueIndexOf(int i)
    {
        if(columnName.size()>=i)
        {
            return columnValue.get(i);
        }

            return null;
    }
    public int getColumnNameIndex(String column)
    {
        if(columnName.contains(column))
        {
            return columnName.lastIndexOf(column);
        }
        else
        {
             return -1;
        }
    }
    public String getColumnNameString()
    {
        String columnString="";
        if(size!=0)
        {
            columnString+=columnName.get(0);
            for(int i=1;i<size;i++)
            {
                columnString+=",";
                columnString+=columnName.get(i);
            }
        }
        return columnString;
    }
    public String getColumnValueString()
    {
        String columnPreparedStatementString="";
        if(size!=0)
        {
            columnPreparedStatementString+=toSqlValue(columnValue.get(0));
            for(int i=1;i<size;i++)
            {
                columnPreparedStatementString+=",";
                columnPreparedStatementString+=toSqlValue(columnValue.get(i));
            }
        }
        return  columnPreparedStatementString;
    }
    public String getColumnNameValuePairString()
    {
        String columnPreparedStatementString="";
        if(size!=0)
        {
            columnPreparedStatementString+=columnName.get(0)+"="+toSqlValue(columnValue.get(0));
            for(int i=1;i<size;i++)
            {
                columnPreparedStatementString+=",";
                columnPreparedStatementString+=columnName.get(i)+"="+toSqlValue(columnValue.get(i));
            }
        }
        return  columnPreparedStatementString;
    }
    private String toSqlValue(Object obj)
    {
        if(obj.getClass()==Integer.class)
        {
           return obj.toString();
        }
        else if(obj.getClass()==String.class)
        {
            return "'"+obj.toString()+"'";
        }
        else return null;
    }
}
