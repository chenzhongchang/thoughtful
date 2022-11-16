package demo2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws IOException {
        testProcessBuilder1();
//        checkPhysicAddress();
    }

    /**
     * 查看"D:\"目录, Windows系统下查看目录的命令是dir
     */
    public static void checkDirectory() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd","/c","dir");
        processBuilder.directory(new File("D:/"));
        Process process = processBuilder.start();
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

    /**
     * 查看ip地址【Windows系统下】
     */
    public static void checkPhysicAddress() {
        ProcessBuilder processBuilder = new ProcessBuilder("ipconfig","/all");
        try {
//            processBuilder.command("ipconfig /all");
            processBuilder.directory(new File("C:\\Program Files (x86)\\Tesseract-OCR"));
//            List<String> command = new ArrayList<>();
//            processBuilder.command(command);
            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "GBK"));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.indexOf("IPv4") != -1) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testProcessBuilder1() {
        ProcessBuilder processBuilder = new ProcessBuilder();
//       processBuilder.command("ping","127.0.0.1");
//        processBuilder.directory(new File("C:\\Program Files (x86)\\Tesseract-OCR"));
//        "tesseract","E:\\cs1.png","1","-l","chi_sim"
        List<String> command = new ArrayList<>();
        command.add("C://Program Files (x86)//Tesseract-OCR//tesseract");// E:\cs1.png 1 -l chi_sim
        command.add("E://cs1.png");
        command.add("1");
        command.add("-l");
        command.add("chi_sim");
        processBuilder.command(command);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testProcessBuilder() {
        ProcessBuilder processBuilder = new ProcessBuilder();
//       processBuilder.command("ping","127.0.0.1");
        processBuilder.command("ipconfig");
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void testFFmpeg() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        //定义命令内容
        List<String> command = new ArrayList<>();
        command.add("D:/program/ffmpeg-20191229-e20c6d9-win64-static/bin/ffmpeg.exe");
        command.add("-i");
        command.add("D:/test/video/1.avi");
        command.add("-y");//覆盖输出文件
        command.add("-c:v");
        command.add("libx264");
        command.add("-s");
        command.add("1280x720");
        command.add("-pix_fmt");
        command.add("yuv420p");
        command.add("-b:a");
        command.add("63k");
        command.add("-b:v");
        command.add("753k");
        command.add("-r");
        command.add("18");
        command.add("D:/test/video/1.mp4");
        processBuilder.command(command);
        //将标准输入流和错误输入流合并，通过标准输入流读取信息
        processBuilder.redirectErrorStream(true);
        try {
            //启动进程
            Process start = processBuilder.start();
            //获取输入流
            InputStream inputStream = start.getInputStream();
            //转成字符输入流
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "gbk");
            int len = -1;
            char[] c = new char[1024];
            StringBuffer outputString = new StringBuffer();
            //读取进程输入流中的内容
            while ((len = inputStreamReader.read(c)) != -1) {
                String s = new String(c, 0, len);
                outputString.append(s);
                System.out.print(s);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
