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
	 * ��ѯperson��emergency contacts
	 * @param childId person id
	 * @return ����emergency contact �б�List<EmergencyContactViewDTO>��
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
	 * ���emergency contact
	 * @param childId emergency contact ������ person �� id
	 * @param name ������ϵ������
	 * @param phone ������ϵ�˵绰
	 * @return ������ӵĽ�����ϵ�˶���EmergencyContactViewDTO
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
	 * ɾ��������ϵ��
	 * @param childId ������ϵ��������person �� id
	 * @param phone ������ϵ�˵绰
	 * @return �ɹ�����true�����򷵻�false
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ViewDTO<Boolean> delete(Integer childId,String phone){
		logger.info("add. Child ID:" + childId + ",phone:" + phone );
		ViewDTO<Boolean> view = emergencyContactsService.delete(childId,phone);
		
		return view;
	}
	
	
}
