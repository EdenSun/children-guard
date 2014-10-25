package eden.sun.childrenguard.server.cometd;

import java.util.Date;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.cometd.annotation.Listener;
import org.cometd.annotation.Service;
import org.cometd.annotation.Session;
import org.cometd.bayeux.server.BayeuxServer;
import org.cometd.bayeux.server.ServerMessage;
import org.cometd.bayeux.server.ServerSession;

import eden.sun.childrenguard.server.dto.ViewDTO;
import eden.sun.childrenguard.server.dto.param.ChildEndExtraInfoParam;
import eden.sun.childrenguard.server.service.IChildEndService;
import eden.sun.childrenguard.server.util.CometdChannel;
import eden.sun.childrenguard.server.util.NumberUtil;

@Named
@Singleton
@Service("childEndDataSyncCometdService")
public class ChildEndDataSyncService extends BaseCometService{
	@Inject
	private BayeuxServer bayeux;
	@Session
	private ServerSession serverSession;
	
	@Inject
	private IChildEndService childEndService;
	
	@PostConstruct
	public void init() {
	}
	
	@Listener("/service/childend/uploadChildExtraInfo")
	public void uploadChildExtraInfo(ServerSession remote, ServerMessage message) {
		logger.info(CometdChannel.CHILD_END_UPLOAD_CHILD_EXTRA_INFO);
		Map<String, Object> input = message.getDataAsMap();
		
		ChildEndExtraInfoParam extraInfoParam = getUploadChildExtraInfoParam(input);
		
		ViewDTO<Boolean> view = childEndService.uploadChildExtraInfo(extraInfoParam);
		
		String resultJSON = toJson(view);
		
		remote.deliver(serverSession, CometdChannel.CHILD_END_UPLOAD_CHILD_EXTRA_INFO, resultJSON);
	}

	private ChildEndExtraInfoParam getUploadChildExtraInfoParam(
			Map<String, Object> input) {
		Date now = new Date();
		Integer childId = NumberUtil.toInteger(input.get("childId").toString());
		Double latitude = NumberUtil.toDouble(input.get("latitude").toString());
		Double longitude = NumberUtil.toDouble(input.get("longitude").toString());
		Date locationUpdateTime = null;
		if( latitude != null && longitude != null ){
			locationUpdateTime = now;
		}
		Double networkTrafficUsed = NumberUtil.toDouble(input.get("networkTrafficUsed").toString());
		Date networkTrafficUpdateTime = null;
		if( networkTrafficUsed != null ){
			networkTrafficUpdateTime = now;
		}
		Double speed = NumberUtil.toDouble(input.get("speed").toString());
		Date speedUpdateTime = null;
		if( speed != null ){
			speedUpdateTime = now;
		}
		
		ChildEndExtraInfoParam param = new ChildEndExtraInfoParam();
		param.setChildId(childId);
		param.setLatitude(latitude);
		param.setLongitude(longitude);
		param.setLocationUpdateTime(locationUpdateTime);
		param.setNetworkTrafficUsed(networkTrafficUsed);
		param.setNetworkTrafficUpdateTime(networkTrafficUpdateTime);
		param.setSpeed(speed);
		param.setSpeedUpdateTime(speedUpdateTime);
		return param;
	}
	
}
