package arthur.service.classification;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.print.attribute.standard.PrinterLocation;

import arthur.bean.data.FlowFeatureVector;
import arthur.bean.data.IPPacket;
import arthur.bean.data.NetworkFlow;
import arthur.dao.Mongo.DBHelperIPPacket;
import arthur.dao.Mongo.DBHelperNetWorkFlow;
import arthur.service.datatrans.DataTransAlgorithm;
import arthur.service.datatrans.TransImp;
import arthur.service.file2bean.PcapParser;

public class StartClassification {
	public static void main(String[] args){
//		DBHelperIPPacket.dropCollection();
//		
//		//读取文件到数据库
//		InputStream is;
//		try {
//			is = new FileInputStream("D:\\00DATA\\BS\\267num.pcap");
//			PcapParser.unpack(is);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<IPPacket> ps = DBHelperIPPacket.queryAll();
//		int i = 0;
//		for(IPPacket p:ps){
//			println(i+"----"+p.toString());
//			i++;
//		}
		//
//		TransImp.ipPacket2NetFlow(ps);
		Map<Integer, NetworkFlow> mapnf = DataTransAlgorithm.packets2NetworkFlow(ps);
		Set<Integer> keys = mapnf.keySet();
//		DBHelperNetWorkFlow.createCollection();
		for (Integer i : keys) {
			NetworkFlow nf = mapnf.get(i);
			DBHelperNetWorkFlow.save(nf);
//			FlowFeatureVector fv = DataTransAlgorithm.getFlowFeatureVector(nf);
//			TransImp.netflow2FeatureVector(nf);
		}
		//读取训练数据到数据库
		//从数据库获得Bof进行分类
		//分类结果输出 并 保存到数据库
	}
	
	private static void println(String s){
		System.out.println(s);
	}
	
}
