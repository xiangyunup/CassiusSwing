package org.lxy.swing.dto;


import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.Serializable;

/**
 * @author Cassius
 * @date 2019/7/22 14:42
 */
@Data
public class GeneratorReq implements Serializable {

    private boolean isWeb = true;
    private boolean isStartsWith = false;
    private String projectPath;
    private String mapperXmlPath = File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+ "mapper" +File.separator;
    private String serverPath="";
    private String javaPath =File.separator+"src"+File.separator+"main"+File.separator+"java";
    private String author = "Cassius";
    private String packageName;
//    private String tableName;
    private String[] tableNames;
    private String dbUrl = "";
    private String driverName = "com.mysql.jdbc.Driver";
    private String schemaName = "";
    private String userName = "";
    private String password = "";

    public String getProjectPath() {
        if(StringUtils.isNotBlank(projectPath)){
            return projectPath;
        }
        return System.getProperty("user.dir");
    }

    public String getDbUrl() {
        if(StringUtils.isNotBlank(getSchemaName())){
            return dbUrl.replaceFirst("\\w{1,30}\\?",getSchemaName()+"?");
        }
        return dbUrl;
    }
}
