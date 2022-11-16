package server.autotemplate.constant;

import java.util.Date;
public interface AutoCreateConstant {

    /**注释：编写类文件的作者*/
    public static final String AUTHOR="auto";
    /**注释：创建类文件的时间*/
    public static final String DATE = new Date().toLocaleString();
    /**注释： 描述信息*/
    public static final String DESCRIPTION = "企业";


    /**注释： 模块名称*/
    public static final String MODEL = "admin";
    /**注释： 功能模块(类名)*/
    public static final String ENTITYCLASS = "Book";
    /**注释： 实体类的变量名称*/
    public static final String LOWERENTITY = "book";

    /**注释： Dao名*/
    public static final String ENTITYDAOCLASS = ENTITYCLASS+"Dao";
    //实体类的变量名称
    /**注释： Dao名变量*/
    public static final String LOWERENTITYDAO = LOWERENTITY+"Dao";


    //生成包结构
    /**注释： 基础包*/
    public static final String BASEPACKAGE="server.src.main.java.";

    public static final String TEMPLATEPACKAGE="csj.thoughtful.server.autotemplate.template";
    /**注释： 包头*/
    public static final String HEADPACKAGE="csj.thoughtful.server.com.tz."+MODEL+".";
    /**注释： action的包结构*/
    public static String ACTIONPACKAGE = HEADPACKAGE+LOWERENTITY+".action";
    //dao的包结构                  "com.tz."+model+"."+lowerentity+".dao";
    /**注释： dao的包结构*/
    public static String DAOPACKAGE = HEADPACKAGE+LOWERENTITY+".dao";
    //entity的包结构       "com.tz."+model+"."+lowerentity+".entity";
    /**注释： entity的包结构*/
    public static String ENTITYPACKAGE = HEADPACKAGE+LOWERENTITY+".model";


}
