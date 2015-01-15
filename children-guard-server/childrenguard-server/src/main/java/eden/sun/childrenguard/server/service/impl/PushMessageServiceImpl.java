package eden.sun.childrenguard.server.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eden.sun.childrenguard.server.dao.generated.PushMessageMapper;
import eden.sun.childrenguard.server.dto.PushMessageViewDTO;
import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.Parent;
import eden.sun.childrenguard.server.model.generated.PushMessage;
import eden.sun.childrenguard.server.model.generated.PushMessageExample;
import eden.sun.childrenguard.server.model.generated.PushMessageExample.Criteria;
import eden.sun.childrenguard.server.service.IParentService;
import eden.sun.childrenguard.server.service.IPushMessageService;

@Service
public class PushMessageServiceImpl extends BaseServiceImpl implements IPushMessageService{

	@Autowired
	private IParentService parentService;
	
	@Autowired
	private PushMessageMapper pushMessageMapper;
	
	@Override
	public ViewDTO<List<PushMessageViewDTO>> listPushMsg(String accessToken)
			throws ServiceException {
		if( accessToken == null ){
			throw new ServiceException("Parameter access token can not be null.");
		}
		
		ViewDTO<List<PushMessageViewDTO>> view = new ViewDTO<List<PushMessageViewDTO>>();
		try {
			Parent parent = parentService.getByAccessToken(accessToken);
			if( parent == null ){
				throw new ServiceException("Parent is not exists.(accessToken:" + accessToken + ")");
			}
			
			List<PushMessage> pushMessageList = listByOwnerId(parent.getId());
			List<PushMessageViewDTO> pushMessageViewList = trans2PushMessageViewDTOList(pushMessageList);
			view.setData(pushMessageViewList);
			
			return view;
		} catch (Exception e) {
			logger.error("list push message error.", e);
			throw new ServiceException(e);
		}
	}

	private List<PushMessageViewDTO> trans2PushMessageViewDTOList(
			List<PushMessage> pushMessageList) {
		if( pushMessageList == null ){
			return null;
		}
		
		List<PushMessageViewDTO> list = new ArrayList<PushMessageViewDTO>();
		for(PushMessage pushMessage :pushMessageList){
			PushMessageViewDTO dto = trans2PushMessageViewDTO(pushMessage);
			if( dto != null ){
				list.add(dto);
			}
		}
		
		return list;
	}

	private PushMessageViewDTO trans2PushMessageViewDTO(PushMessage pushMessage) {
		if( pushMessage == null ){
			return null;
		}
		PushMessageViewDTO dto = new PushMessageViewDTO();
		BeanUtils.copyProperties(pushMessage, dto);
		
		return dto;
	}

	private List<PushMessage> listByOwnerId(Integer ownerId)throws ServiceException {
		if( ownerId == null ){
			throw new ServiceException("Parameter owner id can not be null.");
		}
		PushMessageExample example = new PushMessageExample();
		Criteria criteria = example.createCriteria();
		criteria.andOwnerIdEqualTo(ownerId);
		
		return pushMessageMapper.selectByExampleWithBLOBs(example);
	}
	
	@Override
	public ViewDTO<PushMessageViewDTO> delete(String accessToken,
			Integer pushMessageId) throws ServiceException {
		if( pushMessageId == null ){
			throw new ServiceException("Parameter push message id can not be null.");
		}
		
		PushMessage domain = getById(pushMessageId);
		
		PushMessageViewDTO viewDto = trans2PushMessageViewDTO(domain);
		if( viewDto == null ){
			throw new ServiceException("Specified push message is not exists.");
		}
		
		Parent parent = parentService.getByAccessToken(accessToken);
		if( parent == null ){
			throw new ServiceException("Parent is not exists.(accessToken:" + accessToken + ")");
		}
		
		if( parent.getId() != null && domain.getOwnerId() != null && domain.getOwnerId().equals(parent.getId())){
			ViewDTO<PushMessageViewDTO> view = new ViewDTO<PushMessageViewDTO>();
			deleteById(pushMessageId);
			
			view.setData(viewDto);
			return view;
		}else{
			throw new ServiceException("Have no permission to delete this push message.(push message id: " + pushMessageId + ")");
		}
		
	}

	private void deleteById(Integer pushMessageId) {
		pushMessageMapper.deleteByPrimaryKey(pushMessageId);
	}

	private PushMessage getById(Integer pushMessageId) {
		return pushMessageMapper.selectByPrimaryKey(pushMessageId);
	}

	@Override
	public ViewDTO<Boolean> batchdelete(String accessToken, Integer[] ids)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
