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
import eden.sun.childrenguard.server.dto.param.PresetLockParam;
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
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> delete(Integer childId,Integer presetLockId){
		logger.info("delete called. childId:" + childId  + ",presetLockId:" + presetLockId);
		
		ViewDTO<PresetLockViewDTO> view = presetLockService.delete(childId,presetLockId);
		return view;
	}
	
	
	/**
	 * /parent/presetlock/loadPresetLockData
	 * 获取person 预设锁定的数据
	 * @param childId person id
	 * @return 返回预设锁定对象 PresetLockViewDTO
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
	 * 应用预设锁定
	 * @param childId person id
	 * @param applyPresetLockParamJson 预设锁定设置json(ApplyPresetLockParam)
	 * @return 成功返回true，否则返回false
	 */
	@RequestMapping("/applyPresetLock")
	@ResponseBody
	public ViewDTO<Boolean> applyPresetLock(Integer presetLockId,String applyPresetLockParamJson){
		logger.info("applyPresetLock called. presetLockId:" + presetLockId + " ,applyPresetLockParam:" + applyPresetLockParamJson );
		
		PresetLockParam applyPresetLockParam = JSONUtil.getApplyPresetLockParam(applyPresetLockParamJson);
		ViewDTO<Boolean> view = presetLockService.applyPresetLock(presetLockId,applyPresetLockParam);
		
		return view;
		
	}
	
	/**
	 * /parent/presetlock/listChildPresetLockApp
	 * 获取person的预设锁定中的app列表()
	 * @param childId person id
	 * @return 返回预设锁定app列表（List<AppViewDTO>）
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
	 * 获取person 预设锁定的数据
	 * @param childId person id
	 * @return 返回预设锁定对象 PresetLockViewDTO
	 */
	@RequestMapping("/loadPresetLockById")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> loadPresetLockById(Integer presetLockId){
		logger.info("loadPresetLockById called. presetLockId:" + presetLockId );
		
		ViewDTO<PresetLockViewDTO> view = presetLockService.loadPresetLockById(presetLockId);
		
		return view;
		
	}
	
	
	@RequestMapping("/newPresetLock")
	@ResponseBody
	public ViewDTO<PresetLockViewDTO> newPresetLock(String applyPresetLockParamJson){
		logger.info("newPresetLock called. applyPresetLockParam:" + applyPresetLockParamJson );
		
		PresetLockParam applyPresetLockParam = JSONUtil.getApplyPresetLockParam(applyPresetLockParamJson);
		ViewDTO<PresetLockViewDTO> view = presetLockService.newPresetLock(applyPresetLockParam);
		
		return view;
		
	}
	
	
}
