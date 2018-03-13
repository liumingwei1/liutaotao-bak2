package com.taotao.manage.mapper;

import com.github.abel533.mapper.Mapper;
import com.taotao.manage.pojo.Content;
import com.taotao.manage.pojo.ContentCategory;

import java.util.List;

public interface ContentMapper extends Mapper<Content> {
    /*根據categoryID查詢類容列表，更新時間倒序排序
    *
    * */
    public List<Content> queryContentList(Long categoryId);
}
