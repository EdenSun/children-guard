package eden.sun.childrenguard.server.dao.generated;

import eden.sun.childrenguard.server.model.generated.Relationship;
import eden.sun.childrenguard.server.model.generated.RelationshipExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface RelationshipMapper {
    int countByExample(RelationshipExample example);

    int deleteByExample(RelationshipExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Relationship record);

    int insertSelective(Relationship record);

    List<Relationship> selectByExampleWithRowbounds(RelationshipExample example, RowBounds rowBounds);

    List<Relationship> selectByExample(RelationshipExample example);

    Relationship selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Relationship record, @Param("example") RelationshipExample example);

    int updateByExample(@Param("record") Relationship record, @Param("example") RelationshipExample example);

    int updateByPrimaryKeySelective(Relationship record);

    int updateByPrimaryKey(Relationship record);
}