package com.dm.atform.util;

public class QueryOperators {  
    public static final String OR = "$or";  
    public static final String AND = "$and";  
  
    public static final String GT = "$gt";  
    public static final String GTE = "$gte";  
    public static final String LT = "$lt";  
    public static final String LTE = "$lte";  
  
    public static final String NE = "$ne";  
    public static final String IN = "$in";  
    public static final String NIN = "$nin";  
    public static final String MOD = "$mod";  
    public static final String ALL = "$all";  
    public static final String SIZE = "$size";  
    public static final String EXISTS = "$exists";  
    public static final String ELEM_MATCH = "$elemMatch";  
  
    // (to be implemented in QueryBuilder)  
    public static final String WHERE = "$where";  
    public static final String NOR = "$nor";  
    public static final String TYPE = "$type";  
    public static final String NOT = "$not";  
  
    // geo operators  
    public static final String WITHIN = "$within";  
    public static final String NEAR = "$near";  
    public static final String NEAR_SPHERE = "$nearSphere";  
    public static final String BOX = "$box";  
    public static final String CENTER = "$center";  
    public static final String POLYGON = "$polygon";  
    public static final String CENTER_SPHERE = "$centerSphere";  
    // (to be implemented in QueryBuilder)  
    public static final String MAX_DISTANCE = "$maxDistance";  
    public static final String UNIQUE_DOCS = "$uniqueDocs";  
  
  
    // meta query operators (to be implemented in QueryBuilder)  
    public static final String RETURN_KEY = "$returnKey";  
    public static final String MAX_SCAN = "$maxScan";  
    public static final String ORDER_BY = "$orderby";  
    public static final String EXPLAIN =  "$explain";  
    public static final String SNAPSHOT = "$snapshot";  
    public static final String MIN = "$min";  
    public static final String MAX = "$max";  
    public static final String SHOW_DISK_LOC = "$showDiskLoc";  
    public static final String HINT = "$hint";  
    public static final String COMMENT = "$comment";  
}  
