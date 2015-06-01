package eden.sun.childrenguard.server.service.impl;

import java.text.ParseException;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.service.IVersionService;
import eden.sun.childrenguard.server.util.Constants;
import eden.sun.childrenguard.server.util.DateUtil;

@Service
public class VersionServiceImpl implements IVersionService {

	@Autowired
	private IParentService parentService;
	
	@Override
	public ViewDTO<Boolean> isInTrial(String mobile) throws ServiceException {
		ViewDTO<Boolean> view = new ViewDTO<Boolean>();
		try {
			
			Parent parent = parentService.getByMobile(mobile);
			if( parent == null ){
				view.setMsg(ViewDTO.MSG_ERROR);
				return view;
			}
			
			Date now = new Date();
			Date createTime = parent.getCreateTime();
			
			if( DateUtil.dateDiff(createTime,now) <= Constants.TRIAL_DAY ){
				view.setData(true);
				view.setMsg(ViewDTO.MSG_SUCCESS);
				return view;
			}else{
				view.setData(false);
				view.setMsg(ViewDTO.MSG_SUCCESS);
				return view;
			}
		} catch (Exception e) {
			view.setMsg(ViewDTO.MSG_ERROR);
			return view;
		}
		
	}

}
