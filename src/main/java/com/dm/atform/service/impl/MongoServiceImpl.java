package com.dm.atform.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.dm.atform.model.AtField;
import com.dm.atform.model.AtTable;
import com.dm.atform.service.MongoService;
import com.dm.atform.sqldao.AtFieldMapper;
import com.dm.platform.util.UUIDUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.Mongo;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteConcern;

@Service
public class MongoServiceImpl implements MongoService {
	@Autowired
	private MongoTemplate template;
	@Autowired
	private AtFieldMapper fieldMapper;
	private Collection staticWords;

	{
		staticWords = new ArrayList();
		staticWords.add("tableId");
		staticWords.add("pageSize");
		staticWords.add("pageNum");
		staticWords.add("sort");
	}

	private DBCollection getColl(AtTable atTable) {
		return template.getCollection(atTable.getTableName());
	}

	/**
	 * 获取所有数据库实例
	 */
	/*
	 * public void testGetDBS(AtTable atTable) {
	 * template.getDb().getCollectionNames(); List<String> dbnames =
	 * MongoUtil.getMong().getDatabaseNames(); for (String dbname : dbnames) {
	 * System.out.println("dbname:" + dbname); } }
	 */

	/**
	 * 查询所有表名
	 */
	public void getAllCollections(AtTable atTable) {
		Set<String> colls = template.getDb().getCollectionNames();
		for (String s : colls) {
			System.out.println(s);
		}
	}

	/**
	 * 添加一条记录
	 */

