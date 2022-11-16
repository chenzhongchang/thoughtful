package server.com.tz.admin.book.dao;

import java.util.List;
import server.com.tz.admin.book.model.Book;
/**
 * 企业
 * @author auto
 * @date 2021-10-13 16:11:20
 * @dodifyNote
 * @version 1.0
 */
public interface BookDao {

	public void addBook(Book book);
	
	public void setBook(Book book);
	
	public List<Book> listBook(Book book);
	
	public void removeBook(Book book);
	
}
