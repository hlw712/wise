package com.zl.wise.common.pagination;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public <U> Pagination<U> map(Function<? super T, ? extends U> converter) {
        Pagination<U> pagination = new Pagination();
        List<U> convertedContent = this.getConvertedContent(converter);
        pagination.setItems(convertedContent);
        pagination.setTotalElements(this.getTotalElements());
        pagination.setTotalPages(this.getTotalPages());
        pagination.setSortPageParam(this.getSortPageParam());
        return pagination;
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        if (this.items == null) {
            return null;
        } else {
            Stream var10000 = this.items.stream();
            converter.getClass();
            return (List)var10000.map(converter::apply).collect(Collectors.toList());
        }
    }

    protected Pagination(Pagination.PaginationBuilder<T, ?, ?> b) {
        this.totalElements = b.totalElements;
        this.totalPages = b.totalPages;
        this.sortPageParam = b.sortPageParam;
        this.items = b.items;
    }

    public static <T> Pagination.PaginationBuilder<T, ?, ?> builder() {
        return new Pagination.PaginationBuilderImpl();
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

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof Pagination)) {
            return false;
        } else {
            Pagination<?> other = (Pagination)o;
            if (!other.canEqual(this)) {
                return false;
            } else if (this.getTotalElements() != other.getTotalElements()) {
                return false;
            } else if (this.getTotalPages() != other.getTotalPages()) {
                return false;
            } else {
                label40: {
                    Object this$sortPageParam = this.getSortPageParam();
                    Object other$sortPageParam = other.getSortPageParam();
                    if (this$sortPageParam == null) {
                        if (other$sortPageParam == null) {
                            break label40;
                        }
                    } else if (this$sortPageParam.equals(other$sortPageParam)) {
                        break label40;
                    }

                    return false;
                }

                Object this$items = this.getItems();
                Object other$items = other.getItems();
                if (this$items == null) {
                    if (other$items != null) {
                        return false;
                    }
                } else if (!this$items.equals(other$items)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof Pagination;
    }

    public int hashCode() {
        int PRIME = true;
        int result = 1;
        long $totalElements = this.getTotalElements();
        int result = result * 59 + (int)($totalElements >>> 32 ^ $totalElements);
        long $totalPages = this.getTotalPages();
        result = result * 59 + (int)($totalPages >>> 32 ^ $totalPages);
        Object $sortPageParam = this.getSortPageParam();
        result = result * 59 + ($sortPageParam == null ? 43 : $sortPageParam.hashCode());
        Object $items = this.getItems();
        result = result * 59 + ($items == null ? 43 : $items.hashCode());
        return result;
    }

    public String toString() {
        return "Pagination(totalElements=" + this.getTotalElements() + ", totalPages=" + this.getTotalPages() + ", sortPageParam=" + this.getSortPageParam() + ", items=" + this.getItems() + ")";
    }

    public Pagination() {
    }

    private static final class PaginationBuilderImpl<T> extends Pagination.PaginationBuilder<T, Pagination<T>, Pagination.PaginationBuilderImpl<T>> {
        private PaginationBuilderImpl() {
        }

        protected Pagination.PaginationBuilderImpl<T> self() {
            return this;
        }

        public Pagination<T> build() {
            return new Pagination(this);
        }
    }

    public abstract static class PaginationBuilder<T, C extends Pagination<T>, B extends Pagination.PaginationBuilder<T, C, B>> {
        private long totalElements;
        private long totalPages;
        private SortPageParam sortPageParam;
        private List<T> items;

        public PaginationBuilder() {
        }

        protected abstract B self();

        public abstract C build();

        public B totalElements(long totalElements) {
            this.totalElements = totalElements;
            return this.self();
        }

        public B totalPages(long totalPages) {
            this.totalPages = totalPages;
            return this.self();
        }

        public B sortPageParam(SortPageParam sortPageParam) {
            this.sortPageParam = sortPageParam;
            return this.self();
        }

        public B items(List<T> items) {
            this.items = items;
            return this.self();
        }

        public String toString() {
            return "Pagination.PaginationBuilder(totalElements=" + this.totalElements + ", totalPages=" + this.totalPages + ", sortPageParam=" + this.sortPageParam + ", items=" + this.items + ")";
        }
    }
}
