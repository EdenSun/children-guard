package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.EmergencyContacts;
import eden.sun.childrenguard.server.model.generated.EmergencyContactsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface EmergencyContactsMapper {
    int countByExample(EmergencyContactsExample example);

    int deleteByExample(EmergencyContactsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EmergencyContacts record);

    int insertSelective(EmergencyContacts record);

    List<EmergencyContacts> selectByExampleWithRowbounds(EmergencyContactsExample example, RowBounds rowBounds);

    List<EmergencyContacts> selectByExample(EmergencyContactsExample example);

    EmergencyContacts selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EmergencyContacts record, @Param("example") EmergencyContactsExample example);

    int updateByExample(@Param("record") EmergencyContacts record, @Param("example") EmergencyContactsExample example);

    int updateByPrimaryKeySelective(EmergencyContacts record);

    int updateByPrimaryKey(EmergencyContacts record);
}