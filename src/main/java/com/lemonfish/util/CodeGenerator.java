package com.lemonfish.util;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Chao Zhang
 * @version V1.0
 * @Package com.lemonfish.util
 * @date 2020/4/30 11:35
 */
public class CodeGenerator {


    /**
     * 读取控制台内容
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        /**
         * 代码生成器
         */
        AutoGenerator mpg = new AutoGenerator();
        globalConfig(mpg);
        dataSourceConfig(mpg);
        packageConfig(mpg);
        strategyConfig(mpg);
        CustomConfig(mpg);
        mpg.execute();
    }
    /**
     * @Description 自定义配置
     * @param mpg mpg
     * @return void
     * @author Lemonfish
     * @date 2020/4/30 14:19
     */
    private static void CustomConfig(AutoGenerator mpg) {
        /**
         * 自定义配置
         */
        InjectionConfig in = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<>();
                //自定义配置，在模版中cfg.superColums 获取
                // TODO 这里解决子类会生成父类属性的问题，在模版里会用到该配置
                map.put("superColums", this.getConfig().getStrategyConfig().getSuperEntityColumns());
                this.setMap(map);
            }
        };
        mpg.setCfg(in);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        //自定义模版
        templateConfig.setMapper("templates/mapper2.java");
        templateConfig.setServiceImpl("templates/serviceImpl2.java");
        templateConfig.setController("/templates/controller2.java");
        templateConfig.setService("/templates/service2.java");
//        templateConfig.setEntity("/templates/btl/entity.java");
        mpg.setTemplate(templateConfig);
    }

    private static void strategyConfig(AutoGenerator mpg) {
        /**
         * 4. 策略配置globalConfiguration中
         */
        // 自动填充配置相关信息
        TableFill createdTime = new TableFill("created_time", FieldFill.INSERT);
        TableFill updatedTime = new TableFill("updated_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills = new ArrayList<>();
        tableFills.add(createdTime);
        tableFills.add(updatedTime);

        StrategyConfig stConfig = new StrategyConfig();
        //全局大写命名
        stConfig.setCapitalMode(true)
                // 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)
                // 字段映射到实体的命名策略
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // 生成的表, 支持多表一起生成，以数组形式填写
                .setInclude(scanner("表名,多个英文逗号分割").split(","))
                // 实体是否为lombok模型（默认 false）
                .setEntityLombokModel(true)
                // 生成 @RestController 控制器
                .setRestControllerStyle(true)
                // 设置自定义继承的Entity类全称，带包名
                .setSuperEntityClass("com.lemonfish.entity.BaseEntity")
                // 驼峰转下划线
                .setControllerMappingHyphenStyle(true)
                // 逻辑删除字段
                .setLogicDeleteFieldName("is_deleted")
                // 自动填充配置
                .setTableFillList(tableFills)
                //设置自定义基础的Entity类，公共字段
                .setSuperEntityColumns("id", "created_time", "updated_time","is_deleted");
        mpg.setStrategy(stConfig);
    }

    private static void packageConfig(AutoGenerator mpg) {
        /**
         * 3. 包配置
         */
        PackageConfig pc = new PackageConfig();
        //父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc
                .setParent("com.lemonfish")
                .setController("controller")
                .setEntity("entity")
                .setService("service")
//                .setServiceImpl("service.Impl")
                .setMapper("mapper");
        mpg.setPackageInfo(pc);
    }

    private static void dataSourceConfig(AutoGenerator mpg) {
        /**
         * 2. 数据源配置
         */
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig
                .setUsername("root")
                .setPassword("123456")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://localhost:3306/lemonc?useSSL=false&useUnicode=true&characterEncoding=utf8");
        mpg.setDataSource(dataSourceConfig);
    }

    private static void globalConfig(AutoGenerator mpg) {
        /**
         * 1. 全局配置
         */
        GlobalConfig globalConfig = new GlobalConfig();
        //生成文件的输出目录
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig
                // 设置作者
                .setAuthor("LemonFish")
                // 设置日期格式
                .setDateType(DateType.ONLY_DATE)
                //是否覆盖文件
                .setFileOverride(true)
                //生成后打开文件
                .setOpen(false)
                // 主键策略
                .setIdType(IdType.AUTO)
                // 设置swagger2
                .setSwagger2(true)
                // 设置生成的service接口的名字的首字母是否为I，默认Service是以I开头的
                .setServiceName("%sService");
        //生成基本的resultMap
//                .setBaseResultMap(true)
        //生成基本的SQL片段
//                .setBaseColumnList(true);
        mpg.setGlobalConfig(globalConfig);
    }


}
