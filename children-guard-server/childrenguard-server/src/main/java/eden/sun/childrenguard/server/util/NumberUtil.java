package eden.sun.childrenguard.server.util;

import java.util.Random;

public class NumberUtil {

	/**
	 * 获取n位随机数
	 * @param count 位数
	 * @return
	 */
	public static String getRandomNumberByCount(int n) {
		if (n < 1 || n > 10) {
            throw new IllegalArgumentException("cannot random " + n + " bit number");
        }
        Random ran = new Random();
        if (n == 1) {
            return String.valueOf(ran.nextInt(10));
        }
        int bitField = 0;
        char[] chs = new char[n];
        for (int i = 0; i < n; i++) {
            while(true) {
                int k = ran.nextInt(10);
                if( (bitField & (1 << k)) == 0) {
                    bitField |= 1 << k;
                    chs[i] = (char)(k + '0');
                    break;
                }
            }
        }
        return new String(chs);
	}

	public static Integer toInteger(String numStr) {
		if( numStr == null ){
			return null;
		}
		
		return Integer.parseInt(numStr);
	}

	public static Double toDouble(String numStr) {
		if( numStr == null ){
			return null;
		}
		
		return Double.parseDouble(numStr);
	}

}
