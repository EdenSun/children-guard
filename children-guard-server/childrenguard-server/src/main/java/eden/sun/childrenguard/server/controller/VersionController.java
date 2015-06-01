package eden.sun.childrenguard.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.service.IVersionService;

@Controller
@RequestMapping("/parent/version")
public class VersionController extends BaseController{

	@Autowired
	private IVersionService versionService;
	
	/**
	 * /parent/version/isInTrial
	 * test trial version
	 * @param mobile ” œ‰
	 * @return 
	 */
	@RequestMapping("/isInTrial")
	@ResponseBody
	public ViewDTO<Boolean> isTrial(String mobile){
		logger.info("isTrial called. Mobile:" + mobile );
		ViewDTO<Boolean> view = versionService.isInTrial(mobile);
		
		return view;
	}
	
}
