package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.ChildExtraInfo;
import eden.sun.childrenguard.server.model.generated.ChildExtraInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ChildExtraInfoMapper {
    int countByExample(ChildExtraInfoExample example);

    int deleteByExample(ChildExtraInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChildExtraInfo record);

    int insertSelective(ChildExtraInfo record);

    List<ChildExtraInfo> selectByExampleWithRowbounds(ChildExtraInfoExample example, RowBounds rowBounds);

    List<ChildExtraInfo> selectByExample(ChildExtraInfoExample example);

    ChildExtraInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChildExtraInfo record, @Param("example") ChildExtraInfoExample example);

    int updateByExample(@Param("record") ChildExtraInfo record, @Param("example") ChildExtraInfoExample example);

    int updateByPrimaryKeySelective(ChildExtraInfo record);

    int updateByPrimaryKey(ChildExtraInfo record);
}