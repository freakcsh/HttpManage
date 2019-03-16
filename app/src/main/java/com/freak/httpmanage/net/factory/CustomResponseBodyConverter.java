package com.freak.httpmanage.net.factory;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;
/**
 *
 * @author freak
 * @date 2019/03/12
 */

public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private JSONObject mObject;

    CustomResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
//        LogUtils.e(json);
//        try {
//            mObject = new JSONObject(json);
//                if (mObject.optJSONObject("date") == null || mObject.optJSONArray("date") == null) {
//                    Map<String,Object> map=new HashMap<>(2);
//                    map.put("code",mObject.optString("code"));
//                    map.put("msg",mObject.optString("msg"));
//                    Map<String ,Object> map1=new HashMap<>(0);
//                    map.put("data",map1);
//                    String jsonString=gson.toJson(map);
//                    LogUtils.e(jsonString);
//                    T result = adapter.fromJson(jsonString);
//                    return result;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        T result = adapter.fromJson(json);
//        JSONObject object = null;
//        try {
//            object = new JSONObject(json);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        if (result instanceof HttpResult) {
//            ((HttpResult) result).setData(object);
//        }
//        LogUtils.d(result);
        return  result;
    }
}
