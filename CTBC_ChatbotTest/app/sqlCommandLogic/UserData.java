package sqlCommandLogic;

import databaseUtil.SqlObject;

/**
 * Created by roye on 2017/4/27.
 */
public class UserData {
    private SqlObject userInformationSqlObject;
    private SqlObject articleInformationSqlObject;
    private SqlObject classInformationSqlObject;
    private SqlObject userSpecialExperienceSqlObject;

    public SqlObject getUserInformationSqlObject() {
        return userInformationSqlObject;
    }

    public void setUserInformationSqlObject(SqlObject userInformationSqlObject) {
        this.userInformationSqlObject = userInformationSqlObject;
    }

    public SqlObject getArticleInformationSqlObject() {
        return articleInformationSqlObject;
    }

    public void setArticleInformationSqlObject(SqlObject articleInformationSqlObject) {
        this.articleInformationSqlObject = articleInformationSqlObject;
    }

    public SqlObject getClassInformationSqlObject() {
        return classInformationSqlObject;
    }

    public void setClassInformationSqlObject(SqlObject classInformationSqlObject) {
        this.classInformationSqlObject = classInformationSqlObject;
    }

    public SqlObject getUserSpecialExperienceSqlObject() {
        return userSpecialExperienceSqlObject;
    }

    public void setUserSpecialExperienceSqlObject(SqlObject userSpecialExperienceSqlObject) {
        this.userSpecialExperienceSqlObject = userSpecialExperienceSqlObject;
    }

    public SqlObject getArticleContentSqlObject() {
        return articleContentSqlObject;
    }

    public void setArticleContentSqlObject(SqlObject articleContentSqlObject) {
        this.articleContentSqlObject = articleContentSqlObject;
    }

    private SqlObject articleContentSqlObject;
}
