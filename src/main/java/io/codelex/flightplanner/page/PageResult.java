package io.codelex.flightplanner.page;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {

   private Integer page;

   private Integer totalItems;

   private List<T> items;

    public PageResult(Integer page, Integer totalItems, List<T> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
