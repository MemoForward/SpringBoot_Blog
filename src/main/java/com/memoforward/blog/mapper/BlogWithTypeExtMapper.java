package com.memoforward.blog.mapper;


import com.memoforward.blog.dto.TypeDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogWithTypeExtMapper {

    List<TypeDto> listTopTypes(@Param("limit") Integer limit);

}