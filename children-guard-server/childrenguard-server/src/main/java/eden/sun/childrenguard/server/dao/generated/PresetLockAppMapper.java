package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.PresetLockApp;
import eden.sun.childrenguard.server.model.generated.PresetLockAppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PresetLockAppMapper {
    int countByExample(PresetLockAppExample example);

    int deleteByExample(PresetLockAppExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PresetLockApp record);

    int insertSelective(PresetLockApp record);

    List<PresetLockApp> selectByExampleWithRowbounds(PresetLockAppExample example, RowBounds rowBounds);

    List<PresetLockApp> selectByExample(PresetLockAppExample example);

    PresetLockApp selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PresetLockApp record, @Param("example") PresetLockAppExample example);

    int updateByExample(@Param("record") PresetLockApp record, @Param("example") PresetLockAppExample example);

    int updateByPrimaryKeySelective(PresetLockApp record);

    int updateByPrimaryKey(PresetLockApp record);
}