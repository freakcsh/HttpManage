package com.freak.httpmanage.bean;

import org.json.JSONObject;

/**
 *
 * @author Administrator
 * @date 2018/4/10
 */

public class BaseBean<T> {

    private String respCode;

    private String respDesc;

    private T respData;

    private JSONObject jsonObject;
//
    private String errorJson;

    public String getRespCode() {
        return respCode == null ? "" : respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getRespDesc() {
        return respDesc == null ? "" : respDesc;
    }

    public void setRespDesc(String respDesc) {
        this.respDesc = respDesc;
    }

    public T getRespData() {
        return respData;
    }

    public void setRespData(T respData) {
        this.respData = respData;
    }


    public JSONObject getJsonObject() {
        return jsonObject == null ? new JSONObject() : jsonObject;
    }

    public void setJsonObject(JSONObject jsonObject) {
        this.jsonObject = jsonObject;
    }

    public String getErrorJson() {
        return errorJson == null ? "" : errorJson;
    }

    public void setErrorJson(String errorJson) {
        this.errorJson = errorJson;
    }

    @Override
    public String toString() {
        if (jsonObject == null) {
            return "BaseBean{" +
                    "respCode='" + respCode + '\'' +
                    ", respDesc='" + respDesc + '\'' +
                    ", respData=" + respData +
                    ", errorJson=" + errorJson +
                    '}';
        }
        return "BaseBean{" +
                "respCode='" + respCode + '\'' +
                ", respDesc='" + respDesc + '\'' +
                ", respData=" + respData +
                ", jsonObject=" + jsonObject.toString() +
                '}';
    }
}
