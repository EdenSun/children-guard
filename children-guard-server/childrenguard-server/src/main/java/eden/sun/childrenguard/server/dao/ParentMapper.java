package eden.sun.childrenguard.server.dao;

import eden.sun.childrenguard.server.model.Parent;
import eden.sun.childrenguard.server.model.ParentExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParentMapper {
    int countByExample(ParentExample example);

    int deleteByExample(ParentExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Parent record);

    int insertSelective(Parent record);

    List<Parent> selectByExampleWithRowbounds(ParentExample example, RowBounds rowBounds);

    List<Parent> selectByExample(ParentExample example);

    Parent selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Parent record, @Param("example") ParentExample example);

    int updateByExample(@Param("record") Parent record, @Param("example") ParentExample example);

    int updateByPrimaryKeySelective(Parent record);

    int updateByPrimaryKey(Parent record);
}