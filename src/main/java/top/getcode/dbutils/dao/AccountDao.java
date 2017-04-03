package top.getcode.dbutils.dao;

import org.apache.commons.dbutils.QueryRunner;
import top.getcode.dbutils.util.JDBCUtils;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 　对于这样的同时更新一个表中的多条数据的操作，那么必须保证要么同时成功，要么都不成功， 所以需要保证这两个update操作在同一个事务中进行。
 * 在开发中，我们可能会在AccountDao写一个转账处理方法，如下：
 */
public class AccountDao {
	/**
	 * 这个方法用来处理两个用户之间的转账业务 在开发中,DAO层的职责应该只涉及到CRUD,
	 * 而这个transfer方法是处理两个用户之间的转账业务的，已经涉及到具体的业务操作，应该在业务层中做，不应该出现在DAO层的
	 * 所以在开发中DAO层出现这样的业务处理方法是完全错误的
	 * @throws SQLException 
	 */
	public void transfer(String sourceName, String targetName, float money) throws SQLException {
		Connection conn = null;
		try {
			conn = JDBCUtils.getConnection();
			// 开启事务
			conn.setAutoCommit(false);
			/**
			 * 在创建QueryRunner对象时，不传递数据源给它，是为了保证这两条SQL在同一个事务中进行，
			 * 我们手动获取数据库连接，然后让这两条SQL使用同一个数据库连接执行
			 */
			QueryRunner qr=new QueryRunner();
			String sql1="UPDATE ACCOUNT SET BALANCE=BALANCE-100 WHERE NAME=?";
			String sql2="UPDATE ACCOUNT SET BALANCE=BALANCE+100 WHERE NAME=?";
			
			qr.update(conn,sql1,new Object[]{sourceName});
			
			 //模拟程序出现异常让事务回滚
//			int x=1/0;
			qr.update(conn,sql2,new Object[]{targetName});
			
			// sql 正常执行后就提交事务
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			if(conn!=null){
				// 出现异常之后就回滚事务
				conn.rollback();
			}
		}finally{
			conn.close();
		}
	}
}
