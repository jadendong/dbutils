import top.getcode.dbutils.service.AccountService;

import java.sql.SQLException;

public class TestAccountService {

	/**
	 * 测试转账事务
	 * 原生方法,获取连接-->关闭commit-->提交or回滚
	 * 但是AccountDao的这个transfer方法可以处理转账业务，并且保证了在同一个事务中进行，
	 * 但是AccountDao的这个transfer方法是处理两个用户之间的转账业务的，已经涉及到具体的业务操作，
	 * 应该在业务层中做，不应该出现在DAO层的，
	 * 在开发中，DAO层的职责应该只涉及到基本的CRUD，
	 * 不涉及具体的业务操作，所以在开发中DAO层出现这样的业务处理方法是一种不好的设计
	 * @param args
	 */
	public static void main(String[] args) {
		AccountService as=new AccountService();
		try {
			as.transfer("小李", "小黑", 100);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
