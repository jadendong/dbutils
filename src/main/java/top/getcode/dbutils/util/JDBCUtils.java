package top.getcode.dbutils.util;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtils {
	private static ComboPooledDataSource ds = null;
	static {
		try {
			ds = new ComboPooledDataSource();
		} catch (Exception e) {
			throw new ExceptionInInitializerError("数据源获取失败！");
		}

	}

	// 使用ThreadLocal存储当前线程中的Connection对象
	private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

	/**
	 * 获取数据源
	 * 
	 * @return
	 */
	public static DataSource getDataSource() {

		return ds;
	}

	/**
	 * 从数据源中获取数据库连接
	 * 
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		// 从当前线程中获取Connection
		Connection conn = threadLocal.get();
		if (conn == null) {
			// 从数据源中获取数据库连接
			conn = getDataSource().getConnection();
			// 将conn绑定到当前线程
			threadLocal.set(conn);
		}
		return conn;
	}

	/**
	 * 开启事务
	 */
	public static void startTransaction() {
		try {
			Connection conn = threadLocal.get();
			if (conn == null) {
				conn = getConnection();
				// 把conn绑定到当前线程上
				threadLocal.set(conn);
			}
			// 开启事务
			conn.setAutoCommit(false);
		} catch (Exception e) {

			throw new RuntimeException(e);
		}

	}

	/**
	 * 回滚事务
	 */
	public static void rollback() {
		try {
			// 从当前线程获取connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.rollback();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 提交事务
	 */
	public static void commit() {
		try {
			// 从当前线程中获取connection
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.commit();
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 关闭数据库连接,注意!不是真的关闭,而是把连接还给连接池
	 * 
	 */
	public static void close() {
		try {
			Connection conn = threadLocal.get();
			if (conn != null) {
				conn.close();
				// 解除当前线程上绑定的conn
				threadLocal.remove();
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
