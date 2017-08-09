package com.welltang.bi.domain;

public class InviteNeo4j extends Neo4jNode {
	 
	//邀请时间
	public long create_time;
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
}
