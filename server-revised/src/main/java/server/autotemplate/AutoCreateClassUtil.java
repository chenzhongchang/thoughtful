package server.autotemplate;

import server.autotemplate.constant.AutoCreateConstant;
import server.autotemplate.util.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class AutoCreateClassUtil {


    /**
     * 创建实体类（通过一个模版来生成一个实体类）
     */
    private static void createEntityClass() throws Exception{
        //创建一个实体类的。java的源文件E:\work_tool\selfStudy\wk\autoCMS\src\main\java
        String classpackage= AutoCreateConstant.BASEPACKAGE +AutoCreateConstant.ENTITYPACKAGE;
        classpackage=classpackage.replaceAll("\\.","/");
        String newClassName = Utils.getHomeDir(classpackage)+AutoCreateConstant.ENTITYCLASS+".java";
        // 获取对应的模板文件
        String templatepackage=AutoCreateConstant.BASEPACKAGE+AutoCreateConstant.TEMPLATEPACKAGE;
        templatepackage=templatepackage.replaceAll("\\.","/");
        String actionTempContent = Utils.readFile(Utils.getHomeDir(templatepackage)+"entity.txt");
        System.out.println(Utils.getHomeDir(templatepackage)+"entity.txt");
        // 判断目录是否存在，不存在就创建一个
        new File(newClassName).getParentFile().mkdirs();
        if(!Utils.isExist(newClassName)) {
            Utils.bulidClass(actionTempContent, newClassName, AutoCreateConstant.ENTITYPACKAGE, AutoCreateConstant.DAOPACKAGE);
            System.out.println("创建实体Entity类"+newClassName+"成功了!");
        }else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            if(line !=null &&line.equalsIgnoreCase("y")){
                Utils.bulidClass(actionTempContent, newClassName, AutoCreateConstant.ENTITYPACKAGE, AutoCreateConstant.DAOPACKAGE);
                System.out.println("覆盖新的实体Entity类"+newClassName+"成功了!");
            }
        }
    }

    /**
     * 创建实体dao类（通过一个模版来生成一个实体类）
     */
    private static void createInterfaceClass() throws Exception{
        //创建一个实体类的。java的源文件E:\work_tool\selfStudy\wk\autoCMS\src\main\java
        String classpackage=AutoCreateConstant.BASEPACKAGE+AutoCreateConstant.DAOPACKAGE;
        classpackage=classpackage.replaceAll("\\.","/");
        String newClassName = Utils.getHomeDir(classpackage)+AutoCreateConstant.ENTITYDAOCLASS+".java";
        // 获取对应的模板文件
        String templatepackage=AutoCreateConstant.BASEPACKAGE+AutoCreateConstant.TEMPLATEPACKAGE;
        templatepackage=templatepackage.replaceAll("\\.","/");
        String actionTempContent = Utils.readFile(Utils.getHomeDir(templatepackage)+"entityDao.txt");
        System.out.println(Utils.getHomeDir(templatepackage)+"entityDao.txt");
        // 判断目录是否存在，不存在就创建一个
        new File(newClassName).getParentFile().mkdirs();
        if(!Utils.isExist(newClassName)) {
            Utils.bulidClass(actionTempContent, newClassName, AutoCreateConstant.ENTITYPACKAGE, AutoCreateConstant.DAOPACKAGE);
            System.out.println("创建实体EntityDao类"+newClassName+"成功了!");
        }else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            if(line !=null &&line.equalsIgnoreCase("y")){
                Utils.bulidClass(actionTempContent, newClassName, AutoCreateConstant.ENTITYPACKAGE, AutoCreateConstant.DAOPACKAGE);
                System.out.println("覆盖新的实体Entity类"+newClassName+"成功了!");
            }
        }
    }

    public static void createImplementsClass() throws Exception{
        //创建一个实体类的。java的源文件E:\work_tool\selfStudy\wk\autoCMS\src\main\java
        String classpackage=AutoCreateConstant.BASEPACKAGE+AutoCreateConstant.DAOPACKAGE;
        classpackage=classpackage.replaceAll("\\.","/");
        String newClassName = Utils.getHomeDir(classpackage+"/impl")+AutoCreateConstant.ENTITYDAOCLASS+"Impl.java";
        // 获取对应的模板文件
        String templatepackage=AutoCreateConstant.BASEPACKAGE+AutoCreateConstant.TEMPLATEPACKAGE;
        templatepackage=templatepackage.replaceAll("\\.","/");
        String actionTempContent = Utils.readFile(Utils.getHomeDir(templatepackage)+"daoImpl.txt");
        System.out.println(Utils.getHomeDir(templatepackage)+"daoImpl.txt");
        // 判断目录是否存在，不存在就创建一个
        new File(newClassName).getParentFile().mkdirs();
        if(!Utils.isExist(newClassName)) {
            Utils.bulidClass(actionTempContent, newClassName, AutoCreateConstant.ENTITYPACKAGE, AutoCreateConstant.DAOPACKAGE);
            System.out.println("创建实体EntityDaoImpl类"+newClassName+"成功了!");
        }else {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String line = reader.readLine();
            if(line !=null &&line.equalsIgnoreCase("y")){
                Utils.bulidClass(actionTempContent, newClassName, AutoCreateConstant.ENTITYPACKAGE, AutoCreateConstant.DAOPACKAGE);
                System.out.println("覆盖新的实体Entity类"+newClassName+"成功了!");
            }
        }

    }


    //java入口函数
    public static void main(String[] args) {
        System.out.println("las go");
        //获取java工程所在目录
        System.out.println("工程的根目录："+System.getProperty("user.dir"));

        //创建java项目中的实体类 autotemplate/com/tz/dao
        try {
            createEntityClass();
            createInterfaceClass();
            createImplementsClass();
            System.out.println("ok");
        } catch (Exception e) {
            System.out.println("failure");
            e.printStackTrace();
        }

    }


}
