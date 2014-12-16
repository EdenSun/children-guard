package eden.sun.childrenguard.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.EmergencyContactViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IEmergencyContactsService;

@Controller
@RequestMapping("/parent/emergencyContact")
public class EmergencyContactController extends BaseController{
	
	@Autowired
	private IEmergencyContactsService emergencyContactsService;
	
	/**
	 * /parent/emergencyContact/listByChild
	 * 查询person的emergency contacts
	 * @param childId person id
	 * @return 返回emergency contact 列表（List<EmergencyContactViewDTO>）
	 */
	@RequestMapping("/listByChild")
	@ResponseBody
	public ViewDTO<List<EmergencyContactViewDTO>> listByChild(Integer childId){
		logger.info("listByChild. Child ID:" + childId );
		ViewDTO<List<EmergencyContactViewDTO>> view = emergencyContactsService.listViewByChild(childId);
		
		return view;
	}
	
	/**
	 * /parent/emergencyContact/add
	 * 添加emergency contact
	 * @param childId emergency contact 所属的 person 的 id
	 * @param name 紧急联系人姓名
	 * @param phone 紧急联系人电话
	 * @return 返回添加的紧急联系人对象EmergencyContactViewDTO
	 */
	@RequestMapping("/add")
	@ResponseBody
	public ViewDTO<EmergencyContactViewDTO> add(Integer childId,String name,String phone){
		logger.info("add. Child ID:" + childId + ",name:" + name + ",phone:" + phone );
		ViewDTO<EmergencyContactViewDTO> view = emergencyContactsService.add(childId,name,phone);
		
		return view;
	}
	
	/**
	 * /parent/emergencyContact/delete
	 * 删除紧急联系人
	 * @param childId 紧急联系人所属的person 的 id
	 * @param phone 紧急联系人电话
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ViewDTO<Boolean> delete(Integer childId,String phone){
		logger.info("add. Child ID:" + childId + ",phone:" + phone );
		ViewDTO<Boolean> view = emergencyContactsService.delete(childId,phone);
		
		return view;
	}
	
	
}
