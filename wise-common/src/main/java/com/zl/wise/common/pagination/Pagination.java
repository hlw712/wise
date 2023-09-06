package com.zl.wise.common.pagination;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class Pagination <T> implements Serializable {
    @Min(0L)
    private long totalElements;
    @Min(0L)
    private long totalPages;
    @NotNull
    @Valid
    private SortPageParam sortPageParam;
    @NotNull
    private List<T> items;

    public Pagination(@Min(0L) long totalElements, @Min(0L) long totalPages, @NotNull @Valid SortPageParam sortPageParam, @NotNull List<T> items) {
        this.totalElements = totalElements;
        this.totalPages = totalPages;
        this.sortPageParam = sortPageParam;
        this.items = items;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public long getTotalPages() {
        return this.totalPages;
    }

    public SortPageParam getSortPageParam() {
        return this.sortPageParam;
    }

    public List<T> getItems() {
        return this.items;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public void setSortPageParam(SortPageParam sortPageParam) {
        this.sortPageParam = sortPageParam;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

}
