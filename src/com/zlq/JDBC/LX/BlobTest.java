package com.zlq.JDBC.LX;

import com.zlq.JDBC.jdbc.JDBCUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class BlobTest {
    @Test
    public void testInsert() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        String sql = "insert into student(name,email,birth,photo) values(?,?,?,?)";
        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setObject(1,"人");
        ps.setObject(2,"832838@qq.com");
        ps.setObject(3,"2008-01-26");
        FileInputStream is = new FileInputStream(new File("205421.jpg"));
        ps.setBlob(4,is);
        ps.execute();
        JDBCUtils.closeResource(conn,ps);


    }
}
