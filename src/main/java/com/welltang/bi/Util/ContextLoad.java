package com.welltang.bi.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextLoad {
	public static Properties prop;
	private static String confile = "res.properties";
	private static Logger logger = LoggerFactory.getLogger(ContextLoad.class);
	
	public static enum RELATIONSHIP {
		COMMENT("BBS_COMMENT"), FOLLOW("BBS_FOLLOW"), INVITE("INVITE"), POSTSPRAISE("BBS_PRAISE");
		private String name;
		private RELATIONSHIP(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		@Override
		public String toString() {
			return getName();
		}
		
	}

	static {
		prop = new Properties();
		InputStream inputStream = null;
		try {
			// java应用
			confile = ContextLoad.class.getClassLoader().getResource("").getPath() + confile;
			logger.info("confile path:{}",confile);
			File file = new File(confile);
			inputStream = new BufferedInputStream(new FileInputStream(file));
			prop.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static Map parseMap(Properties prop, String prefix) {
		Map result = new HashMap();
		for (Object key : prop.keySet()) {
			if (((String) key).startsWith(prefix)) {
				String name = ((String) key).substring(prefix.length());
				result.put(name, prop.get(key));
			}
		}
		return result;
	}
}
