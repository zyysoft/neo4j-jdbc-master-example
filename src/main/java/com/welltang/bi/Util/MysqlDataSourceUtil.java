package com.welltang.bi.Util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class MysqlDataSourceUtil {
	private static DataSource dataSource;
	public static DataSource dataSource(Map properties) throws Exception {
		if(dataSource==null) return DruidDataSourceFactory.createDataSource(properties);
		return dataSource;
	}
	
	public static void close(PreparedStatement ps,ResultSet rs){
		if(ps!=null){
			try {
				ps.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
