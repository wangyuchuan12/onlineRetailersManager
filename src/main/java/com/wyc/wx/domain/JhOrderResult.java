package com.wyc.wx.domain;

import java.util.List;

public class JhOrderResult {
    private String company;
    private String com;
    private String no;
    private List<JhOrderResultRecord> list;
    private String status;
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getCom() {
        return com;
    }
    public void setCom(String com) {
        this.com = com;
    }
    public String getNo() {
        return no;
    }
    public void setNo(String no) {
        this.no = no;
    }
    public List<JhOrderResultRecord> getList() {
        return list;
    }
    public void setList(List<JhOrderResultRecord> list) {
        this.list = list;
    }
}
