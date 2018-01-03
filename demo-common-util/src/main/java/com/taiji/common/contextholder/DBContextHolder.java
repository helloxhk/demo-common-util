package com.taiji.common.contextholder;

public class DBContextHolder {

	public static final String DEFAULT_DATASOURCE_MARK="baseDataSource";
	
	private static final ThreadLocal<String> contextHolder = new ThreadLocal<String>();
	 
    public static void setDBType(int dbType) {
        contextHolder.set(new Integer(dbType).toString());
    }
 
    public static void setDBType(String dbType) {
        contextHolder.set(dbType);
    }
 
    public static String getDBType() {
        return (String) contextHolder.get();
    }
 
    public static void clearDBType() {
        contextHolder.remove();
    }

	
}
