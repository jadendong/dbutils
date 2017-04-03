package top.getcode.dbutils.DBUtils;

import oracle.jdbc.OraclePreparedStatement;
import oracle.jdbc.OracleTypes;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import top.getcode.dbutils.entity.Account;
import top.getcode.dbutils.util.JDBCUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;

/**
 * 参考自 http://www.cnblogs.com/xdp-gacl/p/4007225.html
 * 
 * 一、commons-dbutils简介:　
 * commons-dbutils 是 Apache 组织提供的一个开源
 * JDBC工具类库，它是对JDBC的简单封装，学习成本极低，并且使用dbutils能极大简化jdbc编码的工作量，同时也不会影响程序的性能。
 * 因此dbutils成为很多不喜欢hibernate的公司的首选。
 * 
   * 　commons-dbutilsAPI介绍：
 * 
 * org.apache.commons.dbutils.QueryRunner
 * org.apache.commons.dbutils.ResultSetHandler 　　工具类
 * 
 * org.apache.commons.dbutils.DbUtils
 * 
 * QueryRunner类使用讲解:
 * 
   * 　该类简单化了SQL查询，它与ResultSetHandler组合在一起使用可以完成大部分的数据库操作，能够大大减少编码量。
   * 　QueryRunner类提供了两个构造方法：
 * 
 * 1. 默认的构造方法 
 * 2. 需要一个 javax.sql.DataSource 来作参数的构造方法。
 * 
 * QueryRunner类的主要方法:
 * 
 * public Object query(Connection conn, String sql, Object[] params,
 * ResultSetHandler rsh) throws SQLException:
 * 执行一个查询操作，在这个查询中，对象数组中的每个元素值被用来作为查询语句的置换参数。该方法会自行处理
 * PreparedStatement 和 ResultSet 的创建和关闭。 　　
 * 
 * public Object query(String sql,Object[] params, ResultSetHandler rsh)
 * throws SQLException:　
 * 几乎与第一种方法一样；唯一的不同在于它不将数据库连接提供给方法，并且它是从提供给构造方法的数据源(DataSource)
 * 或使用的setDataSource 方法中重新获得 Connection。 　　
 * 
 * public Object query(Connection conn,
 * String sql, ResultSetHandler rsh) throws SQLException : 
 * 执行一个不需要置换参数的查询操作。
 * 
 * public int update(Connection conn, String sql, Object[] params) throws
 * SQLException: 
 * 用来执行一个更新（插入、更新或删除）操作。 　　
 * 
 * public int update(Connection conn,String sql) throws SQLException:
 * 用来执行一个不需要置换参数的更新操作。
 */
public class QueryRunnerCRUD {

	// 将数据源传递给QueryRunner，QueryRunner内部通过数据源获取数据库连接
	QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

	/**
	 * 增加
	 * 发现一个现象:
	 * 用这个方法插入的记录,会默认插到数据库的第一条,不像通常插入会在最后一条
	 * 不知道是怎么回事...
	 */
	public void add() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

		String sql = "insert into account values(account_seq.nextval,?,?,?)";

		Object[] params = { "小白2", 52.66,
				new Date(new java.util.Date().getTime()) };

		qr.update(sql, params);
	}

	/**
	 * 或得返回的主键值
	 * 出BUG咯===
	 * @throws SQLException
	 */
	public void add2() throws SQLException {
		QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
//		String sql="INSERT INTO ACCOUNT VALUES(ACCOUNT_SEQ.NEXTVAL,?,?,SYSDATE)";
		String sql2="INSERT INTO TEST VALUES(6,'小黑',sysdate)";

		BigDecimal o= (BigDecimal) qr.insert(sql2,new ScalarHandler());
		System.out.println(o);
	}

	/**
	 * 上面的那个出BUG了,让我换个法子
	 * !!!!!还是不行
	 * cannot be cast to oracle.jdbc.OraclePreparedStatement
	 */
	public int fixAdd2() throws SQLException {
		Connection conn=JDBCUtils.getConnection();
		String sql="BEGIN INSERT INTO ACCOUNT VALUES(ACCOUNT_SEQ.NEXTVAL,?,?,SYSDATE) RETRUNING id into ?;END ;";
		OraclePreparedStatement pstt= (OraclePreparedStatement) conn.prepareStatement(sql);
		pstt.setString(1,"呵呵姐");
		pstt.setDouble(2,52.33);

		pstt.registerReturnParameter(3, OracleTypes.INTEGER);

		pstt.executeUpdate();

		ResultSet rs=pstt.getReturnResultSet();
		int id=-1;
		if (rs.next()){
			id=rs.getInt(1);
		}
		return id;
	}

	/**
	 * 搞不出来就不睡觉~~~~
	 * 好吧,这样成功了,但是为什么第一个方法失败了.......
	 */
	public void fix2Add2() throws SQLException {
		String sql="INSERT INTO ACCOUNT VALUES(ACCOUNT_SEQ.NEXTVAL,?,?,SYSDATE)";
		Connection conn=JDBCUtils.getConnection();
//		PreparedStatement ps=conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		// 获取指定ID
		String generatedColumns[] = { "ID" };
		PreparedStatement ps=conn.prepareStatement(sql,generatedColumns);

		ps.setString(1,"大哥的女人");
		ps.setDouble(2,233.666);

		int i=ps.executeUpdate();

		// 检索对象生产的键
		ResultSet rs=ps.getGeneratedKeys();
		if (rs.next()){
			System.out.println("主键是: "+rs.getObject(1));
		}
		JDBCUtils.close();
	}
	/**
	 * 删除
	 * 
	 */

	public void delete() throws SQLException {
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());

		String sql = "DELETE FROM ACCOUNT WHERE id=?";

		qr.update(sql, new Object[] { 5 });
	}

	/**
	 * 更新
	 */
	public int update() {
		int r = -1;
		String sql = "update ACCOUNT set name=? where id=?";
		Object params[] = { "dd2", 4 };
		try {
			r = qr.update(sql, params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return r;
	}

	/**
	 * 查找某个用户
	 */
	@SuppressWarnings("deprecation")
	public void find() throws SQLException {
		String sql = "SELECT *　FROM ACCOUNT WHERE ID=?";

		Account account = (Account) qr.query(sql, new Object[] { 4 },
				new BeanHandler<Account>(Account.class));

		System.out.println(account);
	}

	/**
	 * 得到所有
	 */
	public void getAll() throws SQLException {
		String sql = "SELECT * FROM  ACCOUNT";
		List<Account> list = qr.query(sql, new BeanListHandler<Account>(
				Account.class));
		System.out.println(list);
	}

	/**
	 * 批处理
	 */
	public void batch() throws SQLException {
		String sql = "insert into account values(account_seq.nextval,?,?,sysdate)";
		Object[][] params = new Object[10][];
		for (int i = 0; i < params.length; i++) {
			params[i] = new Object[] { "大宝剑" + i, 123.00 - Math.random() * 50 };
		}

		// 执行批处理
		int[] i = qr.batch(sql, params);
		/**
		 * 查看批处理的返回值 是int[]保存的的数组 于是猜测是以数组索引对应的上面的二维数组的语句,而值则对应该条语句是否成功.
		 * 令人不解的是,成功后的值不是1,而是-2 ....
		 */
		for (int j = 0; j < i.length; j++) {
			System.out.println(i[j]);
			System.out.println("第" + (j + 1) + "条"
					+ (i[j] == -2 ? "成功执行" : "执行失败"));
		}
	}
}
