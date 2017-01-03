package com.dm.atform.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dm.atform.model.AtTable;
import com.dm.atform.service.AtTableService;
import com.dm.atform.service.MongoService;
import com.dm.platform.util.PageConvertUtil;
import com.dm.platform.util.ResponseUtil;
import com.dm.platform.util.SqlParam;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("table")
public class TableController {
	
	@Autowired
	private AtTableService tableService;
	@Autowired
	private MongoService mongo;
	@RequestMapping("/page")
	public String page(Model model) {
		return "/atform/table/page";
	}
	@RequestMapping("list")
	@ResponseBody
	public Object list(
			@RequestParam(value = "pageNum", required = false,defaultValue="1") Integer pageNum,
			@RequestParam(value = "pageSize", required = false,defaultValue="10") Integer pageSize,
			@RequestParam(value="sort" ,required = false,defaultValue="createDate_desc") String sort,
			AtTable atTable){
		Map map =new SqlParam().autoParam(atTable, sort,pageNum,pageSize);
		PageInfo<AtTable> page = this.tableService.findByArg(map);
		return PageConvertUtil.grid(page);
	}
	@RequestMapping("load")
	@ResponseBody
	public Object load(String id){
		return this.tableService.findOne(id);
	}
	@RequestMapping("loadCollections")
	@ResponseBody
	public Object loadCollections(){
		return this.mongo.loadCollections();
	}
	@RequestMapping("insertOrUpdate")
	@ResponseBody
	public Object addOrUpdate(AtTable atTable){
		this.tableService.saveOrUpdate(atTable);
		return ResponseUtil.success();
	}
	@RequestMapping("delete")
	@ResponseBody
	public Object delte(AtTable atTable){
		this.tableService.delete(atTable.getId());
		return ResponseUtil.success();
	}
	@RequestMapping("tree")
	@ResponseBody
	public Object tree(@RequestParam(value="tableId",required=false) String tableId,@RequestParam(value="status",required=false) String status){
		return this.tableService.getTree(tableId,status);
	}
}
