package demo;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Pdf2TextUtil {


    /**
     * 传入一个pdf文件str（文件路径）
     * @param fileStr
     * @throws Exception
     */
    public static String readPdf(String fileStr) throws Exception {
        // 是否排序
        boolean sort = false;
        File pdfFile=new File(fileStr);
        // 输入文本文件名称
        String textFile = null;
        // 编码方式
        String encoding = "UTF-8";
        // 开始提取页数
        int startPage = 1;
        // 结束提取页数
        int endPage = Integer.MAX_VALUE;
        // 文件输入流，生成文本文件
        Writer output = null;
        // 内存中存储的PDF Document
        PDDocument document = null;
        try {

            //注意参数是File。
            document = PDDocument.load(pdfFile);

            // 以原来PDF的名称来命名新产生的txt文件
            textFile=fileStr.replace(".pdf",".txt");

            // 文件输入流，写入文件倒textFile
            output = new OutputStreamWriter(new FileOutputStream(textFile),encoding);
            // PDFTextStripper来提取文本
            PDFTextStripper stripper = null;
            stripper = new PDFTextStripper();
            // 设置是否排序
            stripper.setSortByPosition(sort);
            // 设置起始页
            stripper.setStartPage(startPage);
            // 设置结束页
            stripper.setEndPage(endPage);
            // 调用PDFTextStripper的writeText提取并输出文本
            stripper.writeText(document, output);

            System.out.println(" pdf转txt成功！");
            return textFile;
        } finally {
            if (output != null) {
                // 关闭输出流
                output.close();
            }
            if (document != null) {
                // 关闭PDF Document
                document.close();
            }
        }
    }

    public static void main(String[] args) {
        try {
            //单个pdf转txt
            String filePath="C:\\Users\\Administrator\\Desktop\\人类简史：从动物到上帝（以）尤瓦尔·赫拉利.pdf";
            String txtStr = readPdf(filePath);
            System.out.println("txtStr="+txtStr);

//            //遍历读取文件夹下的文件
//            String strPath="G:\\test\\publication-tran1";
//            List<File> fileList = FileUtil.getFileList(strPath);
//            for (int i=0;i<fileList.size();i++){
//                try {
//                    String txtStr = readPdf(fileList.get(i).getAbsolutePath());
//                } catch (Exception e) {
//                    System.out.println("出错："+fileList.get(i).getAbsolutePath());
//                }
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}