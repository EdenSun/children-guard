package eden.sun.childrenguard.server.util;

import java.util.ArrayList;
import java.util.List;

import eden.sun.childrenguard.server.exception.ServiceException;
import eden.sun.childrenguard.server.model.generated.PresetLock;

public class ScheduleLockHelper {
	public static List<Boolean> getRepeatList(PresetLock presetLock){
		if( presetLock == null){
			return null;
		}
		
		List<Boolean> repeatList = new ArrayList<Boolean>();
		boolean monday = DataTypeUtil.getBooleanValue(presetLock.getRepeatMonday());
		boolean tuesday = DataTypeUtil.getBooleanValue(presetLock.getRepeatTuesday());
		boolean wednesday = DataTypeUtil.getBooleanValue(presetLock.getRepeatWednesday());
		boolean thurday = DataTypeUtil.getBooleanValue(presetLock.getRepeatThurday());
		boolean friday = DataTypeUtil.getBooleanValue(presetLock.getRepeatFriday());
		boolean saturday = DataTypeUtil.getBooleanValue(presetLock.getRepeatSaturday());
		boolean sunday = DataTypeUtil.getBooleanValue(presetLock.getRepeatSunday());

		repeatList.add(monday);
		repeatList.add(tuesday);
		repeatList.add(wednesday);
		repeatList.add(thurday);
		repeatList.add(friday);
		repeatList.add(saturday);
		repeatList.add(sunday);
		
		return repeatList;
	}
	
	
	public static String getRepeatSummary(List<Boolean> repeatList) {
		if( repeatList == null || repeatList.size() == 0 ){
			return "";
		}
		if( repeatList.size() != 7 ){
			throw new ServiceException("weekday data error.");
		}
		
		boolean monday = repeatList.get(0);
		boolean tuesday = repeatList.get(1);
		boolean wednesday = repeatList.get(2);
		boolean thurday = repeatList.get(3);
		boolean friday = repeatList.get(4);
		boolean saturday = repeatList.get(5);
		boolean sunday = repeatList.get(6);
		
		StringBuffer sb = new StringBuffer();
		if( monday ){
			sb.append("Mon.").append(" ");
		}
		
		if( tuesday ){
			sb.append("Tues.").append(" ");
		}
		
		if( wednesday ){
			sb.append("Wed.").append(" ");
		}
		
		if( thurday ){
			sb.append("Thur.").append(" ");
		}
		
		if( friday ){
			sb.append("Fri.").append(" ");
		}
		
		if( saturday ){
			sb.append("Sat.").append(" ");
		}
		
		if( sunday ){
			sb.append("Sun.").append(" ");
		}
		return sb.toString().trim();
	}
}
