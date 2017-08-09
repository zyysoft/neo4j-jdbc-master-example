package com.welltang.bi.domain;

public class Neo4jNode {
	// 邀请人
	public int master_id;
	// 被邀请人
	public int passive_id;

	public int getMaster_id() {
		return master_id;
	}

	public void setMaster_id(int master_id) {
		this.master_id = master_id;
	}

	public int getPassive_id() {
		return passive_id;
	}

	public void setPassive_id(int passive_id) {
		this.passive_id = passive_id;
	}

}
