package arthur.service.classification;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;  

public class Bayes {  
    /** 
     * 将原训练元组按类别划分 
     * @param datas 训练元组 
     * @return Map<类别，属于该类别的训练元组> 
     */  
    private static Map<String, List<List<String>>> datasOfClass(List<List<String>> trainDatas){
        Map<String, List<List<String>>> map = new HashMap<String, List<List<String>>>();  
        List<String> t = new LinkedList<String>();
        String c = "";
        for (int i = 0; i < trainDatas.size(); i++) {  
            t = trainDatas.get(i);  
            c = t.get(t.size() - 1);	//这里默认最后一个元素是训练数据的 标记的 元素  
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
     * 在训练数据的基础上预测测试元组的类别 
     * @param datas 训练元组 
     * @param testT 测试元组 
     * @return 测试元组的类别 
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
     * 计算指定属性列上指定值出现的概率 
     * @param d 属于某一类的训练元组 
     * @param value 列值 
     * @param index 属性列索引 
     * @return 概率 
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