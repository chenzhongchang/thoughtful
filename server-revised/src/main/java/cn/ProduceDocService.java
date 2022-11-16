package cn;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class ProduceDocService {


    //异步化处理题目的方法
    public static String makeDocAsyn(SrcDocVo pendingDocVo) throws
            InterruptedException, ExecutionException {
        System.out.println("开始处理文档："+ pendingDocVo.getDocName());
//
//        Map<Integer,TaskResultVo> qstResultMap = new HashMap<>();
//        //循环处理文档中的每个题目,准备并行化处理题目
//        for(Integer questionId: pendingDocVo.getQuestionList()){
//            qstResultMap.put(questionId, ParallerQstService.makeQuestion(questionId));
//        }
//        StringBuffer sb = new StringBuffer();
//        for(Integer questionId: pendingDocVo.getQuestionList()){
//            TaskResultVo result = qstResultMap.get(questionId);
//            sb.append(result.getQuestionDetail()==null?
//                    result.getQuestionFuture().get().getQuestionDetail()
//                    :result.getQuestionDetail());
//        }
        return "complete_"+System.currentTimeMillis()+"_"
                + pendingDocVo.getDocName()+".pdf";

    }


}
