package cn.source.new_class_system.base.query;

import lombok.Data;

@Data
public class PagingQuery {
    private Integer pageNum;

    private Integer pageSize;

    private Long totalPage;

}
