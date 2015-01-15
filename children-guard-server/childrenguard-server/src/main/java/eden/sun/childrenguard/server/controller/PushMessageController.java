package eden.sun.childrenguard.server.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.PushMessageViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IPushMessageService;

@Controller
@RequestMapping("/parent/pushmsg")
public class PushMessageController extends BaseController{

	@Autowired
	private IPushMessageService pushMessageService;
	
	@RequestMapping("/list")
	@ResponseBody
	public ViewDTO<List<PushMessageViewDTO>> list(String accessToken){
		logger.info("listPushMsg called. accessToken:" + accessToken );
		
		ViewDTO<List<PushMessageViewDTO>> view = pushMessageService.listPushMsg(accessToken);
		return view;
	}
	
	
	@RequestMapping("/delete")
	@ResponseBody
	public ViewDTO<PushMessageViewDTO> delete(String accessToken,Integer pushMessageId){
		logger.info("delete called. accessToken:" + accessToken + " , pushMessageId:" + pushMessageId );
		
		ViewDTO<PushMessageViewDTO> view = pushMessageService.delete(accessToken,pushMessageId);
		return view;
	}
	
	@RequestMapping("/batchdelete")
	@ResponseBody
	public ViewDTO<Boolean> batchdelete(String accessToken,Integer[] ids){
		logger.info("delete called. accessToken:" + accessToken + " , ids:" + ids );
		
		ViewDTO<Boolean> view = pushMessageService.batchdelete(accessToken,ids);
		return view;
	}
	
}
