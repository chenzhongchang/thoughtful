package csj.thoughtful.web.jsoup;

import java.io.*;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class MyDome3 {           
	private static String url = "https://www.bxwx.tv/book/25308665/";
	private static String blogName = "guoxiaolongonly";
	private static String fname = "他都渡劫了，还叫我相信科学";
	private static Integer ratio = 1;
	private static StringBuffer sb = new StringBuffer();
	private static Integer index=0;

	//章节节点
	private static String id="listmain";
	private static String tag="";
	private static String clazz="";
	//文章节点
	private static String contentClazz="";
	private static String contentId="content";
	private static String contentTag="";
	//截取字符串
	private static String jqstr="";

	private volatile Executor executor = null;

	private static DelayQueue<ItemVo<String>> queue
			= new DelayQueue<>();//存放已完成任务等待过期的队列
	private static StringBuffer context=new StringBuffer();
	private static Long startTime;


	//任务完成后，放入队列，经过expireTime时间后，从整个框架中一出
	public void putJob(String jobName, long expireTime){
		ItemVo<String> item = new ItemVo<>(expireTime,jobName);
		queue.offer(item);
		System.out.println("Job["+jobName+"已经放入了过期检查缓存，过期时长："+expireTime+"]");
	}

	static{
		Thread thread = new Thread(new FetchJob());
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
					context.append(jobName);
					if(queue.size()==0){
						System.out.println(queue.size()+" is null 1");
						Thread.sleep(3*1000);
						if(queue.size()==0&&!"".equals(context.toString())){
							System.out.println(queue.size()+" is null 2");
							long endTime=System.currentTimeMillis();
							long useTime=(endTime-startTime)/1000;
							System.out.println("===========耗时："+useTime+"s");
							saveArticle(sb, blogName);
						}
					}
				}catch (InterruptedException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		MyDome3 myDome3=new MyDome3();
		startTime=System.currentTimeMillis();
		myDome3.getArticleListFromUrl(url);
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
					System.out.println("href:"+relHref);
					String linkHref = element.text();
					sb.append(linkHref+"\r\n");
					//用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
					if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
					{
						StringBuffer sbb = new StringBuffer();
						relHref = relHref.substring(jqstr.length(),relHref.length());
						sbb.append(url).append(relHref);
						System.out.println(sbb.toString());//去掉最后的#comment输出
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
						System.out.println("href:"+relHref);
						if(finalI >22){
							System.out.println("============element:"+element);
						}
						String linkHref = element.text();
						sb.append(linkHref+"\r\n");
						//用if语句过滤掉不是文章链接的内容。因为文章的链接有两个，但评论的链接只有一个，反正指向相同的页面就拿评论的链接来用吧
						if ((!relHref.startsWith("http://")&&!relHref.startsWith("https://")) && relHref.endsWith("html"))
						{
							StringBuffer sbb = new StringBuffer();
							relHref = relHref.substring(jqstr.length(),relHref.length());
							sbb.append(url).append(relHref);
							System.out.println(sbb.toString());//去掉最后的#comment输出
							getArticleFromUrl(sbb.toString());//可以通过这个url获取文章了
						}else {
							getArticleFromUrl(relHref);//可以通过这个url获取文章了
						}
//						putJob
					}
				});

			}
		}
		saveArticle(sb, blogName);
	}
	/**
	 * 获取文章内容
	 * @param detailurl
	 */
	public static void getArticleFromUrl(String detailurl) {
		try {
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
			queue.add(new ItemVo<>(0,content));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
