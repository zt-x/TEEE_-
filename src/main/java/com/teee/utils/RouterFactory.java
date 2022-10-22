package com.teee.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class RouterFactory{
    public JSONObject getRouterObject(String name, String path, String component, String icon){
        JSONObject router = new JSONObject();
        router.put("name", name);
        router.put("path", path);
        router.put("component", component);
        router.put("icon", icon);
        return router;
    }
}
