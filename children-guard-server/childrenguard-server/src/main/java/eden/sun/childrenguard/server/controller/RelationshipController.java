package eden.sun.childrenguard.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IRelationshipService;

@Controller
@RequestMapping("/parent/relationship")
public class RelationshipController extends BaseController{

	@Autowired
	private IRelationshipService relationshipService;
	
	/**
	 * /parent/relationship/listAll
	 * ��ѯ���й�ϵ�б�person �� parent �Ĺ�ϵ��
	 * @return ���ع�ϵ�б�(List<RelationshipViewDTO>)
	 */
	@RequestMapping("/listAll")
	@ResponseBody
	public ViewDTO<List<RelationshipViewDTO>> listAll(){
		logger.info("List all relationship.");
		
		ViewDTO<List<RelationshipViewDTO>> view = relationshipService.listAll();
		
		return view;
	}
}
