package csj.thoughtful.vipconcurrent.project.two;

import csj.thoughtful.vipconcurrent.project.two.assist.CreatePendingDocs;
import csj.thoughtful.vipconcurrent.project.two.assist.SL_QuestionBank;
import csj.thoughtful.vipconcurrent.project.two.service.ProduceDocService;
import csj.thoughtful.vipconcurrent.project.two.vo.SrcDocVo;

import java.util.List;

//最初的实现，速度较慢，需要进行性能提升
public class SingleWeb {
    public static void main(String[] args) {
        System.out.println("数据库初始化............");
        SL_QuestionBank.initBank();
        System.out.println("题库初始化完成，");

        //创建两个待处理文档
        List<SrcDocVo> docList = CreatePendingDocs.makePendingDoc(2);//创建两个文档
        long startTotal = System.currentTimeMillis();
        for (SrcDocVo doc:docList){//遍历文档
            System.out.println("开始处理文档："+doc.getDocName()+".......");
            long start = System.currentTimeMillis();
            String localName = ProduceDocService.makeDoc(doc);
            System.out.println("文档"+localName+"生成耗时："
            +(System.currentTimeMillis()-start)+"ms");
            start = System.currentTimeMillis();
            String remoteUrl = ProduceDocService.upLoadDoc(localName);
            System.out.println("已上传至["+remoteUrl+"]耗时："+
            (System.currentTimeMillis()-start)+"ms");
        }
        System.out.println("-------------共耗时："+
                (System.currentTimeMillis()-startTotal)+"ms--------------");
    }
}
