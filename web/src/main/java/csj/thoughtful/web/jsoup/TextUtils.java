package csj.thoughtful.web.jsoup;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TextUtils {

    private static Integer index=1;
    private static Integer ratio=1;
    private static String fname="beijuyibaicide无敌";
    private static String blogName = "guoxiaolongonly";

    private static String atype="id";
    private static String aid="list";
    private static String contentType="id";
    private static String contentId="content";
    private static String jqstr="/118_118167/";
    private static String url = "https://www.qbiqu.com/118_118167/";
    private static StringBuffer sb=new StringBuffer();
    private static int timeout=30000;
    private static boolean ispage=false; //章节内容是否分页
    private static String nextBtnType="clazz|section-opt";//book_middle_title


    public static void main(String[] args) {
        doCrawlingContent(aid,atype);
    }

    /**
     * 获取文章列表
     * @param aid,type
     * aid-a链接的标识，type-a链接标识的类型
     */
    public static void doCrawlingContent(String aid,String atype){
        Document doc = null;
        Elements elements = null;
        Elements els =null;
        try {
//            doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(10000).get();
            Connection connection=Jsoup.connect(url);
            connection=connection.method(Connection.Method.GET).timeout(timeout);
            doc = connection.get();
            if("id".equals(atype)){
                Element el = doc.getElementById(aid);
                elements = el.getElementsByTag("a");//找到所有a标签
            }else if("tag".equals(atype)){
                els = doc.getElementsByTag(aid);
                elements = els.get(index).getElementsByTag("a");//找到所有a标签
            }else if("clazz".equals(atype)){
                els = doc.getElementsByClass(aid);
                elements = els.get(index).getElementsByTag("a");//找到所有a标签
            }

        } catch (IOException e) {
            System.out.println("======e:"+e);
        }
        int saveNum=100;
        for(int i =0;i<(elements.size())/ratio;i++) {
            if(ispage&&i>=1){
                saveArticle(sb,blogName);
            }
            Element element = elements.get(i);
            if(elements.size()==1) {
                element = els.get(i).getElementsByTag("a").get(index);
            }
            String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
            System.out.println("href:"+relHref);
            String linkHref = element.text();
            sb.append(linkHref+"\r\n");
            //用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
            if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
            {
                StringBuffer sbb = new StringBuffer();
                String newUrl=url.replace(jqstr,"");
                newUrl=newUrl+relHref;
//                relHref = relHref.substring(jqstr.length(),relHref.length());
//                sbb.append(url).append(relHref);
                System.out.println(newUrl);//去掉最后的#comment输出
                getArticleFromUrl(newUrl);//可以通过这个url获取文章了
            }else {
                getArticleFromUrl(relHref);//可以通过这个url获取文章了
            }
            if(i>saveNum){
                saveNum=saveNum+100;
                saveArticle(sb,blogName);
            }
        }
        saveArticle(sb,blogName);
    }

    /**
     * 获取文章内容
     * @param detailurl
     */
    public static void getArticleFromUrl(String detailurl) {
        try {
            Document document = Jsoup.connect(detailurl).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31").timeout(timeout).get();
//			Jsoup.connect(detailurl).get();
            String content = null;
            if("clazz".equals(contentType)) {
                content = document.getElementsByClass(contentId).get(0).text().replaceAll("。", "。\r\n");
            }else if("tag".equals(contentType)){
                content = document.getElementsByTag(contentId).get(0).text().replaceAll("。", "。\r\n");
            }else if("id".equals(contentType)){
                content = document.getElementById(contentId).text().replaceAll("。", "。\r\n");
            }
            content=content.replaceAll("<p>","").replaceAll("\\*","").
                    replaceAll("\uE2D6","").replaceAll(" 　　 　　","");
            sb.append(content+"\r\n");
            if(ispage){
                getNextPageContent(document);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            saveArticle(sb,blogName);
        }
    }
    /**
     * 获取下一页文章内容
     * @param doc
     */
    public static void getNextPageContent(Document doc){
        Elements elements = null;
        Elements els =null;
        String id="";
        try {
            Element cs = doc.getElementsByClass("title").get(0);
            if(cs!=null){
                String title=cs.text();
                sb.append(title+"\r\n");
            }
            if(nextBtnType.indexOf("id|")!=-1){
                id=nextBtnType.replace("id|","");
                Element el = doc.getElementById(id);
                elements = el.getElementsByTag("a");//找到所有a标签
            }else if(nextBtnType.indexOf("tag|")!=-1){
                id=nextBtnType.replace("tag|","");
                els = doc.getElementsByTag(id);
                elements = els.get(index).getElementsByTag("a");//找到所有a标签
            }else if(nextBtnType.indexOf("clazz|")!=-1){
                id=nextBtnType.replace("clazz|","");
                els = doc.getElementsByClass(id);
                elements = els.get(index).getElementsByTag("a");//找到所有a标签
            }
        }catch (Exception e){
            System.out.println("===e:"+e);
        }

        for(int i =0;i<(elements.size())/ratio;i++) {
            Element element = elements.get(i);
            if(elements.size()==1) {
                element = els.get(i).getElementsByTag("a").get(index);
            }
            String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
            String linkHref = element.text();
            if(linkHref.equals("下一章")){//下一页
                if(relHref.indexOf(".html")!=-1){
                    System.out.println("href:"+relHref);
                    //用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
                    if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
                    {
                        StringBuffer sbb = new StringBuffer();
                        String newUrl=url.replace(jqstr,"");
                        newUrl=newUrl+relHref;
//                relHref = relHref.substring(jqstr.length(),relHref.length());
//                sbb.append(url).append(relHref);
                        System.out.println(newUrl);//去掉最后的#comment输出
                        getArticleFromUrl(newUrl);//可以通过这个url获取文章了
                    }else {
                        getArticleFromUrl(relHref);//可以通过这个url获取文章了
                    }
                }
            }

        }
    }


    /**
     * 保存文章到本地
     * @param content
     * @param blogName
     */
    public static void saveArticle(StringBuffer content, String blogName) {
        String lujing = "d:\\MyLoadArticle\\" + blogName + "\\" + fname + ".txt";//保存到本地的路径和文件名
        File file = new File(lujing);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            FileOutputStream out=new FileOutputStream(file,false);
//			FileWriter fw = new FileWriter(file, true);
//			BufferedWriter bw = new BufferedWriter(fw);
            out.write(content.toString().getBytes("UTF-8"));
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("===========报错了==============");
            e.printStackTrace();
        }
        System.out.println("============保存成功===========位置："+lujing);
    }



}
