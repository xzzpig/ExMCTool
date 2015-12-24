package com.github.xzzpig.exmctool.logutil;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class LogRedirectWithLog4j {

	  public static void regist()
			    throws Exception
			  {
			    Logger logger = (Logger)LogManager.getRootLogger();
			    logger.addAppender(new LogAppender());
			  }

}
