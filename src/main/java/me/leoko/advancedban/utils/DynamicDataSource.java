package me.leoko.advancedban.utils;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.leoko.advancedban.MethodInterface;
import me.leoko.advancedban.Universal;

public class DynamicDataSource
{
    private HikariConfig config = new HikariConfig();

    public DynamicDataSource(int type) throws ClassNotFoundException
    {
        MethodInterface mi = Universal.get().getMethods();
        switch (type)
        {
            case DatasourceType.MYSQL:
                String ip = mi.getString(mi.getMySQLFile(), "SQL.IP", "Unknown");
                String dbName = mi.getString(mi.getMySQLFile(), "SQL.DB-Name", "Unknown");
                String usrName = mi.getString(mi.getMySQLFile(), "SQL.Username", "Unknown");
                String password = mi.getString(mi.getMySQLFile(), "SQL.Password", "Unknown");
                String properties = mi.getString(mi.getMySQLFile(), "SQL.Properties", "verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf8");
                int port = mi.getInteger(mi.getMySQLFile(), "SQL.Port", 3306);
                Class.forName("com.mysql.jdbc.Driver");
                config.setJdbcUrl("jdbc:mysql://" + ip + ":" + port + "/" + dbName + "?" + properties);
                config.setUsername(usrName);
                config.setPassword(password);
                break;
            case DatasourceType.POSTGRES:
                ip = mi.getString(mi.getMySQLFile(), "SQL.IP", "Unknown");
                dbName = mi.getString(mi.getMySQLFile(), "SQL.DB-Name", "Unknown");
                usrName = mi.getString(mi.getMySQLFile(), "SQL.Username", "Unknown");
                password = mi.getString(mi.getMySQLFile(), "SQL.Password", "Unknown");
                properties = mi.getString(mi.getMySQLFile(), "SQL.Properties", "verifyServerCertificate=false&useSSL=false&useUnicode=true&characterEncoding=utf8");
                port = mi.getInteger(mi.getMySQLFile(), "SQL.Port", 5432);
                Class.forName("org.postgresql.Driver");
                config.setJdbcUrl("jdbc:postgres://" + ip + ":" + port + "/" + dbName + "?" + properties);
                config.setUsername(usrName);
                config.setPassword(password);
                break;
            default:
                String driverClassName = "org.hsqldb.jdbc.JDBCDriver";
                Class.forName(driverClassName);
                config.setDriverClassName(driverClassName);
                config.setJdbcUrl("jdbc:hsqldb:file:" + mi.getDataFolder().getPath() + "/data/storage;hsqldb.lock_file=false");
                config.setUsername("SA");
                config.setPassword("");
                break;
        }
    }

    public HikariDataSource generateDataSource()
    {
        return new HikariDataSource(config);
    }
}
