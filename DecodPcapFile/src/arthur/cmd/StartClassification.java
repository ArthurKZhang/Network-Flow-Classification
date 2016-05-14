package arthur.cmd;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import arthur.bean.data.BoF;
import arthur.bean.data.FlowFeatureVector;
import arthur.dao.Mongo.DBHelperBOF;
import arthur.dao.Mongo.DBHelperFlowFeatureVector;
import arthur.dao.Mongo.DBHelperTrainVector;
import arthur.service.classification.AVGDis;
import arthur.service.classification.Classification;
import arthur.service.classification.Distance;
import arthur.service.classification.MINDis;
import arthur.service.datatrans.TransImp;

public class StartClassification {
	public static void main(String[] args) {
		println("#>>Prepare Train Datas? (y/n)");
		Scanner scanner = new Scanner(System.in);
		String inputS = scanner.next();
		if (inputS.equals("y")) {
			println("---Preparing Train Datas-----");
			InputStream is;
			try {
				DataPreparation.dropAll();
				DataPreparation.createAll();
				is = new FileInputStream("D:\\00DATA\\BS\\qq141.pcap");
				DataPreparation.preTrainData(is, "QQ");
				is = new FileInputStream("D:\\00DATA\\BS\\http.pcap");
				DataPreparation.preTrainData(is, "http");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		println("#>>Prepare Test Datas? (y/n)");
		inputS = scanner.next();
		if (inputS.equals("y")) {
			println("---Preparing Test Datas-----");
			InputStream is2;
			try {
				DataPreparation.createNoTrain();
				is2 = new FileInputStream("D:\\00DATA\\BS\\qq141.pcap");
				DataPreparation.preTestData(is2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			v2BoF();
		}
		println("---DONE WITH DATA PREPARATION -----------");

		println("#>>Start Classification? (y/n)");
		inputS = scanner.next();
		if (inputS.equals("y")) {

			// 从数据库获得Bof进行分类
			println("---GET BoF to Classify-----------");
			List<BoF> bs = DBHelperBOF.queryAll();
			Map<String, List<FlowFeatureVector>> trainMap = DBHelperTrainVector.queryAll();

			println("#>>Choose Distance-Calculating Method: (AVG:1,MIN:2)");
			Distance disMethod;
			inputS = scanner.next();
			if (inputS.equals("1")) {
				disMethod = new AVGDis();
			} else {
				disMethod = new MINDis();
			}

			for (BoF b : bs) {
				// println(b.toString());
				Map<String, Double> info = Classification.classifier(trainMap, b, disMethod);
				for(String s:info.keySet()){
					println("距离 "+s+"类的距离："+info.get(s));
				}
			}
		}

	}

	private static void v2BoF() {
		List<FlowFeatureVector> vs = DBHelperFlowFeatureVector.queryAll();
		TransImp.featureVector2BOF(vs);
	}

	private static void println(String s) {
		System.out.println(s);
	}
	// private static void pcap2DB2V(InputStream is,int vdb, String type){
	// try {
	// PcapParser.unpack(is);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// List<IPPacket> ps = DBHelperIPPacket.queryAll();
	// TransImp.ipPacket2NetFlow2FVector(ps,vdb,type);
	// }
}
