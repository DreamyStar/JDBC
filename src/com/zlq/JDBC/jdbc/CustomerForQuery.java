package com.zlq.JDBC.jdbc;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CustomerForQuery {
    @Test
    public void testQuery1(){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select e_no,e_name,e_job,hireDate from employees where e_no=?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1,1001);
            rs = ps.executeQuery();
            if (rs.next()){//判断结果集下一条是否有数据
                int id = rs.getInt(1);
                String name = rs.getString(2);
                String email = rs.getString(3);
                Date birth = rs.getDate(4);
    //            System.out.println("id="+id);
    //            Object[] date = new Object[]{id,name,email,birth};
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn,ps,rs);
        }


    }
}
