package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.PresetLock;
import eden.sun.childrenguard.server.model.generated.PresetLockExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PresetLockMapper {
    int countByExample(PresetLockExample example);

    int deleteByExample(PresetLockExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PresetLock record);

    int insertSelective(PresetLock record);

    List<PresetLock> selectByExampleWithRowbounds(PresetLockExample example, RowBounds rowBounds);

    List<PresetLock> selectByExample(PresetLockExample example);

    PresetLock selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PresetLock record, @Param("example") PresetLockExample example);

    int updateByExample(@Param("record") PresetLock record, @Param("example") PresetLockExample example);

    int updateByPrimaryKeySelective(PresetLock record);

    int updateByPrimaryKey(PresetLock record);
}