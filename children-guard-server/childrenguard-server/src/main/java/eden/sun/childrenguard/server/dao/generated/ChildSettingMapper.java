package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.ChildSetting;
import eden.sun.childrenguard.server.model.generated.ChildSettingExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface ChildSettingMapper {
    int countByExample(ChildSettingExample example);

    int deleteByExample(ChildSettingExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ChildSetting record);

    int insertSelective(ChildSetting record);

    List<ChildSetting> selectByExampleWithRowbounds(ChildSettingExample example, RowBounds rowBounds);

    List<ChildSetting> selectByExample(ChildSettingExample example);

    ChildSetting selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") ChildSetting record, @Param("example") ChildSettingExample example);

    int updateByExample(@Param("record") ChildSetting record, @Param("example") ChildSettingExample example);

    int updateByPrimaryKeySelective(ChildSetting record);

    int updateByPrimaryKey(ChildSetting record);
}