package autotemplate.com.tz.order.dao;

import java.util.List;
import autotemplate.com.tz.order.model.Order;
/**
 * 企业
 * @author auto
 * @date 2021-10-13 9:26:56
 * @dodifyNote
 * @version 1.0
 */
public interface OrderDao {

	public void addOrder(Order order);
	
	public void setOrder(Order order);
	
	public List<Order> listOrder(Order order);
	
	public void removeOrder(Order order);
	
}
