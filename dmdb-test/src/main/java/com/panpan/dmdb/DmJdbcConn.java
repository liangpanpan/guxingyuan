package com.panpan.dmdb;

import java.sql.*;
import java.util.UUID;

/**
 * <pre>
 * Modify Information:
 * Author       Date          Description
 * ============ ============= ============================
 * liangpanpan   2025/10/27       create this file
 * </pre>
 */
public class DmJdbcConn {

    // 定义连接
    static Connection con = null;
    // 定义 DM JDBC 驱动串
    static String cname = "dm.jdbc.driver.DmDriver";
    // 定义 DM URL 连接串
    static String url = "jdbc:dm://10.1.1.170:5236";
    // 定义连接用户名
    static String userid = "SYSDBA";
    // 定义连接用户口令
    static String pwd = "TJym1230123";

    // SYSDBA TJym1230123

    // sic  TJym@1230123

    public static void main(String[] args) {
        try {
            Class.forName(cname);
            con = DriverManager.getConnection(url, userid, pwd);
            con.setAutoCommit(true);
            System.out.println("[SUCCESS]conn database");


            PreparedStatement preparedStatement = con.prepareStatement("insert into sic.sic_multisource_rule" +
                    " (id, rule_name, rule_describe, rule_level, intel_source, satisfy_one_type, match_num, status)" +
                    " values (?, ?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, UUID.randomUUID().toString().replace("-", ""));
            preparedStatement.setString(2, "测试规则名称1");
            preparedStatement.setString(3, "测试规则描述1");
            preparedStatement.setString(4, "严重");
            preparedStatement.setString(5, "天际友盟");
            preparedStatement.setInt(6, 1);
            preparedStatement.setInt(7, 0);
            preparedStatement.setInt(8, 1);

            int i = preparedStatement.executeUpdate();
            System.out.println("execute result:" + i);
            con.commit();
            preparedStatement.close();

            System.out.println("execute search");

            preparedStatement = con.prepareStatement("select * from sic.sic_multisource_rule");
            ResultSet resultSet = preparedStatement.executeQuery();
            int index = 1;
            while (resultSet.next()) {
                System.out.println("============ 第" + index + "条数据 ===============");
                System.out.println("id:" + resultSet.getString("id"));
                System.out.println("rule_name:" + resultSet.getString("rule_name"));
                System.out.println("rule_describe:" + resultSet.getString("rule_describe"));
                System.out.println("rule_level:" + resultSet.getString("rule_level"));
                index++;
            }
        } catch (Exception e) {
            System.out.println("[FAIL]conn database：" + e.getMessage());
        } finally {
            try {
                disConn(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disConn(Connection con) throws SQLException {
        if (con != null) {
            con.close();
        }
    }

}
