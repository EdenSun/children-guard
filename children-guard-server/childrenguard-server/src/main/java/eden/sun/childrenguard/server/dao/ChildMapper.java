package eden.sun.childrenguard.server.dao;

import eden.sun.childrenguard.server.model.Child;
import eden.sun.childrenguard.server.model.ChildExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ChildMapper {
    int countByExample(ChildExample example);

    int deleteByExample(ChildExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Child record);

    int insertSelective(Child record);

    List<Child> selectByExampleWithRowbounds(ChildExample example, RowBounds rowBounds);

    List<Child> selectByExample(ChildExample example);

    Child selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Child record, @Param("example") ChildExample example);

    int updateByExample(@Param("record") Child record, @Param("example") ChildExample example);

    int updateByPrimaryKeySelective(Child record);

    int updateByPrimaryKey(Child record);
}