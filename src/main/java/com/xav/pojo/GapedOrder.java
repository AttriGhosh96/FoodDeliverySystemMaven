package com.xav.pojo;

import com.xav.receivedData.Order;

public class GapedOrder {

    private Order oder;
    private int gap;

    public GapedOrder(Order oder, int gap) {
        this.oder = oder;
        this.gap = gap;
    }

    public Order getOder() {
        return oder;
    }

    public void setOder(Order oder) {
        this.oder = oder;
    }

    public int getGap() {
        return gap;
    }

    public void setGap(int gap) {
        this.gap = gap;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GapedOrder)) return false;

        GapedOrder that = (GapedOrder) o;

        if (getGap() != that.getGap()) return false;
        return getOder() != null ? getOder().equals(that.getOder()) : that.getOder() == null;
    }

    @Override
    public int hashCode() {
        int result = getOder() != null ? getOder().hashCode() : 0;
        result = 31 * result + getGap();
        return result;
    }
}
