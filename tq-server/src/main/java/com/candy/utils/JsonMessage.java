package com.candy.utils;


import java.util.Date;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonMessage
{
    private static final int JSON_RESULT_SUCCESS = ErrorCodeEnum.SUCCESS.getCode();

    private static final int JSON_RESULT_FAILED = ErrorCodeEnum.UNKNOWN.getCode();

    private int code = JSON_RESULT_SUCCESS;
    private String rdesc;
    private JSONObject data;
    private Date time = null;

    protected JsonMessage()
    {
        
    }

    private JsonMessage(int code, String rdesc)
    {
        this.code = code;
        this.rdesc = rdesc;
    }

    public static JsonMessage createSuccess()
    {
        JsonMessage jsonResult = new JsonMessage(JSON_RESULT_SUCCESS, null);
        return jsonResult;
    }

    public static JsonMessage createSuccess(String rdesc)
    {
        JsonMessage jsonResult = new JsonMessage(JSON_RESULT_SUCCESS, rdesc);
        return jsonResult;
    }

    public static JsonMessage createFalied()
    {
        JsonMessage jsonResult = new JsonMessage(JSON_RESULT_FAILED, "System sneak off.");
        return jsonResult;
    }

    public static JsonMessage createFalied(String rdesc)
    {
        JsonMessage jsonResult = new JsonMessage(JSON_RESULT_FAILED, rdesc);
        return jsonResult;
    }

    public static JsonMessage createFalied(int code, String rdesc)
    {
        JsonMessage jsonResult = new JsonMessage(code, rdesc);
        return jsonResult;
    }

    public int getCode()
    {
        return this.code;
    }

    public void setData(JSONObject data)
    {
        this.data = data;
    }

    public JSONObject getData()
    {
        return this.data;
    }

    public Date getTime()
    {
        return this.time;
    }

    public String getRdesc()
    {
        return this.rdesc;
    }

    public void setRdesc(String rdesc)
    {
        this.rdesc = rdesc;
    }

    public JsonMessage addData(JSONObject data)
    {
        if (data != null)
        {
            this.data = data;
        }
        return this;
    }

    @Override
    public String toString()
    {
        this.time = new Date();
        try
        {
            return JSONObject.toJSONString(this, FastJsonSerializeConfig.get(),
                    new SerializerFeature[0]);
        } catch (Exception e)
        {
            TqLog.getErrorLog().error("return jsonObject to String exception", e);
        }
        return createFalied("return jsonObject to String exception").toString();
    }
}
