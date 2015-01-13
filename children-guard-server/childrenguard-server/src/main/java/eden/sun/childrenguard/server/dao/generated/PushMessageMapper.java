package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.PushMessage;
import eden.sun.childrenguard.server.model.generated.PushMessageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PushMessageMapper {
    int countByExample(PushMessageExample example);

    int deleteByExample(PushMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PushMessage record);

    int insertSelective(PushMessage record);

    List<PushMessage> selectByExampleWithBLOBsWithRowbounds(PushMessageExample example, RowBounds rowBounds);

    List<PushMessage> selectByExampleWithBLOBs(PushMessageExample example);

    List<PushMessage> selectByExampleWithRowbounds(PushMessageExample example, RowBounds rowBounds);

    List<PushMessage> selectByExample(PushMessageExample example);

    PushMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PushMessage record, @Param("example") PushMessageExample example);

    int updateByExampleWithBLOBs(@Param("record") PushMessage record, @Param("example") PushMessageExample example);

    int updateByExample(@Param("record") PushMessage record, @Param("example") PushMessageExample example);

    int updateByPrimaryKeySelective(PushMessage record);

    int updateByPrimaryKeyWithBLOBs(PushMessage record);

    int updateByPrimaryKey(PushMessage record);
}