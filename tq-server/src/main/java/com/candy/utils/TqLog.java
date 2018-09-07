package com.candy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TqLog
{
  private static Logger dailyLog = LoggerFactory.getLogger("DailyLog");

  private static Logger errorLog = LoggerFactory.getLogger("ErrorLog");

  private static Logger systemLog = LoggerFactory.getLogger("SystemLog");

  public static Logger getDailyLog()
  {
    return dailyLog;
  }

  public static Logger getErrorLog()
  {
    return errorLog;
  }

  public static Logger getSystemLog()
  {
    return systemLog;
  }
}