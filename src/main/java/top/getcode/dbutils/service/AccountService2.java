package top.getcode.dbutils.service;

import top.getcode.dbutils.dao.AccountDao2;
import top.getcode.dbutils.entity.Account;
import top.getcode.dbutils.util.JDBCUtils;

public class AccountService2 {

	public void transfer(int sourceid, int targetid, float money) {
		try {
			// 开启事务,在业务层处理事务,保证dao层的多个操作在同一个事务中进行
			JDBCUtils.startTransaction();
			AccountDao2 dao = new AccountDao2();

			Account source = dao.find(sourceid);
			Account target = dao.find(targetid);

			source.setBalance(source.getBalance() - money);
			target.setBalance(target.getBalance() + money);

			dao.update(source);

			// 模拟程序出现异常,让事务回滚
//			 int x=1/0;

			dao.update(target);

			// SQL正常执行后提交事务
			JDBCUtils.commit();
		} catch (Exception e) {
			e.printStackTrace();
			// 出现异常之后,就回滚事务
			JDBCUtils.rollback();
		} finally {
			JDBCUtils.close();
		}
	}
}
