package cn;

import net.sf.sevenzipjbinding.*;
import net.sf.sevenzipjbinding.impl.RandomAccessFileInStream;
import net.sf.sevenzipjbinding.simple.ISimpleInArchive;
import net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.util.Random;

public class PwdDictionary {

    public static void initBank(){


    }


    //生成随机字符串，代表题目的实际内容,length表示题目的长度
    private static String makePwd() throws Exception {
        byte a0,a1,a2,a3,a4,a5,a6,a7;
        StringBuffer sb;
        long startTime=System.currentTimeMillis();
        int num=0;
        for(a0=49;a0<58;a0++){
            for(a1=48;a1<58;a1++){
                for(a2=48;a2<58;a2++){
                    for(a3=48;a3<58;a3++){
                        for(a4=48;a4<58;a4++){
                            for(a5=48;a5<58;a5++){
                                for(a6=48;a6<58;a6++){
                                    for(a7=48;a7<58;a7++){
                                        sb=new StringBuffer();
                                        sb.append((char)a0).append((char)a1).append((char)a2).append((char)a3).
                                                append((char)a4).append((char)a5).append((char)a6).append((char)a7);
                                        String pwd=sb.toString();
                                        if(checkLogarithm(pwd)&&checkNatural(pwd)){
                                            if(un7z("E:\\signal.7z", "E:\\signal", pwd)){
                                                System.out.println("密码是"+pwd);
                                                return pwd;
                                            }
                                            System.out.println("num="+(++num));
                                            if(0==(num%5000)){
                                                long endTime=System.currentTimeMillis();
                                                System.out.println("num:"+num+"---------耗时："+(endTime-startTime)/1000+"s");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        long endTime=System.currentTimeMillis();
        System.out.println(" 耗时："+(endTime-startTime)/1000+"s");
//        String base = "1234567890";
//        for()
//        return sb.toString();
        return null;
    }

    private static boolean checkNatural(String pwd){
        for(int i=0;i<pwd.length()-1;i++){
            char a=pwd.charAt(i);
            char b=pwd.charAt(i+1);
            if(a==b-1)
                return false;
        }
        return true;
    }

    private static boolean checkLogarithm(String pwd){
        for(int i=0;i<pwd.length()-1;i++){
            char a=pwd.charAt(i);
            char b=pwd.charAt(i+1);
            if(a==b)
                return false;
        }
        return true;
    }


    //生成随机字符串，代表题目的实际内容,length表示题目的长度
    private static String makeQuestionDetail(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //解压密码核对模块，file7zPath压缩文件路径，outPutPath解压文件存放路径，passWord压缩密码
    public static boolean un7z(String file7zPath, final String outPutPath, String passWord) throws Exception {
        boolean flag = false;
        IInArchive archive;
        RandomAccessFile randomAccessFile;
        randomAccessFile = new RandomAccessFile(file7zPath, "r");
        archive = SevenZip.openInArchive(null, new RandomAccessFileInStream(randomAccessFile), passWord);
        int numberOfItems = archive.getNumberOfItems();
        ISimpleInArchive simpleInArchive = archive.getSimpleInterface();
        //for (final ISimpleInArchiveItem it\em : simpleInArchive.getArchiveItems()) {
        ISimpleInArchiveItem item=simpleInArchive.getArchiveItems()[0];
        if (!item.isFolder()) {
            ExtractOperationResult result;
            result = item.extractSlow(new ISequentialOutStream() {
                public int write(byte[] data) throws SevenZipException {
                    try {
                        String parentFilePath = outPutPath;
                        if (!new File(parentFilePath).exists()) {
                            new File(parentFilePath).mkdirs();
                        }
                        IOUtils.write(data, new FileOutputStream(new File(outPutPath + File.separator + item.getPath()), true));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return data.length; // Return amount of consumed
                }
            }, passWord);
            if (result == ExtractOperationResult.OK) {
                flag=true;
            } else {
                flag=false;
            }
        }
        archive.close();
        randomAccessFile.close();
        return flag;
    }

    public static void main(String[] args) throws Exception {
        String pwd=makePwd();
//        System.out.println("===pwd:"+pwd);
//        for (int a=1;a<127;a++){
//            System.out.println(a+"="+((char)a));
//        }

//        String pwd="6852796";
//        boolean b=checkNatural(pwd);
//        System.out.println("====b:"+b);
    }

}
