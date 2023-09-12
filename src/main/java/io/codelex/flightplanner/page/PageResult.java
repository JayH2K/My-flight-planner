package io.codelex.flightplanner.page;

import java.util.ArrayList;
import java.util.List;

public class PageResult<T> {

    Integer page;

    Integer totalItems;

    List<T> items;

    public PageResult(Integer page, Integer totalItems, List<T> items) {
        this.page = page;
        this.totalItems = totalItems;
        this.items = items;
    }
}
