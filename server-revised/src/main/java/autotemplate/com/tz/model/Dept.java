package autotemplate.com.tz.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 企业
 * @author auto
 * @date 2020-5-25 15:14:57
 * @dodifyNote
 * @version 1.0
 */
//@Entity
//@Table(name = "tm_dept")
public class Dept implements Serializable{

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

	public Dept () {

	}
	public Dept (Long id) {
		this.id = id;
	}

	//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	//	@Column(name= "sort")
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	//	@Column(name= "status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	//	@Column(name= "is_delete , length = 1")
	public Integer getIsDelete() {
		return isDelete;
	}
	public void setIsDelete(Integer isDelete) {
		this.isDelete = isDelete;
	}

	//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "update_time")
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}



}
