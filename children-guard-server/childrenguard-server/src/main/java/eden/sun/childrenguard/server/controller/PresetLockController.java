package eden.sun.childrenguard.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.AppViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockListItemViewDTO;
import eden.sun.childrenguard.server.dto.PresetLockViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ApplyPresetLockParam;
import eden.sun.childrenguard.server.service.IChildDetailService;
import eden.sun.childrenguard.server.service.IPresetLockService;
import eden.sun.childrenguard.server.util.JSONUtil;

@Controller
@RequestMapping("/parent/presetlock")
public class PresetLockController extends BaseController{
	@Autowired
	private IPresetLockService presetLockService;
	
	@Autowired
	private IChildDetailService childDetailService;
	
	@RequestMapping("/list")
	@ResponseBody
	public ViewDTO<List<PresetLockListItemViewDTO>> list(Integer childId){
		logger.info("list called. childId:" + childId );
		
		ViewDTO<List<PresetLockListItemViewDTO>> view = presetLockService.listScheduleLock(childId);
		return view;
	}
	
	
	@RequestMapping("/batchdelete")
	@ResponseBody
	public ViewDTO<Boolean> batchdelete(Integer[] ids){
		logger.info("batchdelete called. idList:" + ids );
		
		ViewDTO<Boolean> view = presetLockService.batchDelete(ids);
		return view;
	}
	
	
	/**
	 * /parent/presetlock/loadPresetLockData
	 * ��ȡperson Ԥ������������
	 * @param childId person id
	 * @return ����Ԥ���������� PresetLockViewDTO
	 */
	@RequestMapping("/loadPresetLockData")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> loadPresetLockData(Integer childId){
		logger.info("loadPresetLockData called. childId:" + childId );
		
		ViewDTO<PresetLockViewDTO> view = childDetailService.loadPresetLockData(childId);
		
		return view;
		
	}
	
	/**
	 * /parent/presetlock/applyPresetLock
	 * Ӧ��Ԥ������
	 * @param childId person id
	 * @param applyPresetLockParamJson Ԥ����������json(ApplyPresetLockParam)
	 * @return �ɹ�����true�����򷵻�false
	 */
	@RequestMapping("/applyPresetLock")
	@ResponseBody
	public ViewDTO<Boolean> applyPresetLock(Integer childId,String applyPresetLockParamJson){
		logger.info("applyPresetLock called. childId:" + childId + " ,applyPresetLockParam:" + applyPresetLockParamJson );
		
		ApplyPresetLockParam applyPresetLockParam = JSONUtil.getApplyPresetLockParam(applyPresetLockParamJson);
		ViewDTO<Boolean> view = childDetailService.applyPresetLock(childId,applyPresetLockParam);
		
		return view;
		
	}
	
	/**
	 * /parent/presetlock/listChildPresetLockApp
	 * ��ȡperson��Ԥ�������е�app�б�()
	 * @param childId person id
	 * @return ����Ԥ������app�б�List<AppViewDTO>��
	 */
	@RequestMapping("/listChildPresetLockApp")
	@ResponseBody
	public ViewDTO<List<AppViewDTO>> listChildPresetLockApp(Integer childId) {
		logger.info("listChildPresetLockApp called. childId:" + childId);
		ViewDTO<List<AppViewDTO>> view = childDetailService.listChildPresetLockApp(childId);
		
		return view;
	}
	
	
	/**
	 * /parent/presetlock/loadPresetLockData
	 * ��ȡperson Ԥ������������
	 * @param childId person id
	 * @return ����Ԥ���������� PresetLockViewDTO
	 */
	@RequestMapping("/loadPresetLockById")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> loadPresetLockById(Integer presetLockId){
		logger.info("loadPresetLockById called. presetLockId:" + presetLockId );
		
		ViewDTO<PresetLockViewDTO> view = presetLockService.loadPresetLockById(presetLockId);
		
		return view;
		
	}
	
}
