package com.zlq.JDBC.LX;

import com.zlq.JDBC.jdbc.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.Scanner;

public class Exer2Test {

    //像表中添加记录
    @Test
    public void testInsert() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("四级：");
        int type = scanner.nextInt();
        System.out.println("身份证号：");
        String IDCard = scanner.next();
        System.out.println("准考证号：");
        String examCard = scanner.next();
        System.out.println("学生姓名：");
        String studentName = scanner.next();
        System.out.println("所在城市：");
        String location = scanner.next();
        System.out.println("考试成绩：");
        int garde = scanner.nextInt();

        String sql = "insert into student(type,IDCard,examCard,studentName,location,garde)values(?,?,?,?,?)";
        int update = update(sql, type, IDCard, examCard, studentName, location, garde);

        if (update > 0) {
            System.out.println("添加成功");
        } else {
            System.out.println("添加失败");
        }

    }


    public int update(String sql, Object... args) {//sql中占位符的个数与可变形参的长度相同！
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtils.getConnection();
            //2.预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);//小心参数声明错误！！
            }
            //4.执行
            /* ps.execute();
             * 如果执行的是查询操作，有返回结果，则此方法返回true;
             * 如果执行的是增、删、改操作，没有返回结果，则此方法返回false.
             *
             */
            //方式一：
            //return ps.execute();
            //方式二：executeUpdate返回执行受到影响的行数
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //5.资源的关闭
            JDBCUtils.closeResource(conn, ps);

        }
        return 0;
    }

    /*
    根据身份证号或者准考证号查询
     */
    @Test
    public void queryWithIDCard() {
        System.out.println("请输入类型");
        System.out.println("a。准考证号");
        System.out.println("b。准考证号");
        Scanner scanner = new Scanner(System.in);
        String selection = scanner.next();
//        if (selection.equalsIgnoreCase("a"))//有可能selection空指针异常
        if ("a".equalsIgnoreCase(selection)) {//方法用于将字符串与指定的对象比较,不考虑大小写
            System.out.println("请输入准考证号");
            String examCard = scanner.next();
            String sql = "select FlowID flowID,TYpe type,IDCard,examCard,ExamCard examCard,StudentName name,Location location,Grade grade from student where examCard = ?";

            student stu = getInstance(student.class, sql, examCard);
            if (stu != null) {
                System.out.println(stu);
            } else {
                System.out.println("无法找到准考证号");
            }

        } else if ("b".equalsIgnoreCase(selection)) {
            System.out.println("请输入身份证号");
            String IDCard = scanner.next();
            String sql = "select FlowID flowID,TYpe type,IDCard,examCard,ExamCard examCard,StudentName name,Location location,Grade grade from student where IDCard = ?";

            student stu = getInstance(student.class, sql, IDCard);
            if (stu != null) {
                System.out.println(stu);
            } else {
                System.out.println("无法找到准考证号");
            }

        } else {
            System.out.println("输入错误");
        }

    }

    //通用的查询操作
    public <T> T getInstance(Class<T> clazz, String sql, Object... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();

            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();
            // 获取结果集的元数据 :ResultSetMetaData
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();

            if (rs.next()) {
                T t = clazz.newInstance();
                // 处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    // 获取列值
                    Object columValue = rs.getObject(i + 1);

                    //通过ResultSetMetaData
                    //获取列的列名：getColumnName() --不推荐使用
                    //获取列的别名：getColumnLabel()
                    // String columnName = rsmd.getColumnName(i + 1);
                    String columnLabel = rsmd.getColumnLabel(i + 1);// 获取每个列的列名

                    // 给t对象指定的columnName属性，赋值为columValue：通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(conn, ps, rs);

        }

        return null;
    }

    //问题3：删除指定的学生信息
    @Test
    public void testDeleteByExamCard() {
        System.out.println("请输入学生的考号：");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        //查询指定准考证号的学生
        String sql = "select FlowID flowID,Type type,IDCard,ExamCard examCard,StudentName name,Location location,Grade grade from student where examCard = ?";
        student student = getInstance(student.class, sql, examCard);
        if (student == null) {
            System.out.println("查无此人，请重新输入");
        } else {
            String sql1 = "delete from student where examCard = ?";
            int deleteCount = update(sql1, examCard);
            if (deleteCount > 0) {
                System.out.println("删除成功");
            }
        }
    }

    //优化以后的操作：
    @Test
    public void testDeleteByExamCard1() {
        System.out.println("请输入学生的考号：");
        Scanner scanner = new Scanner(System.in);
        String examCard = scanner.next();
        String sql = "delete from examstudent where examCard = ?";
        int deleteCount = update(sql, examCard);
        if (deleteCount > 0) {
            System.out.println("删除成功");
        } else {
            System.out.println("查无此人，请重新输入");
        }

    }
}
