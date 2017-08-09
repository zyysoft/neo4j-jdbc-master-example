package com.welltang.bi.domain;

public class FollowNeo4j extends Neo4jNode{
	//关注状态
	public int status;
	//关注时间
	public long create_time;
	public long last_modify_time;
 
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getCreate_time() {
		return create_time;
	}
	public void setCreate_time(long create_time) {
		this.create_time = create_time;
	}
	public long getLast_modify_time() {
		return last_modify_time;
	}
	public void setLast_modify_time(long last_modify_time) {
		this.last_modify_time = last_modify_time;
	}
}
