package eden.sun.childrenguard.server.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.RelationshipViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IRelationshipService;

@Controller
@RequestMapping("/relationship")
public class RelationshipController extends BaseController{

	@Inject
	private IRelationshipService relationshipService;
	
	@RequestMapping("/listAll")
	@ResponseBody
	public ViewDTO<List<RelationshipViewDTO>> listAll(){
		logger.info("List all relationship.");
		
		ViewDTO<List<RelationshipViewDTO>> view = relationshipService.listAll();
		
		return view;
	}
}
