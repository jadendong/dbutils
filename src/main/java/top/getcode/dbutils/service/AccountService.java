package top.getcode.dbutils.service;

import top.getcode.dbutils.dao.AccountDao;

import java.sql.SQLException;

/**
 * 然后我们在AccountService中再写一个同名方法，在方法内部调用AccountDao的transfer方法处理转账业务，如下：
 */
public class AccountService {
	public void transfer(String sourceName, String targetName, float money) throws SQLException {
		AccountDao dao = new AccountDao();
		dao.transfer(sourceName, targetName, money);
	}
}


