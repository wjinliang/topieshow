package com.dm.atform.model;

import java.util.Date;

public class AtTable {
//	public static final String deletes="9";
//	public static final String forbiddens="0";
//	public static final String defaults="1";
	
	private String id;

	private String tableName;//表名

	private String status;//0 新建(禁用) 1 可用 9 删除

	private String sortField;//默认排序

	private String whereField;//默认过滤条件

	private String info;//数据介绍

	private Date createDate;//创建时间

	private String createUser;//创建用户

	private String gridName;//数据名称

	private String idField;//主键ID

	private String dataTime;//数据采集时间
	
	private String dataUpdateTime;//数据更新时间
	
	private String origin;//数据来源
	
	private String org;//所属部门
	
	private Integer dataCount;//s数据总条数

	private String pId;
	private Integer seq;
	private String type;//1分类  0数据
	private Integer visitCount;
	
	private Boolean isInsert;
	private Boolean isUpdate;
	private Boolean isDelete;
	private Boolean isDetail;
	private String set;
	private String showDataQuery;
	
	

	public String getSet() {
			String s = "";
			if(isInsert!=null && isInsert)s+="isInsert,";
			if(isUpdate!=null && isUpdate)s+="isUpdate,";
			if(isDelete!=null && isDelete)s+="isDelete,";
			if(isDetail!=null && isDetail)s+="isDetail";
			return s;
	}

	public void setSet(String set) {
		this.set = set;
	}

	public Boolean getIsInsert() {
		if(isInsert==null)
			if(set!=null && set.contains("isInsert"))
				return true;else return false;
		return isInsert;
	}

	public void setIsInsert(Boolean isInsert) {
		this.isInsert = isInsert;
	}

	public Boolean getIsUpdate() {
		if(isUpdate==null)
			if(set!=null && set.contains("isUpdate"))
				return true;else return false;
		return isUpdate;
	}

	public void setIsUpdate(Boolean isUpdate) {
		this.isUpdate = isUpdate;
	}

	public Boolean getIsDelete() {
		if(isDelete==null)
			if(set!=null && set.contains("isDelete"))
				return true;else return false;
		return isDelete == null ? false : isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public Boolean getIsDetail() {
		if(isDetail==null)
			if(set!=null && set.contains("isDetail"))
				return true;else return false;
		return isDetail == null ? true : isDetail;
	}

	public void setIsDetail(Boolean isDetail) {
		this.isDetail = isDetail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getOrg() {
		return org;
	}

	public void setOrg(String org) {
		this.org = org;
	}

	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName == null ? null : tableName.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField == null ? null : sortField.trim();
	}

	public String getWhereField() {
		return whereField;
	}

	public void setWhereField(String whereField) {
		this.whereField = whereField == null ? null : whereField.trim();
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info == null ? null : info.trim();
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser == null ? null : createUser.trim();
	}

	public String getGridName() {
		return gridName;
	}

	public void setGridName(String gridName) {
		this.gridName = gridName == null ? null : gridName.trim();
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataTime() {
		return dataTime;
	}

	public void setDataTime(String dataTime) {
		this.dataTime = dataTime;
	}

	
	public String getDataUpdateTime() {
		return dataUpdateTime;
	}

	public void setDataUpdateTime(String dataUpdateTime) {
		this.dataUpdateTime = dataUpdateTime;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getVisitCount() {
		return visitCount;
	}

	public void setVisitCount(Integer visitCount) {
		this.visitCount = visitCount;
	}

	public String getShowDataQuery() {
		return showDataQuery;
	}

	public void setShowDataQuery(String showDataQuery) {
		this.showDataQuery = showDataQuery;
	}

	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}
	
	

}