package autotemplate.com.tz.dao;

import java.util.List;
import autotemplate.com.tz.model.Dept;
/**
 * 企业
 * @author auto
 * @date 2020-5-25 15:14:57
 * @dodifyNote
 * @version 1.0
 */
public interface DeptDao {

	public void addDept(Dept dept);

	public void setDept(Dept dept);

	public List<Dept> listDept(Dept dept);

	public void removeDept(Dept dept);

}
