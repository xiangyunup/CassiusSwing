package org.lxy.swing.util;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.GeneratorBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.lxy.swing.dto.GeneratorReq;

import java.io.File;

/**
 * 代码生成工具类
 * @author Cassius
 * @date 2019/7/22 12:06
 */
public class GeneratorUtil {

    private static String randomKey = AES.generateRandomKey();

    public static void main(String[] args) throws Exception {
        GeneratorReq generatorReq = new GeneratorReq();
        // 公共model存放目录
        generatorReq.setServerPath(File.separator);
        if(args.length > 0){
            if(args.length < 4){
                throw new RuntimeException("缺少配置参数");
            }
            SwingUtil.checkNull(args[0],"Url不能为空");
            SwingUtil.checkNull(args[1],"账户不能为空");
            SwingUtil.checkNull(args[2],"账户不能为空");
            generatorReq.setDbUrl(args[0]);
            generatorReq.setUserName(args[1]);
            generatorReq.setPassword(args[2]);
            // 业务名称： swing
            String bizName = "swing";
            if(args.length == 5){
                bizName = args[3];
                SwingUtil.checkNull(args[4],"表名不能为空");
                generatorReq.setTableNames(args[4].split(","));
            } else {
                SwingUtil.checkNull(args[3],"表名不能为空");
                generatorReq.setTableNames(args[3].split(","));
            }
            // 包路径
            String packageName = "org.lxy." + bizName + ".server";
            generatorReq.setPackageName(packageName);
        } else {
            generatorReq.setDbUrl(PropertiesUtil.getValue("spring.datasource.url"));
            generatorReq.setUserName(PropertiesUtil.getValue("spring.datasource.username"));
            generatorReq.setPassword(PropertiesUtil.getValue("spring.datasource.password"));
            // 业务名称： swing
            String bizName = "swing";
            // 包路径
            String packageName = "org.lxy." + bizName + ".server";
            generatorReq.setPackageName(packageName);
            // 表名
            String[] tables = {"nn_user"};
            generatorReq.setTableNames(tables);
        }
        System.out.println(JSON.toJSONString(args));
        System.out.println(JSON.toJSONString(generatorReq));
        generateByTables(generatorReq);
//        generateByParam("");
    }

    public static String generateByParam(String param) {
        System.out.println("param:" + param);
        // 生成 16 位随机 AES 密钥
        System.out.println("randomKey:" + randomKey);
        // 随机密钥加密
        String result = AES.encrypt(param, randomKey);
        System.out.println("result:" + result);
        return result;
    }
    /**
     * mysql
     *
     */
    public static void generateByTables(GeneratorReq generatorReq) {
        // 指定输出目录
        String outputDir = generatorReq.getProjectPath() + generatorReq.getServerPath() + generatorReq.getJavaPath();
        GlobalConfig config = GeneratorBuilder.globalConfigBuilder()
                // 覆盖已生成文件
                .fileOverride()
                // 指定输出目录 默认值: windows:D:// linux or mac : /tmp
                .outputDir(outputDir)
                // 生成swagger注解
//                .enableSwagger()
                // 作者名
                .author(generatorReq.getAuthor())
                // 时间策略 默认使用Jdk8时间
                .dateType(DateType.TIME_PACK)
                // 注释日期格式
                .commentDate("yyyy-MM-dd")
                .build();
        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig.Builder(
                        generatorReq.getDbUrl(),
                        generatorReq.getUserName(),
                        generatorReq.getPassword())
                // 关键字处理 ,这里选取了mysql5.7文档中的关键字和保留字（含移除）
                .keyWordsHandler(new MySqlKeyWordsHandler())
                // 数据库 schema name
                .schema(generatorReq.getSchemaName()).build();
        StrategyConfig strategyConfig = GeneratorBuilder.strategyConfigBuilder()
                .enableCapitalMode()
                .addInclude(generatorReq.getTableNames())//修改替换成你需要的表名，多个表名传数组
                .build();
        strategyConfig.mapperBuilder()
                .enableBaseResultMap()
                .enableBaseColumnList()
                .build();
        strategyConfig.entityBuilder()
                .enableLombok()
                .enableChainModel()
                .logicDeleteColumnName("is_valid")
                .logicDeletePropertyName("isValid")
                .addTableFills(
                        new Column("is_valid", FieldFill.INSERT),
                        new Column("create_time", FieldFill.INSERT),
                        new Column("update_time", FieldFill.INSERT_UPDATE)
                )
                .columnNaming(NamingStrategy.underline_to_camel)
                .naming(NamingStrategy.underline_to_camel)
                .build();
        strategyConfig.controllerBuilder()
                .enableRestStyle()
                .build();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        TemplateConfig templateConfig = new TemplateConfig.Builder()
                // 不生成controller
//                .controller("/templates/controller.java")
                .controller("")
                .mapperXml("")
                .build();

        PackageConfig packageConfig = new PackageConfig.Builder()
                .parent(generatorReq.getPackageName())
                .entity("model")
                .build();

        new AutoGenerator(dataSourceConfig)
                .global(config)
                .template(templateConfig)
                .strategy(strategyConfig)
                .packageInfo(packageConfig)
                .execute(new FreemarkerTemplateEngine());
    }

    private static String joinPath(String parentDir, String packageName) {
        if (StringUtils.isBlank(parentDir)) {
            parentDir = System.getProperty(ConstVal.JAVA_TMPDIR);
        }
        if (!StringUtils.endsWith(parentDir, File.separator)) {
            parentDir += File.separator;
        }
        packageName = packageName.replaceAll("\\.", StringPool.BACK_SLASH + File.separator);
        return parentDir + packageName;
    }
}
