package com.memoforward.blog.service;

import com.memoforward.blog.dto.PageDto;
import com.memoforward.blog.model.Type;

import java.util.List;


public interface ITypeService {

    PageDto<Type> listTypes(Integer pageNum, Integer pageLimit);

    Type findTypeByTypeName(String newType);

    void saveType(String newType);

    void removeTypeById(Long typeId);

    List<Type> listAllTypes();
}
