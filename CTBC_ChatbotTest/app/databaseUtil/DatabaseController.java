package databaseUtil;


import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * Created by roye on 2017/4/25.
 */
public class DatabaseController {


    private String dbHost;
    private String dbName;
    private String userName;
    private String password;
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;

    public DatabaseController()
    {
        initLoadJdbc();
        initConnection();
    }
    private void initConnection()
    {
        try {
            connection = DriverManager.getConnection(dbHost+dbName+"?useUnicode=true&characterEncoding=utf8",userName,password);
            System.out.println(dbHost+dbName+"?useUnicode=true&characterEncoding=utf8"+userName+password);
            //statement=connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    private void initLoadJdbc()
    {
        Properties properties=new Properties();
        try {
            properties.load(new FileInputStream("conf/databaseConfiguration.properties"));
            dbHost=properties.getProperty("dbHost");
            dbName=properties.getProperty("dbName");
            userName=properties.getProperty("userName");
            password=properties.getProperty("password");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void execInsert(String tableName, SqlObject obj)
    {
        String sql=" insert into "+tableName+" ("+obj.getColumnNameString()+")"
                + " values ("+obj.getColumnValueString()+");";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            System.out.println("The data has been loaded into db "+tableName+" table.");
        }

    }
    public void execUpdate(String tableName, SqlObject obj,String condition)
    {
        String sql="update "+tableName+" set "+obj.getColumnNameValuePairString()+" "+condition+";";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.execute();
        } catch (SQLException e) {
            System.out.println("The data has been loaded into db "+tableName+" table.");
        }

    }
    public ResultSet execSelect(String sql)
    {
        ResultSet resultSet=null;
       try {
           Statement statement = connection.createStatement();
           resultSet= statement.executeQuery(sql);


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


}
