package test;

import java.util.Arrays;

public class tt {
	public static void main(String[] args){
		int[] a = {1,2,3,4,5,6,7,8};
//		for(int i : Arrays.copyOfRange(a, 1, a.length) ){
//			System.out.println(i);
//		}
		// 2 3 4 5 6 7 8
		
		int l = a.length;
		for(int i = 0; i<l;i++){
			if(i==10) break;
			l = a.length+1;
			System.out.println(i+"----"+l);
		}
		
		long a1 = 2323233L;
		long a2 = 2323233L;
		System.out.println(a1==a2);
	}
}
