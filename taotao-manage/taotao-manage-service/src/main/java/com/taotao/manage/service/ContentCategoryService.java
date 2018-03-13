package com.taotao.manage.service;

import com.taotao.manage.pojo.ContentCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ContentCategoryService extends BaseService<ContentCategory> {
    public void saveContentCategory(ContentCategory contentCategory) {
        contentCategory.setId(null);
        contentCategory.setIsParent(false);
        contentCategory.setStatus(1);
        contentCategory.setSortOrder(1);
        super.save(contentCategory);
        //判斷該節點的父節點的isparentID是否為true
        ContentCategory parent = super.queryById(contentCategory.getParentId());
        if (!parent.getIsParent()) {
            parent.setIsParent(true);
            super.update(parent);
        }
    }

    public void deleteAll(ContentCategory contentCategory) {
        List<Object> ids = new ArrayList<Object>();
        ids.add(contentCategory.getId());
        //遞歸查找節點下的所有節點ID
        this.findAllSubNode(ids,contentCategory.getId());
        super.deleteByIds(ids,ContentCategory.class,"id");
        //判斷該節點是否有兄弟節點，如果沒有，修改父節點IDparent為否
        ContentCategory record = new ContentCategory();
        record.setParentId(contentCategory.getParentId());
        List<ContentCategory> list = super.queryListByWhere(record);
        if (null == list&& list.isEmpty()){
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategory.getParentId());
            parent.setIsParent(false);
            super.updateSelective(parent);
        }
    }
    private void findAllSubNode( List<Object> ids,Long pid){
        ContentCategory record = new ContentCategory();
        record.setParentId(pid);
      List<ContentCategory> list = super.queryListByWhere(record);
      for (ContentCategory contentCategory :list){
          ids.add(contentCategory.getId());
          //判斷該節點是否為父節點，如果是，繼續查找該節點下的子節點
          if (contentCategory.getIsParent()){
              //開始遞歸
              findAllSubNode(ids,contentCategory.getId());
          }
      }
    }
}
