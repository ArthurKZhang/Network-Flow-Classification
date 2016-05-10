package arthur.service.classification;

import java.util.List;
import java.util.Map;

public class DistanceTools {
	public static double avgDistance(List<String> x, List<List<String>> tx) {
		double dis = 0;
		for (List<String> l : tx) {
			dis += distance(x, l);
		}
		return BigDecimalCalculate.div(dis, tx.size());
	}

	public static double minDistance(List<String> x, List<List<String>> tx) {
		double dis = Double.MAX_VALUE;
		double temp;
		for (List<String> l : tx) {
			if ((temp = distance(x, l)) < dis)
				dis = temp;
		}
		return dis;
	}

	public static double mvt(List<String> x, String tw, Map<String, List<List<String>>> mtx) {
		// majority vote//
		double wdis = Double.MAX_VALUE;
		String s = null;
		for (String w : mtx.keySet()) {
			double dis = Double.MAX_VALUE;
			double temp;
			for (List<String> tx : mtx.get(w)) {
				if ((temp = distance(x, tx)) < dis)
					dis = temp;
			}
			if (wdis > dis) {
				wdis = dis;
				s = w;
			}
		}
		return s == tw ? 1 : 0;
	}

	private static double distance(List<String> x, List<String> l) {
		// TODO Auto-generated method stub
		return 0;
	}
}
