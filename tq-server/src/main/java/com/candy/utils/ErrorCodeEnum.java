package com.candy.utils;


public enum ErrorCodeEnum
{
	/**
	 * 
	 */
  SUCCESS, 
  UNKNOWN, 
  PARAM,
  EXIST, 
  NOT_EXIST, 
  DB, 
  DB_NOTUNCATEGORIZED, 
  CACHE, 
  NETWORK,
  AUTH_NOLOGIN, 
  AUTH_LOGINNOAUTH,
  AUTH_LOGINOUTTIME;

  private int code;
  private String msg;

  public int getCode()
  {
    return this.code;
  }

  public String getMsg() {
    return this.msg;
  }

  public boolean isSucess(ErrorCodeEnum e) {
    return (e.getCode() == SUCCESS.getCode());
  }

  public boolean isSucess(int code) {
    return (code == SUCCESS.getCode());
  }

  public static ErrorCodeEnum getErrorCodeEnum(int code)
  {
    for (ErrorCodeEnum e : values()) {
      if (e.getCode() == code) {
        return e;
      }
    }
    return UNKNOWN;
  }
}