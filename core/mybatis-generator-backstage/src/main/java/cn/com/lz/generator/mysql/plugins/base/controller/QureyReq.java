package cn.com.lz.generator.mysql.plugins.base.controller;

public class QureyReq<Q,R> {

    private Q query;

    private R req;

    public Q getQuery() {
        return query;
    }

    public void setQuery(Q query) {
        this.query = query;
    }

    public R getReq() {
        return req;
    }

    public void setReq(R req) {
        this.req = req;
    }
}
