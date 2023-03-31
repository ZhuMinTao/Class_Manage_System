package cn.source.new_class_system.base.result;

import lombok.Data;

import java.util.List;

@Data
public class PagingParam<T> {
    private Integer pageNum;

    private Integer pageSize;

    private Long totalPage;

    private List<T> pageList;
}
