package com.memoforward.blog.service.impl;

import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.enums.BlogError;
import com.memoforward.blog.exception.CustomizedBlogException;
import com.memoforward.blog.mapper.BlogWithTypeMapper;
import com.memoforward.blog.mapper.TypeMapper;
import com.memoforward.blog.model.BlogWithTypeExample;
import com.memoforward.blog.model.Type;
import com.memoforward.blog.model.TypeExample;
import com.memoforward.blog.service.ITypeService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements ITypeService {

    @Autowired
    TypeMapper typeMapper;

    @Autowired
    BlogWithTypeMapper blogWithTypeMapper;

    @Override
    public PageDto<Type> listTypes(Integer pageNum, Integer pageLimit) {
        PageDto<Type> pageDto = new PageDto<>();
        int totalNums = (int)typeMapper.countByExample(new TypeExample());
        pageDto.setTotalNums(totalNums);
        int _totalPage = totalNums / pageLimit;
        int totalPage = totalNums % pageLimit == 0 ? _totalPage : _totalPage + 1;
        pageDto.setTotalPage(totalPage);
        if(pageNum < 1) pageNum = 1;
        if(pageNum > totalPage) pageNum = totalPage;
        pageDto.setCurrentPage(pageNum);
        int offset = (pageNum - 1) * pageLimit;
        List<Type> typeList = typeMapper.selectByExampleWithRowbounds(new TypeExample(), new RowBounds(offset, pageLimit));
        pageDto.setData(typeList);
        return pageDto;
    }

    @Override
    public Type findTypeByTypeName(String newType) {
        TypeExample typeExample = new TypeExample();
        typeExample.createCriteria().andNameEqualTo(newType);
        List<Type> typeList = typeMapper.selectByExample(typeExample);
        if(typeList.size() == 0) return null;
        else return typeList.get(0);
    }

    @Override
    public void saveType(String newType) {
        Type record = new Type();
        record.setName(newType);
        typeMapper.insert(record);
    }

    @Override
    public void removeTypeById(Long typeId) {
        typeMapper.deleteByPrimaryKey(typeId);
        BlogWithTypeExample blogWithTypeExample = new BlogWithTypeExample();
        blogWithTypeExample.createCriteria().andTypeIdEqualTo(typeId);
        blogWithTypeMapper.deleteByExample(blogWithTypeExample);
    }

    @Override
    public List<Type> listAllTypes() {
        return typeMapper.selectByExample(new TypeExample());
    }
}
