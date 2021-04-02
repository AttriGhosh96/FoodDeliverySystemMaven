package com.xav.pojo;

import com.xav.receivedData.Order;

public class GapedOrder {

    private Order order;
    private int gap;

    public GapedOrder(Order order, int gap) {
        this.order = order;
        this.gap = gap;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
        return getOrder() != null ? getOrder().equals(that.getOrder()) : that.getOrder() == null;
    }

    @Override
    public int hashCode() {
        int result = getOrder() != null ? getOrder().hashCode() : 0;
        result = 31 * result + getGap();
        return result;
    }

    @Override
    public String toString() {
        return "GapedOrder{" +
                "order=" + order +
                ", gap=" + gap +
                '}';
    }
}
