package csj.thoughtful.web.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ReplaceFileMain {

    private static final String srcDir="E:\\learning\\wk\\dbcom-order\\system\\src\\main\\resources\\mapper";
    
    public static void main(String[] args) {
        List<File> fileList=getAllFile(new File(srcDir));

            for (File f:fileList){
                try {
                    replaceFileStr(f);
                    updateNewFile(f);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }

    public static void updateNewFile(File file)throws IOException {
        String filePath=file.getAbsolutePath();
        String fileName=file.getName();
        String newName="temp.java";
        filePath=filePath.replace(fileName,newName);
        File readFile=new File(filePath);

        if(!readFile.exists()){
            return;
        }
        String outFilePath=file.getAbsolutePath();
        if(file.delete()){
            System.out.println("文件："+file.getName()+"已删除");
        }
        readFile.renameTo(new File(outFilePath));
        System.out.println("文件名："+readFile.getName()+"已修改为："+fileName);
//        if(readFile.delete()){
//            System.out.println("文件："+readFile.getName()+"已删除");
//        }

    }

    public static void replaceFileStr(File file)throws IOException {
        String filePath=file.getAbsolutePath();
        String fileName=file.getName();
        String newName="temp.java";
        filePath=filePath.replace(fileName,newName);
        File outFile=new File(filePath);

        if(!file.exists()){
            return;
        }else if(!outFile.exists()){
            if(!outFile.createNewFile()){
                return;
            }
        }

        //读取文本,用字符流.如果是其他二进制文件，可使用字节流，在try中创建可自动关闭
        try (BufferedReader reader=new BufferedReader(new FileReader(file));
             BufferedWriter writer=new BufferedWriter(new FileWriter(outFile));){
            String readStr;
            //读一行写一行
            while((readStr=reader.readLine())!=null){
                if(readStr.equals(""))
                    continue;
                String oldStr="net.order";
                String newStr="net.dbcom";
                if(readStr.indexOf(oldStr)!=-1) {
                    System.out.println(readStr);
                    readStr = readStr.replace(oldStr, newStr);
                }
                writer.write(readStr);
                writer.newLine();
            }

                //输出流会在close之前自动执行flush,也可以根据情况手动执行
//                    writer.flush();
        }
    }

    //获取目录下的所有文件
    public static List<File> getAllFile(File dirFile){
        if(Objects.isNull(dirFile)|| !dirFile.exists() || dirFile.isFile())
            return null;
        File[] childrenFiles = dirFile.listFiles();
        if(Objects.isNull(childrenFiles)||childrenFiles.length==0)
            return null;
        List<File> files=new ArrayList<>();
        for(File childFile:childrenFiles){
            if (childFile.isFile()){
                files.add(childFile);
            }else{
                //子目录不为空则继续递归
                List<File> cfiles=getAllFile(childFile);
                if(Objects.isNull(cfiles)||cfiles.isEmpty())
                    continue;
                files.addAll(cfiles);
            }
        }
        return files;
    }

    public static boolean isFile(File file){
        if(file.exists()){
            return  file.isFile();
        }
        return false;
    }

}
