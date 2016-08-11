package com.smily.dbserv.mapper;

import com.smily.dbserv.bean.TestTable;
import com.smily.dbserv.bean.TestTableExample;
import com.smily.mybatis.dbserv.TableMapper;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestTableMapper extends TableMapper {
    int countByExample(TestTableExample example);

    int deleteByExample(TestTableExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TestTable record);

    int insertSelective(TestTable record);

    List<TestTable> selectByExample(TestTableExample example);

    TestTable selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TestTable record, @Param("example") TestTableExample example);

    int updateByExample(@Param("record") TestTable record, @Param("example") TestTableExample example);

    int updateByPrimaryKeySelective(TestTable record);

    int updateByPrimaryKey(TestTable record);
}