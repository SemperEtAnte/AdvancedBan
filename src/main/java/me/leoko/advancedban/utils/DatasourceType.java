package me.leoko.advancedban.utils;

public final class DatasourceType
{
    public static final int MYSQL = 0;
    public static final int HSQL = 1;
    public static final int POSTGRES = 2;

    public static int getByName(String name)
    {
        switch (name.toLowerCase())
        {
            case "mysql":
                return MYSQL;
            case "postgres":
                return POSTGRES;
            default:
                return HSQL;
        }
    }
}
