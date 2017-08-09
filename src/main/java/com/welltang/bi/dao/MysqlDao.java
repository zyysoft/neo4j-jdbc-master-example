package com.welltang.bi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.welltang.bi.Util.MysqlDataSourceUtil;
import com.welltang.bi.domain.CommentNeo4j;
import com.welltang.bi.domain.FollowNeo4j;
import com.welltang.bi.domain.InviteNeo4j;
import com.welltang.bi.domain.PostsPraiseNeo4j;
import com.welltang.bi.domain.User;

public class MysqlDao {
	private static Logger logger = LoggerFactory.getLogger(MysqlDao.class);
	private Connection conn;

	public MysqlDao(Connection conn) {
		this.conn = conn;
	}

	public List<FollowNeo4j> getFollow(Date beginDate) {
		Long beginTime =System.currentTimeMillis();
		String querySql = "select master_id,passive_id,status,create_time,last_modify_time"
				+ " from t_follow "
				+ "where last_modify_time >=?";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<FollowNeo4j> followList = new ArrayList<FollowNeo4j>();
		try {
			ps = conn.prepareStatement(querySql);
			ps.setDate(1, new java.sql.Date(beginDate.getTime()));
			rs = ps.executeQuery();
			while (rs.next()) {
				FollowNeo4j bybe = new FollowNeo4j();
				bybe.setMaster_id(rs.getInt("master_id"));
				bybe.setPassive_id(rs.getInt("passive_id"));
				bybe.setStatus(rs.getInt("status"));
				bybe.setCreate_time(rs.getDate("create_time").getTime());
				bybe.setLast_modify_time(rs.getDate("last_modify_time").getTime());
				followList.add(bybe);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MysqlDataSourceUtil.close(ps, rs);
		}
		
		logger.debug("cost time:{}",System.currentTimeMillis()-beginTime);
		return followList;

	}

	public List<CommentNeo4j> getComment() {
		Long beginTime =System.currentTimeMillis();
		
		String querySql = "select a.user_id as master_id,b.user_id as passive_id,count(1) as cnt"
				+ " from t_posts_comment a,t_posts b " + " where a.posts_id=b.id" + " group by a.user_id,b.user_id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<CommentNeo4j> followList = new ArrayList<CommentNeo4j>();
		try {
			ps = conn.prepareStatement(querySql);
			rs = ps.executeQuery();
			while (rs.next()) {
				CommentNeo4j bybe = new CommentNeo4j();
				bybe.setMaster_id(rs.getInt("master_id"));
				bybe.setPassive_id(rs.getInt("passive_id"));
				bybe.setCnt(rs.getInt("cnt"));
				followList.add(bybe);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MysqlDataSourceUtil.close(ps, rs);
		}
		logger.debug("cost time:{}",System.currentTimeMillis()-beginTime);
		return followList;

	}

	public List<InviteNeo4j> getInvite(Date begindate) {
		Long beginTime =System.currentTimeMillis();
		
		String querySql = "select a.creator_id as master_id,a.user_id as passive_id,a.register_time as create_time"
				+ " from analyze.dw_fact_patient a"
				+ " where  a.register_dt>=?"
				+ " and creator_id >0";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<InviteNeo4j> followList = new ArrayList<InviteNeo4j>();
		try {
			ps = conn.prepareStatement(querySql);
			ps.setDate(1, new java.sql.Date(begindate.getTime()));
			rs = ps.executeQuery();
			while (rs.next()) {
				InviteNeo4j bybe = new InviteNeo4j();
				bybe.setMaster_id(rs.getInt("master_id"));
				bybe.setPassive_id(rs.getInt("passive_id"));
				bybe.setCreate_time(rs.getTimestamp("create_time").getTime());
				followList.add(bybe);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MysqlDataSourceUtil.close(ps, rs);
		}
		logger.debug("cost time:{}",System.currentTimeMillis()-beginTime);
		return followList;

	}
	
	
	public List<PostsPraiseNeo4j> getPostsPraise() {
		Long beginTime =System.currentTimeMillis();
		
		String querySql = "select a.user_id as master_id,b.user_id as passive_id,count(1) as cnt"
				+ " from t_posts_praise a,t_posts b " + " where a.posts_id=b.id" + " group by a.user_id,b.user_id";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<PostsPraiseNeo4j> followList = new ArrayList<PostsPraiseNeo4j>();
		try {
			ps = conn.prepareStatement(querySql);
			rs = ps.executeQuery();
			while (rs.next()) {
				PostsPraiseNeo4j bybe = new PostsPraiseNeo4j();
				bybe.setMaster_id(rs.getInt("master_id"));
				bybe.setPassive_id(rs.getInt("passive_id"));
				bybe.setCnt(rs.getInt("cnt"));
				followList.add(bybe);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MysqlDataSourceUtil.close(ps, rs);
		}
		logger.debug("cost time:{}",System.currentTimeMillis()-beginTime);
		return followList;
	}
	
	public List<User> getUsers() {
		Long beginTime =System.currentTimeMillis();
		
		String querySql = "select a.user_id as user_id,a.name as user_name,substring(a.mobile,1,11) as mobile"
				+ " from analyze.dw_fact_doctor a"
				+ " union all "
				+ " select a.user_id as master_id,a.name as user_name,substring(a.mobile,1,11) as mobile"
				+ " from analyze.dw_fact_patient a";
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<User> followList = new ArrayList<User>();
		try {
			ps = conn.prepareStatement(querySql);
			rs = ps.executeQuery();
			while (rs.next()) {
				User bybe = new User();
				bybe.setUser_id(rs.getInt("user_id"));
				bybe.setUser_name(rs.getString("user_name"));
				bybe.setMobile(rs.getString("mobile"));
				followList.add(bybe);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			MysqlDataSourceUtil.close(ps, rs);
		}
		logger.debug("cost time:{}",System.currentTimeMillis()-beginTime);
		return followList;
	}
}
