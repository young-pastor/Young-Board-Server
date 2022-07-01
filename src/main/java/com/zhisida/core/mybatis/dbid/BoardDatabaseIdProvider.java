
package com.zhisida.core.mybatis.dbid;

import com.zhisida.core.enums.DbIdEnum;
import org.apache.ibatis.mapping.DatabaseIdProvider;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库id选择器
 *
 * @author Young-Pastor
 */
public class BoardDatabaseIdProvider implements DatabaseIdProvider {

    @Override
    public void setProperties(Properties p) {
    }

    @Override
    public String getDatabaseId(DataSource dataSource) throws SQLException {
        String url = dataSource.getConnection().getMetaData().getURL();

        if (url.contains(DbIdEnum.ORACLE.getName())) {
            return DbIdEnum.ORACLE.getCode();
        } else if (url.contains(DbIdEnum.PG_SQL.getName())) {
            return DbIdEnum.PG_SQL.getCode();
        } else if (url.contains(DbIdEnum.MS_SQL.getName())) {
            return DbIdEnum.MS_SQL.getCode();
        } else if (url.contains(DbIdEnum.DM_SQL.getName())) {
            return DbIdEnum.DM_SQL.getCode();
        } else if (url.contains(DbIdEnum.KINGBASE_ES.getName())) {
            return DbIdEnum.KINGBASE_ES.getCode();
        } else {
            return DbIdEnum.MYSQL.getCode();
        }
    }
}
