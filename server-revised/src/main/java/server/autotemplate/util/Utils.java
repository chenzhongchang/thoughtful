package server.autotemplate.util;

import server.autotemplate.constant.AutoCreateConstant;

import java.io.*;

public class Utils {
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

    //内容替换
    public static void bulidClass(String actionTempContent,String newFilepath,String entitypkg,String daopkg) {
        actionTempContent  = actionTempContent.
                replaceAll("\\[author\\]", AutoCreateConstant.AUTHOR).
                replaceAll("\\[date\\]", AutoCreateConstant.DATE).
                replaceAll("\\[entitypackage\\]", entitypkg).
                replaceAll("\\[daopackage\\]", daopkg).
                replaceAll("\\[description\\]", AutoCreateConstant.DESCRIPTION).
                replaceAll("\\[model\\]", AutoCreateConstant.MODEL).
                replaceAll("\\[lowerentity\\]", AutoCreateConstant.LOWERENTITY).
                replaceAll("\\[capentity\\]", AutoCreateConstant.LOWERENTITY).
                replaceAll("\\[entityClass\\]", AutoCreateConstant.ENTITYCLASS).
                replaceAll("\\[entityDaoClass\\]", AutoCreateConstant.ENTITYDAOCLASS);
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
}
