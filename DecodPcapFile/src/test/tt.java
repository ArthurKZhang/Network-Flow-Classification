package test;

import java.util.Arrays;

public class tt {
	public static void main(String[] args){
		int[] a = {1,2,3,4,5,6,7,8};
		for(int i : Arrays.copyOfRange(a, 1, a.length) ){
			System.out.println(i);
		}
		// 2 3 4 5 6 7 8
	}
}
