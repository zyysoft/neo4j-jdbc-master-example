package com.welltang.bi.domain;

public class CommentNeo4j extends Neo4jNode {
	//评价次数
	public int cnt;

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}
}