package autotemplate.com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Date;


/**
 *  java开发自动生成action,dao,daoImpl,services,servicesImpl,entity,js,CRUD页面
 *  实现大型项目快捷开发技术
 *  体现了java开发中大编程思想（不重复的制造轮子）
 */
public class AutoCreateClassUtil {

	//注释：编写类文件的作者
	public static String author = "auto";
	//注释：创建类文件的时间
	public static String date = new Date().toLocaleString();
	//注释： 描述信息
	public static String description = "企业";

	//模块名称
	public static String model = "admin";
	//功能模块(类名)
	public static String entityClass = "Order";
	//实体类的变量名称
	public static String lowerentity = "order";

	//功能模块(类名)
	public static String entityDaoClass = "OrderDao";
	//实体类的变量名称
	public static String lowerentityDao = "orderDao";


	//生成包结构
	//基础包
	public static String basepackage="server.src.main.java.";
	//包头
	public static String headpackage="autotemplate.com.tz.";
	//action的包结构
	public static String actionpackage = headpackage+lowerentity+".action";
	//dao的包结构                  "com.tz."+model+"."+lowerentity+".dao";
	public static String daopackage = headpackage+lowerentity+".dao";
	//entity的包结构       "com.tz."+model+"."+lowerentity+".entity";
	public static String entitypackage = headpackage+lowerentity+".model";

	/**
	 * 创建实体类（通过一个模版来生成一个实体类）
	 */
	public static void createEntityClass() throws Exception{
		//创建一个实体类的。java的源文件E:\work_tool\selfStudy\wk\autoCMS\src\main\java
		String classpackage=basepackage+entitypackage;
		classpackage=classpackage.replaceAll("\\.","/");
		String newClassName = getHomeDir(classpackage)+entityClass+".java";
		// 获取对应的模板文件
		String templatepackage=basepackage+"autotemplate.template";
		templatepackage=templatepackage.replaceAll("\\.","/");
		String actionTempContent = readFile(getHomeDir(templatepackage)+"entity.txt");
		System.out.println(getHomeDir(templatepackage)+"entity.txt");
		// 判断目录是否存在，不存在就创建一个
		new File(newClassName).getParentFile().mkdirs();
		if(!isExist(newClassName)) {
			bulidClass(actionTempContent, newClassName, entitypackage, daopackage);
			System.out.println("创建实体Entity类"+newClassName+"成功了!");
		}else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();
			if(line !=null &&line.equalsIgnoreCase("y")){
				bulidClass(actionTempContent, newClassName, entitypackage, daopackage);
				System.out.println("覆盖新的实体Entity类"+newClassName+"成功了!");
			}
		}
	}
	/**
	 * 创建实体dao类（通过一个模版来生成一个实体类）
	 */
	public static void createEntityDaoClass() throws Exception{
		//创建一个实体类的。java的源文件E:\work_tool\selfStudy\wk\autoCMS\src\main\java
		String classpackage=basepackage+daopackage;
		classpackage=classpackage.replaceAll("\\.","/");
		String newClassName = getHomeDir(classpackage)+entityDaoClass+".java";
		// 获取对应的模板文件
		String templatepackage=basepackage+"autotemplate.template";
		templatepackage=templatepackage.replaceAll("\\.","/");
		String actionTempContent = readFile(getHomeDir(templatepackage)+"entityDao.txt");
		System.out.println(getHomeDir(templatepackage)+"entityDao.txt");
		// 判断目录是否存在，不存在就创建一个
		new File(newClassName).getParentFile().mkdirs();
		if(!isExist(newClassName)) {
			bulidClass(actionTempContent, newClassName, entitypackage, daopackage);
			System.out.println("创建实体EntityDao类"+newClassName+"成功了!");
		}else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();
			if(line !=null &&line.equalsIgnoreCase("y")){
				bulidClass(actionTempContent, newClassName, entitypackage, daopackage);
				System.out.println("覆盖新的实体Entity类"+newClassName+"成功了!");
			}
		}
	}
	public static void createDaoImplClass() throws Exception{
		//创建一个实体类的。java的源文件E:\work_tool\selfStudy\wk\autoCMS\src\main\java
		String classpackage=basepackage+daopackage;
		classpackage=classpackage.replaceAll("\\.","/");
		String newClassName = getHomeDir(classpackage+"/impl")+entityDaoClass+"Impl.java";
		// 获取对应的模板文件
		String templatepackage=basepackage+"autotemplate.template";
		templatepackage=templatepackage.replaceAll("\\.","/");
		String actionTempContent = readFile(getHomeDir(templatepackage)+"daoImpl.txt");
		System.out.println(getHomeDir(templatepackage)+"daoImpl.txt");
		// 判断目录是否存在，不存在就创建一个
		new File(newClassName).getParentFile().mkdirs();
		if(!isExist(newClassName)) {
			bulidClass(actionTempContent, newClassName, entitypackage, daopackage);
			System.out.println("创建实体EntityDaoImpl类"+newClassName+"成功了!");
		}else {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			String line = reader.readLine();
			if(line !=null &&line.equalsIgnoreCase("y")){
				bulidClass(actionTempContent, newClassName, entitypackage, daopackage);
				System.out.println("覆盖新的实体Entity类"+newClassName+"成功了!");
			}
		}
	}
	//内容替换
	public static void bulidClass(String actionTempContent,String newFilepath,String entitypkg,String daopkg) {
		actionTempContent  = actionTempContent.
				replaceAll("\\[author\\]", author).
				replaceAll("\\[date\\]", date).
				replaceAll("\\[entitypackage\\]", entitypkg).
				replaceAll("\\[daopackage\\]", daopkg).
				replaceAll("\\[description\\]", description).
				replaceAll("\\[model\\]", model).
				replaceAll("\\[lowerentity\\]", lowerentity).
				replaceAll("\\[capentity\\]", lowerentity).
				replaceAll("\\[entityClass\\]", entityClass).
				replaceAll("\\[entityDaoClass\\]", entityDaoClass);
		writeFileByLine(actionTempContent,newFilepath);
	}
	//将内容一行行写入文件中
	public static void writeFileByLine(String content,String filename) {
		File file = new File(filename);
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileOutputStream(file));
			writer.print(content);
			writer.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(writer !=null) {
				try {
					writer.close();
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	//判断文件是否存在
	public static boolean isExist(String filePath) {
		File file = new File(filePath);
		return file.exists();
	}

	//文件的读取
	public static String readFile(String filename) {
		StringBuffer buffer = new StringBuffer();
		try {
			FileInputStream inputStream =new FileInputStream(new File(filename));
			InputStreamReader inputStreamReader  = new InputStreamReader(inputStream);
			BufferedReader reader = new BufferedReader(inputStreamReader);
			String tempString = null;
			while((tempString = reader.readLine())!=null) {
				buffer.append(tempString+"\n");
			}
			reader.close();
		}catch(Exception e) {
		}
		return buffer.toString();
	}


	//将路径转换成/
	public static String conversionSpecialCharacters(String string) {
		return string.replaceAll("\\\\", "/");
	}

	//空判断
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0 || str.equals("") || str.matches("\\s*");
	}
	//不为空
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}
	//获取工程的目录
	public static String getHomeDir(String path) {
		if(isNotEmpty(path)) {
			return conversionSpecialCharacters(System.getProperty("user.dir")+"/"+path+"/");
		}else {
			return System.getProperty("user.dir");
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
			createEntityDaoClass();
			createDaoImplClass();
			System.out.println("ok");
		} catch (Exception e) {
			System.out.println("failure");
			e.printStackTrace();
		}

	}


}
