package arthur.service.classification;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;  

public class Bayes {  
    /** 
     * ��ԭѵ��Ԫ�鰴��𻮷� 
     * @param datas ѵ��Ԫ�� 
     * @return Map<������ڸ�����ѵ��Ԫ��> 
     */  
    private static Map<String, List<List<String>>> datasOfClass(List<List<String>> trainDatas){
        Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();  
        List<String> t = new LinkedList<String>();
        String c = "";
        for (int i = 0; i < trainDatas.size(); i++) {  
            t = trainDatas.get(i);  
            c = t.get(t.size() - 1);	//����Ĭ�����һ��Ԫ����ѵ�����ݵ� ��ǵ� Ԫ��  
            if (map.containsKey(c)) {  
                map.get(c).add(t);  
            } else {
                List<List<String>> nt = new LinkedList<List<String>>();  
                nt.add(t);  
                map.put(c, nt);  
            }  
        }  
        return map;  
    }  
      
    /** 
     * ��ѵ�����ݵĻ�����Ԥ�����Ԫ������ 
     * @param datas ѵ��Ԫ�� 
     * @param testT ����Ԫ�� 
     * @return ����Ԫ������ 
     */  
    public static String predictClass(List<List<String>> trainDatas, List<String> testT) {  
        Map<String, List<List<String>>> trainedMap = datasOfClass(trainDatas);  
        Object classes[] = trainedMap.keySet().toArray();  
        double maxP = 0.00;  
        int maxPIndex = -1;  
        for (int i = 0; i < trainedMap.size(); i++) {  
            String c = classes[i].toString();   
            List<List<String>> d = trainedMap.get(c);  
            double pOfC = BigDecimalCalculate.div(d.size(), trainDatas.size(), 3);  
            for (int j = 0; j < testT.size(); j++) {  
                double pv = pOfV(d, testT.get(j), j);  
                pOfC = BigDecimalCalculate.mul(pOfC, pv);  
            }  
            if(pOfC > maxP){  
                maxP = pOfC;  
                maxPIndex = i;  
            }  
        }  
        return classes[maxPIndex].toString();  
    }  
    /** 
     * ����ָ����������ָ��ֵ���ֵĸ��� 
     * @param d ����ĳһ���ѵ��Ԫ�� 
     * @param value ��ֵ 
     * @param index ���������� 
     * @return ���� 
     */  
    private static double pOfV(List<List<String>> d, String value, int index) {  
        double p = 0.00;  
        int count = 0;  
        int total = d.size();  
        for (int i = 0; i < total; i++) {  
            if(d.get(i).get(index).equals(value)){  
                count++;  
            }  
        }  
        p = BigDecimalCalculate.div(count, total, 3);  
        return p;  
    }  
}  