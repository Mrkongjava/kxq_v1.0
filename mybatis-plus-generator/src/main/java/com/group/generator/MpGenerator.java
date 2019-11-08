package com.group.generator;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

public class MpGenerator {


    /**
     * 代码生成    示例代码
     */
    @Test
    public void testGenerator() {
        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(true) // 是否支持AR模式
                .setAuthor("kxq") // 作者
                .setOutputDir("/daima") // 生成路径
                .setFileOverride(true)  // 文件覆盖
                .setIdType(IdType.AUTO) // 主键策略
                .setBaseResultMap(true) //xml ResultMap
                .setBaseColumnList(true)//xml columList
                .setEnableCache(false)// XML 二级缓存
                //自定义文件命名 %s 表示生成代码时用表名代替
//                .setMapperName("%sMapper")
//                .setXmlName("%sXml")
//                .setServiceName("I%sService") //设置生成的service接口的名字的首字母是否为I(如 IEmployeeService）
//                .setServiceImplName("%sServiceImpl")
//                .setControllerName("%sController")
                    ;

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        dsConfig.setDbType(DbType.MYSQL)  // 设置数据库类型
                .setDriverName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://47.107.134.129:3306/mp")
                .setUsername("root")
                .setPassword("KeYpZrZx")
//                .setTypeConvert(new MySqlTypeConvert() {
//                    // 自定义数据库表字段类型转换【可选】
//                    @Override
//                    public DbColumnType processTypeConvert(String fieldType) {
//                        System.out.println("转换类型：" + fieldType);
//                        // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
//                        return super.processTypeConvert(fieldType);
//                    }
//                })
        ;

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        stConfig.setCapitalMode(true) //全局大写命名，oracle注意
                .setDbColumnUnderline(true)  // 指定表名 字段名是否使用下划线
                .setNaming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                .setTablePrefix("tbl_") //设置表前缀，表示将表去掉该前缀再生成对应类名
                .setInclude("tbl_employee")// 设置需要生成的表，多个以逗号分隔；默认生成所有表
//                .setExclude("表名xxx", "表名xxx2")//设置排除生成的表
//                .setSuperEntityClass("全类名")//自定义实体继承的父类
//                .setSuperEntityColumns(new String[]{"test_id", "age"})//自定义类的公共字段
//                .setSuperMapperClass("全类名")//自定义mapper继承的父mapper
//                .setSuperServiceClass("全类名")//自定义 service 父类
//                .setSuperServiceImplClass("全类名")// 自定义 service 实现类父类
//                .setSuperControllerClass("全类名");//自定义controller父类
                    ;
                // 【实体】是否生成字段常量（默认 false,如：public static final String ID = "test_id";）
                // stConfig.setEntityColumnConstant(true);
                // 【实体】是否为构建者模型（默认 false，如：public User setName(String name) {this.name = name; return this;}）
                // stConfig.setEntityBuilderModel(true);

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        pkConfig.setParent("com.group.demo1")
                .setMapper("mapper")
                .setService("service")
                .setController("controller")
                .setEntity("beans")
                .setXml("mapper");

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 【可无】
//        InjectionConfig cfg = new InjectionConfig() {
//            @Override
//            public void initMap() {
//                Map<String, Object> map = new HashMap<String, Object>();
//                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
//                this.setMap(map);
//            }
//        };

        // 自定义 xxList.jsp 生成
//        List<FileOutConfig> focList = new ArrayList<>();
//        focList.add(new FileOutConfig("/template/list.jsp.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                // 自定义输入文件名称
//                return "D://my_" + tableInfo.getEntityName() + ".jsp";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//
//        // 调整 xml 生成目录演示
//        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
//            @Override
//            public String outputFile(TableInfo tableInfo) {
//                return "/develop/code/xml/" + tableInfo.getEntityName() + ".xml";
//            }
//        });
//        cfg.setFileOutConfigList(focList);
//
//        // 关闭默认 xml 生成，调整生成 至 根目录
//        TemplateConfig tc = new TemplateConfig();
//        tc.setXml(null);


        // 自定义模板配置，可以 copy 源码 mybatis-plus/src/main/resources/templates 下面内容修改，
        // 放置自己项目的 src/main/resources/templates 目录下, 默认名称一下可以不配置，也可以自定义模板名称
        // TemplateConfig tc = new TemplateConfig();
        // tc.setController("...");
        // tc.setEntity("...");
        // tc.setMapper("...");
        // tc.setXml("...");
        // tc.setService("...");
        // tc.setServiceImpl("...");
        // 如上任何一个模块如果设置 空 OR Null 将不生成该模块。


        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig)
        // .setTemplate(tc)
        // .setTemplate(tc)
        // .setCfg(cfg)
         //.setCfg(cfg)
        ;

        //6. 执行
        ag.execute();
    }


}
