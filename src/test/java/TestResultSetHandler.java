import top.getcode.dbutils.DBUtils.ResultSetHandler;

import java.sql.SQLException;

public class TestResultSetHandler {

	/**
	 * @param args
	 * @throws SQLException
	 */
	public static void main(String[] args) throws SQLException {
		ResultSetHandler rsh = new ResultSetHandler();
		// 1.
		// rsh.testArrayHandler();

		// 2.
		// rsh.testArryListHandler();

		// 3.
		// rsh.testBeanHandler();

		// 4.
		 rsh.tsetBeanListHandler();

		// 5.
		// rsh.testColumnListHandler();

		// 6.
		// rsh.testKeyedHandler();

		// 7.
		// rsh.testMapHandler();

		// 8.
		// rsh.testMapListHandler();

		// 9.
//		 rsh.testScalarHandler();
		
		// 10.
//		rsh.testBeanMapHandler();
	}
}
