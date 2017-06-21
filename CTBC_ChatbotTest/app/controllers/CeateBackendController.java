package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import constantField.ConstantField;
import constantField.DatabaseColumnNameVariableTable;
import data.DialogueAnalysis;
import databaseUtil.DatabaseController;
import json.JSONObject;
import play.libs.Json;
import play.mvc.*;

import sqlCommandLogic.SqlCommandComposer;
import sqlCommandLogic.UserData;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * @author NCKU WMMKS LAB
 */
public class CeateBackendController extends Controller {

    @Inject
    SqlCommandComposer sqlCommandComposer;

    public Result HelloWorld() {
        return ok("HelloWorld");
    }

    public Result getUserData()
    {
        DatabaseController databaseController=new DatabaseController();
        JsonNode request = request().body().asJson();
        int id=Integer.parseInt(request.findPath(ConstantField.userAndArticleId).toString());
        JsonNode result = Json.newObject();
        ResultSet resultSet=databaseController.execSelect(sqlCommandComposer.getUserDataSqlById(id));
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            JSONObject resultJsonObject=new JSONObject();
            if(resultSet.next())
            {
                for(int i=1;i<=resultSetMetaData.getColumnCount();i++)
                {
                    Object columnValue=resultSet.getObject(i);
                    if(resultSetMetaData.getColumnTypeName(i).equals(ConstantField.databaseStringType))
                    {
                        resultJsonObject.put(resultSetMetaData.getColumnName(i),columnValue.toString());
                    }
                    else if(resultSetMetaData.getColumnTypeName(i).equals(ConstantField.databaseIntType))
                    {
                        resultJsonObject.put(resultSetMetaData.getColumnName(i),Integer.parseInt(columnValue.toString()));
                    }
                }
            }
            result=Json.parse(resultJsonObject.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ok(result);
    }
    public Result updateUserData()
    {
        DatabaseController databaseController=new DatabaseController();
        JsonNode request = request().body().asJson();
        JsonNode result = Json.newObject();
        JSONObject userDataJsonObject=new JSONObject(request.toString());
        UserData userData=sqlCommandComposer.getUserData(userDataJsonObject);
        int id=userDataJsonObject.getInt(DatabaseColumnNameVariableTable.id);
        String updateCondition="where "+DatabaseColumnNameVariableTable.id+"="+id;
        databaseController.execUpdate(DatabaseColumnNameVariableTable.usersInformationTableName,userData.getUserInformationSqlObject(),updateCondition);
        databaseController.execUpdate(DatabaseColumnNameVariableTable.articlesInformationTableName,userData.getArticleInformationSqlObject(),updateCondition);
        databaseController.execUpdate(DatabaseColumnNameVariableTable.classInformationTableName,userData.getClassInformationSqlObject(),updateCondition);
        databaseController.execUpdate(DatabaseColumnNameVariableTable.articlesContentTableName,userData.getArticleContentSqlObject(),updateCondition);
        databaseController.execUpdate(DatabaseColumnNameVariableTable.usersSpecialExperienceTableName,userData.getUserSpecialExperienceSqlObject(),updateCondition);
        result=Json.parse(new JSONObject().put("message","User id="+id+" data update finish").toString());
        return ok(result);
    }
}
