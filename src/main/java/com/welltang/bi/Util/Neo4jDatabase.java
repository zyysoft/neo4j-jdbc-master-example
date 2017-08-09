package com.welltang.bi.Util;

import java.util.Map;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class Neo4jDatabase {
	public static Driver driver;
	public static Session session;

	public static Session dataSource(Map properties) throws Exception {
		if (driver == null) {
			driver = GraphDatabase.driver((String) properties.get("url"),
					AuthTokens.basic((String) properties.get("username"), (String) properties.get("password")));
		}
		if (session == null) {
			session = driver.session();
		}
		return session;
	}

	public static void close() {
		driver.close();
		session.close();
	}
}
