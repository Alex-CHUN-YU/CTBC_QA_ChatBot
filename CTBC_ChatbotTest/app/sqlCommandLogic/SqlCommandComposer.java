package sqlCommandLogic;

import constantField.DatabaseColumnNameVariableTable;
import databaseUtil.SqlObject;
import json.JSONObject;

import java.util.Arrays;
import java.util.Set;

/**
 * Created by roye on 2017/4/26.
 */
public class SqlCommandComposer {
    public String getUserDataSqlById(int id)
    {
        String sql="select * from articles_content as a,users_information as b,articles_information as c,class_information as d,users_special_experience as e where a.id=b.id and b.id=c.id and c.id=d.id and d.id=e.id and a.id="+id;
        return sql;
    }
    public UserData getUserData(JSONObject userDataJsonObject)
    {
        String sql="";
        UserData userData=new UserData();
        Set<String> columnNameSet= userDataJsonObject.keySet();
        SqlObject userInformationSqlObject=new SqlObject();
        SqlObject articleInformationSqlObject=new SqlObject();
        SqlObject classInformationSqlObject=new SqlObject();
        SqlObject userSpecialExperienceSqlObject=new SqlObject();
        SqlObject articleContentSqlObject=new SqlObject();
        for(String columnName:columnNameSet)
        {
            if(checkTableNumber(columnName)==DatabaseColumnNameVariableTable.genericColumnNameNumber)
            {
                userInformationSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
                articleInformationSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
                classInformationSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
                userSpecialExperienceSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
                articleContentSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
            }
            else if(checkTableNumber(columnName)==DatabaseColumnNameVariableTable.usersInformationTableNumber)
            {
                userInformationSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
            }
            else if(checkTableNumber(columnName)==DatabaseColumnNameVariableTable.classInformationTableNumber)
            {
                classInformationSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
            }
            else if(checkTableNumber(columnName)==DatabaseColumnNameVariableTable.articlesInformationTableNumber)
            {
                articleInformationSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
            }
            else if(checkTableNumber(columnName)==DatabaseColumnNameVariableTable.usersSpecialExperienceTableNumber)
            {
                userSpecialExperienceSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
            }
            else if(checkTableNumber(columnName)==DatabaseColumnNameVariableTable.articlesContentTableNumber)
            {
                articleContentSqlObject.addSqlObject(columnName,userDataJsonObject.get(columnName));
            }
        }
        userData.setUserInformationSqlObject(userInformationSqlObject);
        userData.setClassInformationSqlObject(classInformationSqlObject);
        userData.setUserSpecialExperienceSqlObject(userSpecialExperienceSqlObject);
        userData.setArticleInformationSqlObject(articleInformationSqlObject);
        userData.setArticleContentSqlObject(articleContentSqlObject);
        return userData;
    }
    private int checkTableNumber(String columnName)
    {
        int tableNumber=-1;
        if(Arrays.asList(DatabaseColumnNameVariableTable.genericColumnNameList).contains(columnName))
        {
            tableNumber=DatabaseColumnNameVariableTable.genericColumnNameNumber;
        }
        else if(Arrays.asList(DatabaseColumnNameVariableTable.userInformationColumnNameList).contains(columnName))
        {
            tableNumber=DatabaseColumnNameVariableTable.usersInformationTableNumber;
        }
        else if(Arrays.asList(DatabaseColumnNameVariableTable.articlesInformationColumnNameList).contains(columnName))
        {
            tableNumber=DatabaseColumnNameVariableTable.articlesInformationTableNumber;
        }
        else if(Arrays.asList(DatabaseColumnNameVariableTable.classInformationColumnNameList).contains(columnName))
        {
            tableNumber=DatabaseColumnNameVariableTable.classInformationTableNumber;
        }
        else if(Arrays.asList(DatabaseColumnNameVariableTable.userSpecialInformationColumnNameList).contains(columnName))
        {
            tableNumber=DatabaseColumnNameVariableTable.usersSpecialExperienceTableNumber;
        }
        else if(Arrays.asList(DatabaseColumnNameVariableTable.articleContentColumnNameList).contains(columnName))
        {
            tableNumber=DatabaseColumnNameVariableTable.articlesContentTableNumber;
        }
        return tableNumber;
    }
}
