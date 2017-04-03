import top.getcode.dbutils.DBUtils.QueryRunnerCRUD;

import java.sql.SQLException;

public class TestQueryRunnerCRUD {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		QueryRunnerCRUD qrc=new QueryRunnerCRUD();
		
		//1. 测试添加方法
//		try {
//			qrc.add();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		//2. 测试删除方法
		
//		try {
//			qrc.delete();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		// 3. 测试更新方法
		
//		qrc.update();
		
		// 4. 查找某个用户
//		try {
//			qrc.find();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			
//		}
		
		// 5. 测试得到所有
//		try {
//			qrc.getAll();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
		
		// 6. 执行批处理
		try {
			qrc.batch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
