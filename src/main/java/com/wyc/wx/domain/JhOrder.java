package com.wyc.wx.domain;

public class JhOrder {
    private String resultcode;
    private String reason;
    private JhOrderResult result;
    private String error_code;
    public String getError_code() {
        return error_code;
    }
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }
    public String getResultcode() {
        return resultcode;
    }
    public void setResultcode(String resultcode) {
        this.resultcode = resultcode;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public JhOrderResult getResult() {
        return result;
    }
    public void setResult(JhOrderResult result) {
        this.result = result;
    }
}
