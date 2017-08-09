package com.welltang.bi.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.driver.v1.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.welltang.bi.Util.ContextLoad;
import com.welltang.bi.domain.CommentNeo4j;
import com.welltang.bi.domain.FollowNeo4j;
import com.welltang.bi.domain.InviteNeo4j;
import com.welltang.bi.domain.Neo4jNode;
import com.welltang.bi.domain.PostsPraiseNeo4j;
import com.welltang.bi.domain.User;

public class Neo4jDao {
	private Session session;
	private static Logger logger = LoggerFactory.getLogger(Neo4jDao.class);

	public Neo4jDao(Session session) {
		this.session = session;
	}

	public void getUserById(int user_id) {
		StatementResult result = session.run("MATCH (a:User) WHERE a.user_id = " + user_id
				+ " RETURN a.name AS name, a.followed_cnt AS followed_cnt");
		while (result.hasNext()) {
			Record record = result.next();
			System.out.println(record.get("name").asString() + " " + record.get("followed_cnt").asInt());
		}
	}

	/*
	 * 创建节点和关系
	 */
	public <E> void createNode(List<E> list) {
		Long beginTime = System.currentTimeMillis();

		String cypherParent = "MERGE (u1:User { user_id:{master_id}}) " 
				+ " ON CREATE SET u1.created = timestamp() "
				+ " merge (u2:User{user_id:{passive_id}})" 
				+ " on create set u2.created=timestamp()";
		
		String cypherComment = " merge (u1) - [r:" + ContextLoad.RELATIONSHIP.COMMENT
				+ "] -> (u2) on create set r.cnt= {cnt} on MATCH set r.cnt= {cnt}";
		String cypherFollow = " merge (u1) - [r:" + ContextLoad.RELATIONSHIP.FOLLOW
				+ "] -> (u2) on create set r.create_time= {create_time} ";
		String cypherInvite = " merge (u1) - [r:" + ContextLoad.RELATIONSHIP.INVITE
				+ "] -> (u2) on create set r.create_time= {create_time} ";
		String cypherPostsPraise = " merge (u1) - [r:" + ContextLoad.RELATIONSHIP.POSTSPRAISE
				+ "] -> (u2) on create set r.cnt= {cnt} on MATCH set r.cnt= {cnt}";

		logger.info("list size:{}", list.size());
		Map<String, Object> statementParameters = new HashMap<String, Object>();
		Transaction trans = session.beginTransaction();
		StatementResult result;
		for (E obj : list) {
			if (obj instanceof Neo4jNode) {
				statementParameters.put("master_id", ((Neo4jNode) obj).getMaster_id());
				statementParameters.put("passive_id", ((Neo4jNode) obj).getPassive_id());
			}
			if (obj instanceof CommentNeo4j) {
				statementParameters.put("cnt", ((CommentNeo4j) obj).getCnt());
				result = trans.run(cypherParent + cypherComment, statementParameters);
			}
			if (obj instanceof FollowNeo4j) {
				statementParameters.put("create_time", ((FollowNeo4j) obj).getCreate_time());
				result = trans.run(cypherParent + cypherFollow, statementParameters);
			}
			if (obj instanceof InviteNeo4j) {
				statementParameters.put("create_time", ((InviteNeo4j) obj).getCreate_time());
				result = trans.run(cypherParent + cypherInvite, statementParameters);
			}
			if (obj instanceof PostsPraiseNeo4j) {
				statementParameters.put("cnt", ((PostsPraiseNeo4j) obj).getCnt());
				result = trans.run(cypherParent + cypherPostsPraise, statementParameters);
			}
		}
		trans.success();
		trans.close();
		logger.debug("cost time:{}", System.currentTimeMillis() - beginTime);
	}

	/*
	 * 更新节点属性
	 */
	public void updateNode(List<User> list) {
		Long beginTime = System.currentTimeMillis();

		String cypherParent = "match (u1:User { user_id:{user_id}}) "
				+ " set u1.name ={user_name},u1.mobile={mobile}";
		logger.info("list size:{}", list.size());
		Map<String, Object> statementParameters = new HashMap<String, Object>();
		Transaction trans = session.beginTransaction();
		StatementResult result;
		for (User user : list) {
			statementParameters.put("user_id", user.getUser_id());
			statementParameters.put("user_name", user.getUser_name());
			statementParameters.put("mobile", user.getMobile());
			result = trans.run(cypherParent , statementParameters);
		}
		trans.success();
		trans.close();
		logger.debug("cost time:{}", System.currentTimeMillis() - beginTime);
	}

}
