package csj.thoughtful.web.jsoup.better;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.*;

public class Demo1 {

    private static String url = "https://www.wangshuge.com/books/79/79883/";
    private static String filePath = "D:\\MyBooks\\";
    private static String fname = "ylgw";
    private static Integer ratio = 1;//  章节数除以/ratio = 爬取章节
    private static volatile StringBuffer sb = new StringBuffer(); //存储爬取的内容
    private static Integer index=0;

    //章节节点
    private static String id="at";
    private static String tag="";
    private static String clazz="";
    //文章节点
    private static String contentClazz="";
    private static String contentId="contents";
    private static String contentTag="";
    //截取字符串
    private static String jqstr="";

    private volatile Executor executor = null;

    private static DelayQueue<ItemVo<String>> queue
            = new DelayQueue<>();//存放已完成任务的文章内容

    private static Long startTime;//用于计算爬取时间


    public static class ItemVo<T> implements Delayed {
        private long activeTime;
        private T data;

        public ItemVo(long activeTime, T data){
            this.activeTime= TimeUnit.NANOSECONDS.convert(activeTime,TimeUnit.NANOSECONDS)+System.nanoTime();
            this.data=data;
        }

        public long getActiveTime() {
            return activeTime;
        }

        public T getData() {
            return data;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            long d = unit.convert(this.activeTime-System.nanoTime(),TimeUnit.NANOSECONDS);
            return d;
        }

        @Override
        public int compareTo(Delayed o) {
            long d=this.getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
            return d == 0L ? 0 : (d > 0L?1:-1);
        }
    }


    //任务完成后，放入队列，经过expireTime时间后，从整个框架中一出
    public void putJob(String jobName, long expireTime){
        ItemVo<String> item = new ItemVo<>(expireTime,jobName);
        queue.offer(item);
        System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime+"]");
    }

    static{
        Thread thread = new Thread(new Demo1.FetchJob());
        thread.setDaemon(true);
        thread.start();
        System.out.println("开启任务过期检查守护线程..............");
    }


    //处理队列中到期任务的线程
    private static class FetchJob implements Runnable{

        @Override
        public void run() {
            while(true){
                try {
                    //拿到已经过去的任务
                    ItemVo<String> item = queue.take();
                    String jobName=(String)item.getData();
                    System.out.println(jobName);
                    if(queue.size()==0){
                        System.out.println(queue.size()+" is null 1");
                        Thread.sleep(3*1000);
                        if(queue.size()==0&&!"".equals(sb.toString())){
                            System.out.println(queue.size()+" is null 2");
                            saveArticle(sb);
                        }
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Demo1 demo1=new Demo1();
        startTime=System.currentTimeMillis();
        demo1.getArticleListFromUrl(url);
    }


    /**
     * 获取文章列表
     * @param listurl
     */
    public void getArticleListFromUrl(String listurl) {
        Document doc = null;
        try {
            doc = Jsoup.connect(listurl).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(10000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println(doc);
        Elements elements =null;
        Elements els =null;
        if(!"".equals(id)) {
            Element el = doc.getElementById(id);
            elements = el.getElementsByTag("a");//找到所有a标签
        }
        if(!"".equals(tag)) {
            els = doc.getElementsByTag(tag);
            elements = els.get(index).getElementsByTag("a");//找到所有a标签
        }
        if(!"".equals(clazz)) {
            els = doc.getElementsByClass(clazz);
            elements = els.get(index).getElementsByTag("a");//找到所有a标签
        }
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor();
        }
        if(elements.size()==1) {
            for(int i =0;i<(elements.size())/ratio;i++) {
                Element element = els.get(i).getElementsByTag("a").get(index);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
//                        System.out.println("href:"+relHref);
                        String linkHref = element.text();
                        sb.append(linkHref+"\r\n");
                        //用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
                        if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
                        {
                            StringBuffer sbb = new StringBuffer();
                            relHref = relHref.substring(jqstr.length(),relHref.length());
                            sbb.append(url).append(relHref);
//                            System.out.println(sbb.toString());//去掉最后的#comment输出
                            getArticleFromUrl(sbb.toString());//可以通过这个url获取文章了
                        }else {
                            getArticleFromUrl(relHref);//可以通过这个url获取文章了
                        }
                    }
                });
            }
        }else {
            for(int i =0;i<(elements.size())/ratio;i++) {
                Element element = elements.get(i);
                int finalI = i;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
//                        System.out.println("href:"+relHref);
//                        if(finalI >22){
//                            System.out.println("============element:"+element);
//                        }
                        String linkHref = element.text();
                        sb.append(linkHref+"\r\n");
                        //用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
                        if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
                        {
                            StringBuffer sbb = new StringBuffer();
                            relHref = relHref.substring(jqstr.length(),relHref.length());
                            sbb.append(url).append(relHref);
//                            System.out.println(sbb.toString());//去掉最后的#comment输出
                            getArticleFromUrl(sbb.toString());//可以通过这个url获取文章了
                        }else {
                            getArticleFromUrl(relHref);//可以通过这个url获取文章了
                        }
//						putJob
                    }
                });

            }
        }
        saveArticle(sb);
    }
    /**
     * 获取文章内容
     * @param detailurl
     */
    public static void getArticleFromUrl(String detailurl) {
        try {
            long begin=System.currentTimeMillis();
            Document document = Jsoup.connect(detailurl).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(10000).get();
//			Jsoup.connect(detailurl).get();
            String content = null;
            if(!"".equals(contentClazz)) {
                content = document.getElementsByClass(contentClazz).get(0).text().replaceAll("。", "。\r\n");
            }else if(!"".equals(contentTag)){
                content = document.getElementsByTag(contentTag).get(0).text().replaceAll("。", "。\r\n");
            }else if(!"".equals(contentId)){
                content = document.getElementById(contentId).text().replaceAll("。", "。\r\n");
            }
            content=content.replaceAll("<p>","");
            sb.append(content+"\r\n");
            long end=System.currentTimeMillis();
            String outstr=detailurl+"耗时："+(end-begin)+"ms";
            queue.add(new ItemVo<>(0,outstr));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 保存文章到本地
     * @param content
     */
    public static void saveArticle(StringBuffer content) {
        String lujing = filePath + "\\" + fname + ".txt";//保存到本地的路径和文件名
        FileOutputStream out=null;
        try {
            File file = new File(lujing);

            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            out=new FileOutputStream(file,false);
            out.write(content.toString().getBytes("UTF-8"));
            out.flush();
            out.close();
            System.out.println("============保存成功===========位置："+lujing);
        } catch (IOException e) {
            System.out.println("===========报错了==============");
            e.printStackTrace();
        }
        long endTime=System.currentTimeMillis();
        long useTime=(endTime-startTime)/1000;
        System.out.println("===========爬取本书耗时："+useTime+"s");//200s

    }




}
