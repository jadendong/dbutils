package top.getcode.dbutils.DBUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import top.getcode.dbutils.entity.Account;
import top.getcode.dbutils.util.JDBCUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * ResultSetHandler接口使用讲解 　　
 * 该接口用于处理java.sql.ResultSet，将数据按要求转换为另一种形式。
   * 　ResultSetHandler接口提供了一个单独的方法：Object handle (java.sql.ResultSet .rs)
 * 
 * ResultSetHandler接口的实现类
 * 
 * ArrayHandler：把结果集中的第一行数据转成对象数组。
 * ArrayListHandler：把结果集中的每一行数据都转成一个数组，再存放到List中。
 * BeanHandler：将结果集中的第一行数据封装到一个对应的JavaBean实例中。
 * BeanListHandler：将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里。
 * ColumnListHandler：将结果集中某一列的数据存放到List中。
 * KeyedHandler(name)：将结果集中的每一行数据都封装到一个Map里，再把这些map再存到一个map里，其key为指定的key。
 * MapHandler：将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
 * MapListHandler：将结果集中的每一行数据都封装到一个Map里，然后再存放到List
 */

//  dbutils各种类型的处理器
public class ResultSetHandler {
	QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
	
	// 1.把结果集中的第一行数据转成对象数组
	public void testArrayHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		Object[] result=qr.query(sql,new ArrayHandler());
		
		System.out.println(Arrays.asList(result)); // toString
	}
	
	// 2. 把结果集中的每一行数据都转成一个数组，再存放到List中
	public void testArryListHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		List<Object[]> list=qr.query(sql, new ArrayListHandler());
		
		for (Object[] objects : list) {
			System.out.println(Arrays.asList(objects));
		}
	}
	
	// 3. 将结果集中的第一行数据封装到一个对应的JavaBean实例中。
	
	public void testBeanHandler() throws SQLException{
		String sql="SELECT *　FROM ACCOUNT";
		
		Account account=qr.query(sql, new BeanHandler<Account>(Account.class));
		
		System.out.println(account);
	}
	
	//4. 将结果集中的每一行数据都封装到一个对应的JavaBean实例中，存放到List里
	public void tsetBeanListHandler() throws SQLException{
//		String sql="SELECT * FROM ACCOUNT";
		String sql2="SELECT * FROM ACCOUNT WHERE BALANCE>? AND BALANCE<?";
//		List<Account> accounts=qr.query(sql, new BeanListHandler<Account>(Account.class));
		List<Account> accounts2=qr.query(sql2, new BeanListHandler<Account>(Account.class),1000,2000);
		System.out.println(accounts2);
	}
	
	//5. 将结果集中某一列的数据存放到List中
	public void testColumnListHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		List<String> list=qr.query(sql, new ColumnListHandler<String>("name"));
		
		System.out.println(list);
	}
	
	// 6.将结果集中的每一行数据都封装到一个Map里，再把这些map再存到一个map里，其key为指定的key。
	@SuppressWarnings("unchecked")
	public void testKeyedHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		Map<Integer,Map> map=(Map)qr.query(sql, new KeyedHandler<Integer>("id"));
		
		for (Map.Entry<Integer, Map> me : map.entrySet()) {
			// 这一行直接这么写,会报 java.math.BigDecimal cannot be cast to java.lang.Integer
			// 我也不知道为什么,换种方法
//			int id=me.getKey();
			// 所以,我改成下面写法
			Object o=me.getKey();
			int id=Integer.parseInt(o.toString());
			
			Map<String,Object> innerMap=me.getValue();
			
			System.out.println(id+":");
			for (Map.Entry<String, Object> innerme : innerMap.entrySet()) {
				String columnName=innerme.getKey();
				Object value=innerme.getValue();
				System.out.println(columnName+"="+value);
			}
			System.out.println("---------------------------");
		}
	}
	
	// 7. 将结果集中的第一行数据封装到一个Map里，key是列名，value就是对应的值。
	public void testMapHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		Map<String,Object> map=qr.query(sql, new MapHandler());
		
		for (Map.Entry<String, Object> me : map.entrySet()) {
			System.out.println(me.getKey()+"="+me.getValue());
		}
	}
	
	//8. 将结果集中的每一行数据都封装到一个Map里，然后再存放到List
	@SuppressWarnings("unchecked")
	public void testMapListHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		List<Map> list=(List)qr.query(sql, new MapListHandler());
		
		for (Map<String,Object> map : list) {
			for (Map.Entry<String, Object> me: map.entrySet()) {
				
				System.out.println(me.getKey()+"="+me.getValue());
			}
		}
	}
	
	// 9.用于获取结果集中第一行某列的数据并转换成 T 表示的实际对象。
	public void testScalarHandler() throws SQLException{
		String sql="SELECT COUNT(*) FROM ACCOUNT";
		
		// ScalarHandler 的参数为空或null时，返回第一行第一列的数据
		Object count=qr.query(sql, new ScalarHandler<Integer>());
		System.out.println(count);
	}
	
	// 10. 将查询结果的每一行数据，封装到Account对象，再存入map集合中（key==列名，value==列值）
	
	public void testBeanMapHandler() throws SQLException{
		String sql="SELECT * FROM ACCOUNT";
		
		Map<Integer,Account> maps=qr.query(sql, new BeanMapHandler<Integer, Account>(Account.class));
		
		for ( Map.Entry<Integer, Account> me: maps.entrySet()) {
			System.out.println("key=="+me.getKey());
			System.out.println("value=="+me.getValue());
		}
	}
}
