import top.getcode.dbutils.DBUtils.QueryRunnerCRUD;

import java.sql.SQLException;

/**
 * Created by Administrator on 2017/4/3.
 *
 */
public class TestInsert {
    public static void main(String[] args) throws SQLException {
        QueryRunnerCRUD qrc=new QueryRunnerCRUD();
//        qrc.add();
        // 是否返回主键值
//        qrc.fixAdd2();
//        System.out.println(qrc.fixAdd2());
          qrc.fix2Add2();
    }
}
