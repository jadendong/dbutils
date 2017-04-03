package top.getcode.dbutils.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import top.getcode.dbutils.entity.Account;
import top.getcode.dbutils.util.JDBCUtils;

import java.sql.SQLException;

public class AccountDao2{

	
	@SuppressWarnings("deprecation")
	public Account find(int id) throws SQLException {
		QueryRunner qr=new QueryRunner();
		String sql="SELECT * FROM ACCOUNT WHERE ID=?";
		
		// jdbcUtil.getConnection 获取当前线程中的Connection对象
		
		return (Account)qr.query(JDBCUtils.getConnection(), sql,id,new BeanHandler<Account>(Account.class));
	}

	public void update(Account account) throws SQLException {
		QueryRunner qr=new QueryRunner();
		
		String sql="UPDATE ACCOUNT SET NAME=?,BALANCE=? WHERE id=?";
		
		Object[] params={account.getName(),account.getBalance(),account.getId()};
		
		// jdbcUtil.getConnection 获取当前线程中的Connection对象
		
		qr.update(JDBCUtils.getConnection(),sql,params);
	}

}