	public void addData(AtTable atTable, Map<String, Object> map) {
		DBCollection coll = getColl(atTable);
		BasicDBObject doc = new BasicDBObject();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			doc.put(entry.getKey(), entry.getValue());
			System.out.println("key= " + entry.getKey() + " and value= "
					+ entry.getValue());
		}
		coll.insert(doc);
		// 设定write concern，以便操作失败时得到提示
		coll.setWriteConcern(WriteConcern.SAFE);
	}

	/**
	 * 创建索引
	 */
	public void createIndex(AtTable atTable, String IndexName) {
		getColl(atTable).createIndex(new BasicDBObject(IndexName, 1));
	}

	/**
	 * 获取索引信息
	 */
	public void getIndexInfo(AtTable atTable) {
		List<DBObject> list = getColl(atTable).getIndexInfo();
		for (DBObject o : list) {
			System.out.println(o);
		}
	}

	/**
	 * 添加多条记录
	 */
	public void addMultiData(AtTable atTable) {
		for (int i = 0; i < 100; i++) {
			getColl(atTable).insert(new BasicDBObject().append("i", i));
		}

		List<DBObject> docs = new ArrayList<DBObject>();
		for (int i = 0; i < 50; i++) {
			docs.add(new BasicDBObject().append("i", i));
		}
		getColl(atTable).insert(docs);
		// 设定write concern，以便操作失败时得到提示
		// getColl(atTable).setWriteConcern(WriteConcern.SAFE);
	}


	/**
	 * 获取表中所有记录条数
	 */
	public void count(AtTable atTable) {
		System.out.println(getColl(atTable).getCount());
		System.out.println(getColl(atTable).count());
	}

	/**
	 * 获取查询结果集的记录数
	 */
	public void getCount(AtTable atTable) {
		DBObject query = new BasicDBObject("name", "a");
		long count = getColl(atTable).count(query);
		System.out.println(count);
	}

	/**
	 * 查询所有结果
	 */
	public void getAllDocuments(AtTable atTable) {
		DBCursor cursor = getColl(atTable).find();
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	/**
	 * 按照一个条件查询
	 */
	public void queryByConditionOne(AtTable atTable) {
		BasicDBObject query = new BasicDBObject();
		query.put("name", "MongoDB");
		DBCursor cursor = getColl(atTable).find(query);

		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	/**
	 * AND多条件查询,等于
	 */
	public List queryMulti(AtTable atTable, Map<String, Object> map) {
		BasicDBObject query = new BasicDBObject();
		for (Entry<String, Object> i : map.entrySet()) {
			if (isChinese(i.getValue().toString())) {
				Pattern john = Pattern.compile(i.getValue() + "?n");
				query.put(i.getKey(), john);
			} else {
				query.put(i.getKey(), i.getValue());
			}
		}
		DBCursor cursor = getColl(atTable).find(query);
		List result = new ArrayList();
		try {
			while (cursor.hasNext()) {
				// System.out.println(cursor.next());
				result.add(cursor.next());

			}
		} finally {
			cursor.close();
			return result;
		}
	}

	/**
	 * 区间查询 select * from table where i >50
	 */
	public void queryMulti2(AtTable atTable) {
		BasicDBObject query = new BasicDBObject();
		query = new BasicDBObject();
		query.put("i", new BasicDBObject("$gt", 50)); // e.g. find all where i >
		DBCursor cursor = getColl(atTable).find(query);
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	/**
	 * 区间查询 select * from table where 20 < i <= 30 //比较符 //"$gt"： 大于
	 * //"$gte"：大于等于 //"$lt"： 小于 //"$lte"：小于等于 //"$in"： 包含
	 */
	public void queryMulti3(AtTable atTable) {
		BasicDBObject query = new BasicDBObject();
		query = new BasicDBObject();

		query.put("i", new BasicDBObject("$gt", 20).append("$lte", 30));
		DBCursor cursor = getColl(atTable).find(query);
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	/**
	 * 组合in和and select * from test_Table where (a=5 or b=6) and (c=5 or d = 6)
	 */
	public void queryMulti4(AtTable atTable) {
		BasicDBObject query11 = new BasicDBObject();
		query11.put("a", 1);
		BasicDBObject query12 = new BasicDBObject();
		query12.put("b", 2);
		List<BasicDBObject> orQueryList1 = new ArrayList<BasicDBObject>();
		orQueryList1.add(query11);
		orQueryList1.add(query12);
		BasicDBObject orQuery1 = new BasicDBObject("$or", orQueryList1);

		BasicDBObject query21 = new BasicDBObject();
		query21.put("c", 5);
		BasicDBObject query22 = new BasicDBObject();
		query22.put("d", 6);
		List<BasicDBObject> orQueryList2 = new ArrayList<BasicDBObject>();
		orQueryList2.add(query21);
		orQueryList2.add(query22);
		BasicDBObject orQuery2 = new BasicDBObject("$or", orQueryList2);

		List<BasicDBObject> orQueryCombinationList = new ArrayList<BasicDBObject>();
		orQueryCombinationList.add(orQuery1);
		orQueryCombinationList.add(orQuery2);

		BasicDBObject finalQuery = new BasicDBObject("$and",
				orQueryCombinationList);
		DBCursor cursor = getColl(atTable).find(finalQuery);
	}

	/**
	 * IN查询 if i need to query name in (a,b); just use { name : { $in : ['a',
	 * 'b'] } } select * from things where name='a' or name='b'
	 * 
	 * @param coll
	 */
	public void queryIn(AtTable atTable) {
		BasicDBList values = new BasicDBList();
		values.add("a");
		values.add("b");
		BasicDBObject in = new BasicDBObject("$in", values);
		DBCursor cursor = getColl(atTable).find(new BasicDBObject("name", in));
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	/**
	 * 或查询 select * from table where name = '12' or title = 'p'
	 * 
	 * @param coll
	 */
	public void queryOr(AtTable atTable) {
		QueryBuilder query = new QueryBuilder();
		query.or(new BasicDBObject("name", 12), new BasicDBObject("title", "p"));
		DBCursor cursor = getColl(atTable).find(query.get()).addSpecial(
				"$returnKey", "");
		try {
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
		} finally {
			cursor.close();
		}
	}

	public void mapReduce() throws UnknownHostException {
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("zhongsou_ad");
		/***
		 * book1 = {name : "Understanding JAVA", pages : 100} book2 = {name :
		 * "Understanding JSON", pages : 200} db.books.save(book1)
		 * db.books.save(book2) book = {name : "Understanding XML", pages : 300}
		 * db.books.save(book) book = {name : "Understanding Web Services",
		 * pages : 400} db.books.save(book) book = {name :
		 * "Understanding Axis2", pages : 150} db.books.save(book)
		 * 
		 * var map = function(AtTable atTable) { var category; if ( this.pages
		 * >= 250 ) category = 'Big Books'; else category = "Small Books";
		 * emit(category, {name: this.name}); }; var reduce = function(key,
		 * values) { var sum = 0; values.forEach(function(doc) { sum += 1; });
		 * return {books: sum}; }; var count = db.books.mapReduce(map, reduce,
		 * {out: "book_results"});
		 */
		try {

			DBCollection books = db.getCollection("books");

			BasicDBObject book = new BasicDBObject();
			book.put("name", "Understanding JAVA");
			book.put("pages", 100);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding JSON");
			book.put("pages", 200);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding XML");
			book.put("pages", 300);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding Web Services");
			book.put("pages", 400);
			books.insert(book);

			book = new BasicDBObject();
			book.put("name", "Understanding Axis2");
			book.put("pages", 150);
			books.insert(book);

			String map = "function(AtTable atTable) { " + "var category; "
					+ "if ( this.pages >= 250 ) " + "category = 'Big Books'; "
					+ "else " + "category = 'Small Books'; "
					+ "emit(category, {name: this.name});}";

			String reduce = "function(key, values) { " + "var sum = 0; "
					+ "values.forEach(function(doc) { " + "sum += 1; " + "}); "
					+ "return {books: sum};} ";

			MapReduceCommand cmd = new MapReduceCommand(books, map, reduce,
					null, MapReduceCommand.OutputType.INLINE, null);

			MapReduceOutput out = books.mapReduce(cmd);

			for (DBObject o : out.results()) {
				System.out.println(o.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void GroupByManyField() throws UnknownHostException {
		// 此方法没有运行成功
		Mongo mongo = new Mongo("localhost", 27017);
		DB db = mongo.getDB("libary");
		DBCollection books = db.getCollection("books");
		BasicDBObject groupKeys = new BasicDBObject();
		groupKeys.put("total", new BasicDBObject("$sum", "pages"));

		BasicDBObject condition = new BasicDBObject();
		condition.append("pages", new BasicDBObject().put("$gt", 0));

		String reduce = "function(key, values) { " + "var sum = 0; "
				+ "values.forEach(function(doc) { " + "sum += 1; " + "}); "
				+ "return {books: sum};} ";
		/**
		 * BasicDBList basicDBList =
		 * (BasicDBList)db.getCollection("mongodb中集合编码或者编码") .group(DBObject
		 * key, --分组字段，即group by的字段 DBObject cond, --查询中where条件 DBObject
		 * initial, --初始化各字段的值 String reduce, --每个分组都需要执行的Function String finial
		 * --终结Funciton对结果进行最终的处理
		 */
		DBObject obj = books.group(groupKeys, condition, new BasicDBObject(),
				reduce);
		System.out.println(obj);

		AggregationOutput ouput = books.aggregate(new BasicDBObject("$group",
				groupKeys));
		System.out.println(ouput.getCommandResult());
		System.out.println(books.find(new BasicDBObject("$group", groupKeys)));
	}

	/**
	 * 分页查询
	 */
	public void pageQuery(AtTable atTable) {
		DBCursor cursor = getColl(atTable).find().skip(0).limit(10);
		while (cursor.hasNext()) {
			System.out.println(cursor.next());
		}
	}

	/**
	 * 模糊查询
	 */
	public void likeQuery(AtTable atTable) {
		Pattern john = Pattern.compile("joh?n");
		BasicDBObject query = new BasicDBObject("name", john);

		// finds all people with "name" matching /joh?n/i
		DBCursor cursor = getColl(atTable).find(query);
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION) {
			return true;
		}
		return false;
	}

	// 完整的判断中文汉字和符号
	public static boolean isChinese(String strName) {
		char[] ch = strName.toCharArray();
		for (int i = 0; i < ch.length; i++) {
			char c = ch[i];
			if (isChinese(c)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * AND多条件查询,等于
	 */
	@Override
	public PageInfo queryMulti(AtTable atTable, Integer pageNum,
			Integer pageSize, Map<String, Object> map, String sort) {
		DBCollection coll = getColl(atTable);
		BasicDBObject query = new BasicDBObject();
		for (Entry<String, Object> i : map.entrySet()) {
			String key = i.getKey();
			if (staticWords.contains(key)) {
				continue;
			}
			Pattern john = Pattern.compile(i.getValue() + "+");
			query.put(key, john);
			/*
			 * if (isChinese(i.getValue().toString())) { Pattern john =
			 * Pattern.compile(i.getValue() + "+"); query.put(key, john); } else
			 * { query.put(key, i.getValue()); }
			 */
		}
		DBObject fields = new BasicDBObject();
		Map searchMap = new HashMap();
		AtField f1 = new AtField();
		f1.setTableId(atTable.getId());
		searchMap.put("model",f1 );
		searchMap.put("sort", "a_seq asc");
		List<AtField> fs = this.fieldMapper.selectByArgMap(searchMap);
		DBCursor cursor ;
		long totalCount = coll.count(query);
		BasicDBObject ord = new BasicDBObject();
		if (StringUtils.hasText(sort)) {
			String f = sort.substring(0, sort.lastIndexOf("_"));
			int ad = sort.substring(sort.lastIndexOf("_") + 1).equals("asc") ? 1
					: -1;
			ord.put(f, ad);
		}
		int skip = (pageNum - 1) * pageSize;
		if(fs.size()!=0){
			for (AtField f : fs) {
				fields.put(f.getaField(), "1");
			}
			fields.put(atTable.getIdField(), "1");
			 cursor = coll.find(query, fields).skip(skip)
					.sort(ord).limit(pageSize);
		}else{
			cursor = coll.find(query).skip(skip).sort(ord).limit(pageSize);
		}
		List result = new ArrayList();
		try {
			while (cursor.hasNext()) {
				// System.out.println(cursor.next());
				DBObject o = cursor.next();
				/*
				 * String id = o.get("_id").toString(); o.put("id", id);
				 */
				result.add(o);

			}
		} finally {
			cursor.close();
			Page p = new Page();
			p.addAll(result);
			p.setPageNum(pageNum);
			p.setPageSize(pageSize);
			p.setTotal(totalCount);
			PageInfo page = new PageInfo(p);
			return page;
		}
	}

	@Override
	public Object findOne(AtTable atTable, String id) {
		DBCollection coll = getColl(atTable);
		DBObject dbo = new BasicDBObject();
		dbo.put(atTable.getIdField(), id);
		DBObject o = coll.findOne(dbo);
		if(o==null){
			try{
			Integer i = Integer.valueOf(id);
			dbo.put(atTable.getIdField(), i);
			o = coll.findOne(dbo);
			}catch(Exception e){
				
			}
		}
		return o;
	}
	@Override
	public List<Map> findOne(AtTable atTable) {
		List<Map> list = new ArrayList();
		if(atTable.getTableName()==null) return list;
		DBCollection coll = getColl(atTable);
		DBObject o = coll.findOne();
		if(o!=null){
			for(String s:o.keySet()){
				if(s.equals("_id")) continue;
				Map map = new HashMap();
				map.put("label", s);
				map.put("type", "text");
				map.put("name", s);
				map.put("id", s); 
				map.put("title", s);
				map.put("field", s);
				list.add(map);
			}
		}
		return list;
	}

	@Override
	public void saveOrUpdate(AtTable atTable, Map record) {
		DBCollection coll = getColl(atTable);
		String idField = atTable.getIdField();
		BasicDBObject query = new BasicDBObject();
		if (record.get(idField) == null
				|| record.get(idField).toString().equals("")) {
			record.put(idField, UUIDUtils.getUUID32());
			DBObject stuFound = map2DBObject(record);
			coll.insert(stuFound);
			return;
		}
		query.put(idField, record.get(idField).toString());
		DBObject stuFound = coll.findOne(query);
		if (stuFound == null) {
			stuFound = map2DBObject(record);
			coll.insert(stuFound);
		} else {
			stuFound = map2DBObject(record);
			coll.update(query, stuFound);
		}
	}

	private DBObject map2DBObject(Map<String, Object> record) {
		BasicDBObject doc = new BasicDBObject();
		for (Map.Entry<String, Object> entry : record.entrySet()) {
			doc.put(entry.getKey(), entry.getValue());
		}
		return doc;
	}

	@Override
	public void delete(AtTable atTable, String id) {
		BasicDBObject query = new BasicDBObject();
		query.put(atTable.getIdField(), id);
		// 找到并且删除，并返回删除的对象
		DBObject removeObj = getColl(atTable).findAndRemove(query);
		//System.out.println(removeObj);
	}

	@Override
	public Object loadCollections() {
		List<Map> list = new ArrayList<Map>();
		Set<String> colls = template.getDb().getCollectionNames();
		for (String s : colls) {
			Map map = new HashMap();
			map.put("text", s);
			map.put("value", s);
			list.add(map);
		}
		return list;
	}
}
