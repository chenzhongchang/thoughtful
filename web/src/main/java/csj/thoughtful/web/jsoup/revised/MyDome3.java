package csj.thoughtful.web.jsoup.revised;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyDome3 {

	private static String url = "https://www.wangshuge.com/books/79/79883/";
	private static String filePath = "D:\\MyBooks\\";
	private static String fname = "ylgw1";
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
	private static Long startTime;//用于计算爬取时间





	public static void main(String[] args) {
		startTime=System.currentTimeMillis();
		getArticleListFromUrl(url);
	}
	/**
	 * 获取文章列表
	 * @param listurl
	 */
	public static void getArticleListFromUrl(String listurl) {
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
		if(elements.size()==1) {
			for(int i =0;i<(elements.size())/ratio;i++) {
				Element element = els.get(i).getElementsByTag("a").get(index);
				String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
//				System.out.println("href:"+relHref);
				String linkHref = element.text();
				sb.append(linkHref+"\r\n");
				//用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
				if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
				{
					StringBuffer sbb = new StringBuffer();
					relHref = relHref.substring(jqstr.length(),relHref.length());
					sbb.append(url).append(relHref);
//					System.out.println(sbb.toString());//去掉最后的#comment输出
					getArticleFromUrl(sbb.toString());//可以通过这个url获取文章了
				}else {
					getArticleFromUrl(relHref);//可以通过这个url获取文章了
				}
			}
		}else {
			for(int i =0;i<(elements.size())/ratio;i++) {
				Element element = elements.get(i);
				String relHref = element.attr("href"); // == "/"这个是href的属性值，一般都是链接。这里放的是文章的连接
//				System.out.println("href:"+relHref);
				String linkHref = element.text();
				sb.append(linkHref+"\r\n");
				//用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
				if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
				{
					StringBuffer sbb = new StringBuffer();
					relHref = relHref.substring(jqstr.length(),relHref.length());
					sbb.append(url).append(relHref);
//					System.out.println(sbb.toString());//去掉最后的#comment输出
					getArticleFromUrl(sbb.toString());//可以通过这个url获取文章了
				}else {
					getArticleFromUrl(relHref);//可以通过这个url获取文章了
				}
			}
		}
		saveArticle(sb.toString());
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

			sb.append(content+"\r\n");
			long end=System.currentTimeMillis();
			String outstr=detailurl+"耗时："+(end-begin)+"ms";
			System.out.println(outstr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 保存文章到本地
	 * @param content
	 */
	public static void saveArticle(String content) {
		String lujing = filePath+ "\\" + fname + ".txt";//保存到本地的路径和文件名
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
			out.write(content.getBytes("UTF-8"));
			out.flush();
			out.close();
			long endTime=System.currentTimeMillis();
			long useTime=(endTime-startTime)/1000;
			System.out.println("爬取这本书耗时："+useTime+"s");//193s
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
