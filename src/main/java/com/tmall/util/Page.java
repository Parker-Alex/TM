package com.tmall.util;

public class Page {

    private static final int DEFAULT_COUNT = 5;
    private int start;
    private int count;
    private int total;
    private String param;

    public Page() {
        count = DEFAULT_COUNT;
    }

    public Page(int start, int count) {
        this.start = start;
        this.count = count;
    }

    public boolean hasPreviouse() {
        if (start == 0)
            return false;
        return true;
    }

    public boolean hasNext() {
        return start != getLast();
    }

    public int getLast() {
        int last;
        if (total % count == 0)
            last = total - count;
        else
            last = total - total % count;
        return last = last < 0 ? 0 : last;
    }

    public int getTotalPage() {
        int totalPage;
        if (total % count == 0)
            totalPage = total / count;
        else
            totalPage = total / count + 1;
        return totalPage = totalPage == 0 ? 1 : totalPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    @Override
    public String toString() {
        return "Page [start=" + start + ", count=" + count + ", total=" + total + ", getStart()=" + getStart()
                + ", getCount()=" + getCount() + ", isHasPreviouse()=" + hasPreviouse() + ", isHasNext()="
                + hasNext() + ", getTotalPage()=" + getTotalPage() + ", getLast()=" + getLast() + "]";
    }
}
