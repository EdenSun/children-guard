package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.ParentChild;
import eden.sun.childrenguard.server.model.generated.ParentChildExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ParentChildMapper {
    int countByExample(ParentChildExample example);

    int deleteByExample(ParentChildExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ParentChild record);

    int insertSelective(ParentChild record);

    List<ParentChild> selectByExampleWithRowbounds(ParentChildExample example, RowBounds rowBounds);

    List<ParentChild> selectByExample(ParentChildExample example);

    ParentChild selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ParentChild record, @Param("example") ParentChildExample example);

    int updateByExample(@Param("record") ParentChild record, @Param("example") ParentChildExample example);

    int updateByPrimaryKeySelective(ParentChild record);

    int updateByPrimaryKey(ParentChild record);
}