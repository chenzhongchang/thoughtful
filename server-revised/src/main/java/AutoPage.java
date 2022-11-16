import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AutoPage implements Serializable {

    // 指定的或是页面参数
    private int currentPage; // 当前页
    private int numPerPage; // 每页显示多少条
    private List<?> totalData;//所有数据

    // 查询数据库
    private int totalCount; // 总记录数
    private List<?> recordList; // 本页的数据列表

    public AutoPage(int currentPage, int numPerPage, List<?> totalData){
        this.currentPage=currentPage;
        this.numPerPage=numPerPage;
        this.totalData=totalData;

        if(null==totalData){
            this.totalCount=0;
            this.recordList=new ArrayList<>();
        }else{
            this.totalCount=totalData.size();
            this.setRecordList();
        }

    }

    public List<?> getTotalData() {
        return totalData;
    }

    public List<?> getRecordList() {
        return recordList;
    }
    private void setRecordList(){
        int startNum=0+(numPerPage*(currentPage-1));
        int endNum=numPerPage*currentPage;
        List<Object> result=new ArrayList<>();
        for(int num=0;num<totalCount;num++){
            if(startNum<=num&&num<endNum){
                Object obj=getTotalData().get(num);
                result.add(obj);
            }
        }
        this.recordList=result;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }
}
