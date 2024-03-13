package com.nowcoder.community.pojo;

//封装分页相关的 信息

public class Page {
    //当前页（默认第1页
    private int current = 1;
    //默认一页装入10条数据
    private int limit = 10;
    //总数据量
    private int rows;
    //查询路径，用于复用分页连接。
    private String path;

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if(current>=1)
            this.current = current;
    }

    public int getLimit() {

            return limit;

    }

    public void setLimit(int limit) {
        if(limit>=1 && limit<=100)
            this.limit = limit;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if(rows>=0)
            this.rows = rows;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    //获取当前页的起始行
    public int getOffSet(){
        return limit * (current-1);
    }

    //获取总页数
    public int getTotal(){
        if (rows % limit == 0){
            return rows / limit;
        }else {
            return rows / limit + 1;
        }
    }

    /*
    * 获取起始页码
    * */
    public int getFrom(){
        //我们在下面分页显示的时候，只显示当前页的前两页可以直接跳转
        int from= current-2;
        //在返回时判断一下from是否为负数，如果是直接返回1，不是则返回from
        return from < 1 ? 1 : from;
    }
    /*
    * 获取结束页码
    * */
    public int getTo(){
        int to =current+2;
        int total=getTotal();
        return to > total ? total : to;
    }
}
