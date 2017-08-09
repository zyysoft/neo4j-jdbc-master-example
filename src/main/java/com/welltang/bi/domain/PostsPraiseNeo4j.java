package com.welltang.bi.domain;

public class PostsPraiseNeo4j extends Neo4jNode{
	 
	//点赞次数
	public int cnt;
	public int getCnt() {
		return cnt;
	}
	public void setCnt(int praise) {
		this.cnt = praise;
	}
}