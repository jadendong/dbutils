import top.getcode.dbutils.service.AccountService2;

public class TestAccountService2 {
	/**
	 * 测试利用threadLocal实现事务转账
	 * @param args
	 */
	public static void main(String[] args) {
		AccountService2 accountService2=new AccountService2();
		
		accountService2.transfer(4, 5, 200);
	}
}
