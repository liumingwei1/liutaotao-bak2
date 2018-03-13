package com.taotao.manage.service;

import com.github.abel533.entity.Example;
import com.github.abel533.mapper.Mapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.manage.pojo.BasePojo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public abstract class BaseService<T extends BasePojo> {
    /*
        1、queryById
    2、queryAll
    3、queryOne
    4、queryListByWhere
    5、queryPageListByWhere
    6、save
    7、update
    8、deleteById
    9、deleteByIds
                deleteByWhere
    */
  /*  public abstract Mapper<T> mapper;*/
    @Autowired
    private Mapper<T> mapper;

    /*
    * 根据主键ID查询
    * */
    public T queryById(Long id) {
        return this.mapper.selectByPrimaryKey(id);
    }

    /*
    * 查询所有数据
    * */
    public List<T> queryAll() {
        return this.mapper.select(null);
    }

    /*根据条件查询一条数据，如果有多条数据会抛出异常
    *
    * */
    public T queryOne(T record) {
        return this.mapper.selectOne(record);
    }

    /*
    * 查询数据列表
    * */
    public List<T> queryListByWhere(T record) {
        return this.mapper.select(record);
    }

    /*
    * 根据分页查询
    * */
    public PageInfo<T> queryPageListByWhere(T record, Integer page, Integer rows) {
        PageHelper.startPage(page, rows);
        List<T> list = this.queryListByWhere(record);
        return new PageInfo<T>(list);
    }

    /*
    * 新增数据
    * */
    public Integer save(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insert(t);
    }

    /*
    *  新增数据,选着不为null的字段
    * */
    public Integer saveSelective(T t) {
        t.setCreated(new Date());
        t.setUpdated(t.getCreated());
        return this.mapper.insertSelective(t);
    }

    /*
   * 更新数据
   * */
    public Integer update(T t) {
        t.setCreated(new Date());
        return this.mapper.updateByPrimaryKey(t);
    }

    /*
    *  更新数据,选着不为null的字段
    * */
    public Integer updateSelective(T t) {
        t.setCreated(new Date());
        t.setUpdated(null);
        return this.mapper.updateByPrimaryKeySelective(t);
    }

    /*
    * 根据主键删除ID
    * */
    public Integer deleteById(Long id) {
        return this.mapper.deleteByPrimaryKey(id);
    }

    /*
    * 批量删除
    * */
    public Integer deleteByIds(List<Object> ids, Class<T> clazz, String prperty) {
        Example example = new Example(clazz);
        example.createCriteria().andIn(prperty, ids);
        return this.mapper.deleteByExample(example);
    }

    /*
    * 根据条件删除
    * */
    public Integer deleteByWhere(T record) {
        return this.mapper.delete(record);
    }
}
