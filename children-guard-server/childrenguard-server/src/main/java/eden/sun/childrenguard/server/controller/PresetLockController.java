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
import eden.sun.childrenguard.server.service.IScheduleLockService;
import eden.sun.childrenguard.server.util.JSONUtil;

@Controller
@RequestMapping("/parent/presetlock")
public class PresetLockController extends BaseController{
	@Autowired
	private IScheduleLockService scheduleLockService;
	
	@Autowired
	private IChildDetailService childDetailService;
	
	@RequestMapping("/list")
	@ResponseBody
	public ViewDTO<List<PresetLockListItemViewDTO>> list(Integer childId){
		logger.info("list called. childId:" + childId );
		
		ViewDTO<List<PresetLockListItemViewDTO>> view = scheduleLockService.listScheduleLock(childId);
		return view;
	}
	
	
	@RequestMapping("/batchdelete")
	@ResponseBody
	public ViewDTO<Boolean> batchdelete(Integer[] ids){
		logger.info("batchdelete called. idList:" + ids );
		
		ViewDTO<Boolean> view = scheduleLockService.batchDelete(ids);
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
	public ViewDTO<Boolean> applyPresetLock(Integer childId,String applyPresetLockParamJson){
		logger.info("applyPresetLock called. childId:" + childId + " ,applyPresetLockParam:" + applyPresetLockParamJson );
		
		ApplyPresetLockParam applyPresetLockParam = JSONUtil.getApplyPresetLockParam(applyPresetLockParamJson);
		ViewDTO<Boolean> view = childDetailService.applyPresetLock(childId,applyPresetLockParam);
		
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
}
