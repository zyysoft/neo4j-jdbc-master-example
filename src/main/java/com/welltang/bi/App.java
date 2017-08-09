package com.welltang.bi;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.neo4j.driver.v1.Session;

import com.sun.tools.doclets.internal.toolkit.builders.AbstractBuilder.Context;
import com.welltang.bi.Util.ContextLoad;
import com.welltang.bi.Util.MysqlDataSourceUtil;
import com.welltang.bi.Util.Neo4jDatabase;
import com.welltang.bi.dao.MysqlDao;
import com.welltang.bi.dao.Neo4jDao;
import com.welltang.bi.domain.CommentNeo4j;
import com.welltang.bi.domain.FollowNeo4j;
import com.welltang.bi.domain.InviteNeo4j;
import com.welltang.bi.domain.PostsPraiseNeo4j;
import com.welltang.bi.domain.User;

/**
 * Hello world!
 *
 */
public class App {
	static SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		Properties prop = ContextLoad.prop;
		try {
			Connection mysqlConn = MysqlDataSourceUtil.dataSource(ContextLoad.parseMap(prop, "druid.")).getConnection();
			MysqlDao dao = new MysqlDao(mysqlConn);
			List<FollowNeo4j> followsList=dao.getFollow(df.parse("20160710"));
			List<CommentNeo4j> commentList=dao.getComment();
			List<InviteNeo4j> inviteList= dao.getInvite(df.parse("20160710"));
			List<PostsPraiseNeo4j> postsPraiseList= dao.getPostsPraise();
			List<User> userList = dao.getUsers();
			
			Session neo4jSession = Neo4jDatabase.dataSource(ContextLoad.parseMap(prop, "neo4j."));
			Neo4jDao neo4jDao = new Neo4jDao(neo4jSession);
			neo4jDao.createNode(followsList);
			neo4jDao.createNode(commentList);
			neo4jDao.createNode(inviteList);
			neo4jDao.createNode(postsPraiseList);
			neo4jDao.updateNode(userList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
