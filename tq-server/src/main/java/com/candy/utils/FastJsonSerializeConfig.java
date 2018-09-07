package com.candy.utils;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

public class FastJsonSerializeConfig
{
    private static final SerializeConfig mapping = new SerializeConfig();

    public static SerializeConfig get()
    {
        return mapping;
    }

    static
    {
        mapping.put(Date.class, new ObjectSerializer()
        {
            public void write(JSONSerializer arg0, Object arg1, Object arg2, Type arg3, int arg4)
                    throws IOException
            {
                int time = Double.valueOf(Math.floor(((Date) arg1).getTime() / 1000L)).intValue();
                arg0.write(Integer.valueOf(time));
            }
        });
    }
}
