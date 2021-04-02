package com.xav.pojo;

import java.util.List;

public class ResultPath {
    private List<Location> path;

    public ResultPath(List<Location> path) {
        this.path = path;
    }

    public List<Location> getPath() {
        return path;
    }

    public void setPath(List<Location> path) {
        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResultPath)) return false;

        ResultPath that = (ResultPath) o;

        return path != null ? path.equals(that.path) : that.path == null;
    }

    @Override
    public int hashCode() {
        return path != null ? path.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "ResultPath{" +
                "path=" + path +
                '}';
    }
}
