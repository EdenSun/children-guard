package eden.sun.childrenguard.server.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import eden.sun.childrenguard.server.model.ChildOfParents;

public interface ChildOfParentsMapper {

    List<ChildOfParents> selectAllByParentId(@Param("parentId") Integer parentId);

	/*List<ChildOfParents> selectByChildIdAndParentId(@Param("childId")Integer childId,
			@Param("parentId")Integer parentId);*/

}