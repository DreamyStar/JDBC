package com.zlq.JDBC.jdbc;

import org.junit.Test;

public class test {
    @Test
    public void test(){
        String s1 = "Programming";
        String s2 = new String("Programming");
        String s3 = "Program";
        String s4 = "ming";
        String s5 = "Program" + "ming";
        String s6 = s3 + s4;
        System.out.println(s1 == s2);
        System.out.println(s1 == s5);
        System.out.println(s1 == s6);
        System.out.println(s1 == s6.intern());
        System.out.println(s2 == s2.intern());
        String s7 = new StringBuilder("Program") .append("ming").toString();
        System.out.println(s7.intern() == s7);
    }

}
