package autotemplate.com.tz.order.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业
 * @author auto
 * @date 2021-10-13 9:26:56
 * @dodifyNote
 * @version 1.0
 */
public class Order implements Serializable{
	
	private static final long serialVersionUID = 7989060230279981578L;
	/*主键*/
	private Long id;
	/*排序号*/
	private Integer sort;
	/*发布状态0未发布1发布*/
	private Integer status;
	/*删除状态0未删除1删除*/
	private Integer isDelete;
	/*创建时间*/
	private Date createTime;
	/*更新时间*/
	private Date updateTime;
	
	public Order () {
		
	}
	public Order (Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
   
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
	
}
