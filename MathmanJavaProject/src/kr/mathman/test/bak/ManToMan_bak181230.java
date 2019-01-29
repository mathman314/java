package kr.mathman.test.bak;

/**
 * bool형 체크 완성
 * @author tsjin
 *
 */
public class ManToMan_bak181230 {

	static final boolean infoLog = true;	// 상세 로그
	
	static int[][] c2Arr;
	static int[][] cnArr;
	static long startTime;
	
	static String chkStr0 = "000";
	static String chkStr1 = "111";
	
	public static void main(String[] args) {
		calc(4, 3);
		print("걸린 시간 : " + leftTime());
		
		//main_all(4);
	}
	
	/**
	 * n에 대한 풀이
	 */
	public static void main_all(int n) {
		int m = n;
		
		boolean ret = true;
		
		while (ret) {
			ret = calc(++m, n);
			
			print("걸린 시간 : " + leftTime());
			print("-----------------------------");
		}
	}
	
	private static String getString(int[] arr) {
		String ret = "[";
		for (int i=0; i<arr.length; i++) {
			if (i>0) ret += ",";
			ret += arr[i];
		}
		ret += "]";
		return ret;
	}

	/**
	 * 계산 결과 리턴
	 * true : 예외 사례 발견.
	 * false : 예외 사례 발견하지 못함.
	 */
	private static boolean calc(int m, int n) {
		print("calc(" + m + ", " + n + ")");
		if (m<=n) {
			print("첫번째 인자(" + m + ")가 두번째 인자(" + n + ")보다 커야 합니다.");
			return false;
		} else if (n<3 || n>4) {
			print("두번째 인자(" + n + ")는 3과 4만 가능합니다.");
			return false;
		}
		
		int i, j, k, o, p;
		
		// 2인 조합의 수 : 두 사람씩 짝을 지을 수 있는 경우의 수
		int c2_num = comb2(m);
		print("c2_num = " + c2_num);
		// 2인 조합 배열 : 두 사람씩 짝을 지을 경우, 그 두 사람의 짝을 배열에 담음.
		c2Arr = new int[c2_num][2];
		k = 0;
		for (i=0; i<m-1; i++) {
			for (j=i+1; j<m; j++) {
				c2Arr[k][0] = i;
				c2Arr[k][1] = j;
				if (infoLog) print("c2Arr[" + k + "] : " + getString(c2Arr[k]));
				k++;
			}
		}
		
		// n인 조합의 수 : n 사람씩 짝을 지을 수 있는 경우의 수
		int cn_num = combn(m, n);
		print("cn_num = " + cn_num);
		// n인 조합 배열 : n 사람씩 짝을 지을 경우, 그 n 사람의 조합 순서를 배열에 담음.
		k = 0;
		if (n==3) {
			chkStr0 = "000";
			chkStr1 = "111";
			cnArr = new int[cn_num][3];
			for (i=0; i<m-2; i++) {
				for (j=i+1; j<m-1; j++) {
					for (o=j+1; o<m; o++) {
						cnArr[k][0] = getOrder_c2Arr(i,j);
						cnArr[k][1] = getOrder_c2Arr(i,o);
						cnArr[k][2] = getOrder_c2Arr(j,o);
						if (infoLog) print("cnArr[" + k + "] : " + getString(cnArr[k]));
						k++;
					}
				}
			}
		} else if (n==4) {
			chkStr0 = "000000";
			chkStr1 = "111111";
			cnArr = new int[cn_num][6];
			for (i=0; i<m-3; i++) {
				for (j=i+1; j<m-2; j++) {
					for (o=j+1; o<m-1; o++) {
						for (p=o+1; p<m; p++) {
							cnArr[k][0] = getOrder_c2Arr(i,j);
							cnArr[k][1] = getOrder_c2Arr(i,o);
							cnArr[k][2] = getOrder_c2Arr(i,p);
							cnArr[k][3] = getOrder_c2Arr(j,o);
							cnArr[k][4] = getOrder_c2Arr(j,p);
							cnArr[k][5] = getOrder_c2Arr(o,p);
							if (infoLog) print("cnArr[" + k + "] : " + getString(cnArr[k]));
							k++;
						}
					}
				}
			}
		}
		
		// 여기서부터 시간 체크 시작
		startTime = System.currentTimeMillis();
		
		// 2인 조합 배열에 모든 경우를 넣어 계산
		int ja = 0;	// 자리수 체크
		for (i=0; i<Math.pow(2,c2Arr.length); i++) {
			//int[] bit = new int[c2Arr.length];
			//byte[] bit2 = new byte[c2Arr.length];
			boolean[] bl = new boolean[c2Arr.length];
			String bin = Integer.toBinaryString(i);	// 2진수
			
			if (bin.length()>ja) {
				//print(bin + " : (" + bin.length + ") : " + new Date());
				print(bin.length() + "자리 - " + leftTime());
				ja = bin.length();
			}
			while(bin.length()<c2Arr.length) {
				bin = "0" + bin;
			}
			
//			for (j=0; j<c2Arr.length; j++) {
//				bit[j] = Integer.parseInt(bin.substring(j,j+1));
//			}
//			boolean chk = chkAll_0or1(bit);
//			boolean chk = chkAll_0or1_2(bit);
//			
//			boolean chk = chkAll_0or1_3(bin);
//			
//			for (j=0; j<c2Arr.length; j++) {
//				bit2[j] = Byte.parseByte(bin.substring(j,j+1));
//			}
//			boolean chk = chkAll_0or1_4(bit2);
			
			for (j=0; j<c2Arr.length; j++) {
				bl[j] = bin.substring(j,j+1).equals("0") ? false : true;
			}
			boolean chk = chkAll_0or1_5(bl);
			
			// 전체 출력
			//print("chk["+bin+"] : " + chk);
			
			// 모두 0이거나 모두 1인 경우에 해당되지 않는 방법이 있으면 체크 종료
			if (!chk) {
				print("예외 사례 발견 ==> chk["+bin+"] : " + chk);
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 조건 확인 - 모두 0이거나 모두 1인 경우 true
	 */
	private static boolean chkAll_0or1(int[] arr) {
		int i, j, c;
		for (i=0; i<cnArr.length; i++) {
			boolean chk0 = true;	// 모두 0
			boolean chk1 = true;	// 모두 1
			for (j=0; j<cnArr[i].length; j++) {
				c = arr[cnArr[i][j]];
				if (c==0)
					chk1 = false;
				else
					chk0 = false;
			}
			if (chk0 || chk1) return true;
		}
		return false;
	}

	/**
	 * 조건 확인 - 모두 0이거나 모두 1인 경우 true - 개선버전(배열합 체크)
	 */
	private static boolean chkAll_0or1_2(int[] arr) {
		int i, j, c;
		int jlen = cnArr[0].length;
		for (i=0; i<cnArr.length; i++) {
			c = 0;
			for (j=0; j<jlen; j++) {
				c += arr[cnArr[i][j]];
			}
			if (c==0 || c==jlen) return true;
		}
		return false;
	}

	/**
	 * 조건 확인 - 모두 0이거나 모두 1인 경우 true - 개선버전(문자열 체크)
	 */
	private static boolean chkAll_0or1_3(String str) {
		int i, j;
		String chkStr;
		int jlen = cnArr[0].length;
		
		for (i=0; i<cnArr.length; i++) {
			chkStr = "";
			for (j=0; j<jlen; j++) {
				chkStr += str.substring(cnArr[i][j], cnArr[i][j]+1);
			}
			if (chkStr.equals(chkStr0) || chkStr.equals(chkStr1)) return true;
		}
		return false;
	}

	/**
	 * 조건 확인 - 모두 0이거나 모두 1인 경우 true - 개선버전(배열합 byte 체크)
	 */
	private static boolean chkAll_0or1_4(byte[] arr) {
		int i, j;
		byte c;
		byte jlen = (byte) cnArr[0].length;
		for (i=0; i<cnArr.length; i++) {
			c = 0;
			for (j=0; j<jlen; j++) {
				c += arr[cnArr[i][j]];
			}
			if (c==0 || c==jlen) return true;
		}
		return false;
	}
	
	/**
	 * 조건 확인 - 모두 0이거나 모두 1인 경우 true - 개선 버전(bool형 체크)
	 */
	private static boolean chkAll_0or1_5(boolean[] arr) {
		int i, j;
		for (i=0; i<cnArr.length; i++) {
			boolean chk0 = true;	// 모두 0
			boolean chk1 = true;	// 모두 1
			for (j=0; j<cnArr[i].length; j++) {
				if (arr[cnArr[i][j]])
					chk1 = false;
				else
					chk0 = false;
			}
			if (chk0 || chk1) return true;
		}
		return false;
	}
	
	/**
	 * c2Arr에서의 순서 리턴
	 */
	private static int getOrder_c2Arr(int a, int b) {
		int k;
		for (k=0; k<c2Arr.length; k++) {
			if (c2Arr[k][0]==a && c2Arr[k][1]==b) return k;
		}
		return -1;
	}

	/**
	 * mC2 조합 계산
	 */
	private static int comb2(int m) {
		return m * (m-1) / 2;
	}
	
	/**
	 * mCn 조합 계산
	 */
	private static int combn(int m, int n) {
		int ret = 0;
		if (n==3)
			ret = m * (m-1) * (m-2) / 3 / 2;
		else if (n==4)
			ret = m * (m-1) * (m-2) * (m-3) / 4 / 3 / 2;
		return ret;
	}
	
	/**
	 * 흘러간 시간
	 */
	private static String leftTime() {
		return (System.currentTimeMillis() - startTime) + "밀리초";
	}
	
	/**
	 * 문자열 출력
	 */
	private static void print(String str) {
		System.out.println(str);
	}
	
}
/*
 * 결과
calc(9, 3)
c2_num = 36
c2Arr[0] : [0,1]
c2Arr[1] : [0,2]
c2Arr[2] : [0,3]
c2Arr[3] : [0,4]
c2Arr[4] : [0,5]
c2Arr[5] : [0,6]
c2Arr[6] : [0,7]
c2Arr[7] : [0,8]
c2Arr[8] : [1,2]
c2Arr[9] : [1,3]
c2Arr[10] : [1,4]
c2Arr[11] : [1,5]
c2Arr[12] : [1,6]
c2Arr[13] : [1,7]
c2Arr[14] : [1,8]
c2Arr[15] : [2,3]
c2Arr[16] : [2,4]
c2Arr[17] : [2,5]
c2Arr[18] : [2,6]
c2Arr[19] : [2,7]
c2Arr[20] : [2,8]
c2Arr[21] : [3,4]
c2Arr[22] : [3,5]
c2Arr[23] : [3,6]
c2Arr[24] : [3,7]
c2Arr[25] : [3,8]
c2Arr[26] : [4,5]
c2Arr[27] : [4,6]
c2Arr[28] : [4,7]
c2Arr[29] : [4,8]
c2Arr[30] : [5,6]
c2Arr[31] : [5,7]
c2Arr[32] : [5,8]
c2Arr[33] : [6,7]
c2Arr[34] : [6,8]
c2Arr[35] : [7,8]
cn_num = 84
cnArr[0] : [0,1,8]
cnArr[1] : [0,2,9]
cnArr[2] : [0,3,10]
cnArr[3] : [0,4,11]
cnArr[4] : [0,5,12]
cnArr[5] : [0,6,13]
cnArr[6] : [0,7,14]
cnArr[7] : [1,2,15]
cnArr[8] : [1,3,16]
cnArr[9] : [1,4,17]
cnArr[10] : [1,5,18]
cnArr[11] : [1,6,19]
cnArr[12] : [1,7,20]
cnArr[13] : [2,3,21]
cnArr[14] : [2,4,22]
cnArr[15] : [2,5,23]
cnArr[16] : [2,6,24]
cnArr[17] : [2,7,25]
cnArr[18] : [3,4,26]
cnArr[19] : [3,5,27]
cnArr[20] : [3,6,28]
cnArr[21] : [3,7,29]
cnArr[22] : [4,5,30]
cnArr[23] : [4,6,31]
cnArr[24] : [4,7,32]
cnArr[25] : [5,6,33]
cnArr[26] : [5,7,34]
cnArr[27] : [6,7,35]
cnArr[28] : [8,9,15]
cnArr[29] : [8,10,16]
cnArr[30] : [8,11,17]
cnArr[31] : [8,12,18]
cnArr[32] : [8,13,19]
cnArr[33] : [8,14,20]
cnArr[34] : [9,10,21]
cnArr[35] : [9,11,22]
cnArr[36] : [9,12,23]
cnArr[37] : [9,13,24]
cnArr[38] : [9,14,25]
cnArr[39] : [10,11,26]
cnArr[40] : [10,12,27]
cnArr[41] : [10,13,28]
cnArr[42] : [10,14,29]
cnArr[43] : [11,12,30]
cnArr[44] : [11,13,31]
cnArr[45] : [11,14,32]
cnArr[46] : [12,13,33]
cnArr[47] : [12,14,34]
cnArr[48] : [13,14,35]
cnArr[49] : [15,16,21]
cnArr[50] : [15,17,22]
cnArr[51] : [15,18,23]
cnArr[52] : [15,19,24]
cnArr[53] : [15,20,25]
cnArr[54] : [16,17,26]
cnArr[55] : [16,18,27]
cnArr[56] : [16,19,28]
cnArr[57] : [16,20,29]
cnArr[58] : [17,18,30]
cnArr[59] : [17,19,31]
cnArr[60] : [17,20,32]
cnArr[61] : [18,19,33]
cnArr[62] : [18,20,34]
cnArr[63] : [19,20,35]
cnArr[64] : [21,22,26]
cnArr[65] : [21,23,27]
cnArr[66] : [21,24,28]
cnArr[67] : [21,25,29]
cnArr[68] : [22,23,30]
cnArr[69] : [22,24,31]
cnArr[70] : [22,25,32]
cnArr[71] : [23,24,33]
cnArr[72] : [23,25,34]
cnArr[73] : [24,25,35]
cnArr[74] : [26,27,30]
cnArr[75] : [26,28,31]
cnArr[76] : [26,29,32]
cnArr[77] : [27,28,33]
cnArr[78] : [27,29,34]
cnArr[79] : [28,29,35]
cnArr[80] : [30,31,33]
cnArr[81] : [30,32,34]
cnArr[82] : [31,32,35]
cnArr[83] : [33,34,35]
1자리 - 0밀리초
2자리 - 0밀리초
3자리 - 1밀리초
4자리 - 1밀리초
5자리 - 3밀리초
6자리 - 6밀리초
7자리 - 11밀리초
8자리 - 21밀리초
9자리 - 32밀리초
10자리 - 38밀리초
11자리 - 45밀리초
12자리 - 60밀리초
13자리 - 78밀리초
14자리 - 97밀리초
15자리 - 115밀리초
16자리 - 168밀리초
17자리 - 239밀리초
18자리 - 385밀리초
19자리 - 678밀리초
20자리 - 1262밀리초
21자리 - 2331밀리초
22자리 - 4316밀리초
23자리 - 8287밀리초
24자리 - 16248밀리초
25자리 - 32032밀리초
26자리 - 63419밀리초
27자리 - 126191밀리초
28자리 - 248919밀리초
(임의 중단함)
 
calc(10, 4)
c2_num = 45
c2Arr[0] : [0,1]
c2Arr[1] : [0,2]
c2Arr[2] : [0,3]
c2Arr[3] : [0,4]
c2Arr[4] : [0,5]
c2Arr[5] : [0,6]
c2Arr[6] : [0,7]
c2Arr[7] : [0,8]
c2Arr[8] : [0,9]
c2Arr[9] : [1,2]
c2Arr[10] : [1,3]
c2Arr[11] : [1,4]
c2Arr[12] : [1,5]
c2Arr[13] : [1,6]
c2Arr[14] : [1,7]
c2Arr[15] : [1,8]
c2Arr[16] : [1,9]
c2Arr[17] : [2,3]
c2Arr[18] : [2,4]
c2Arr[19] : [2,5]
c2Arr[20] : [2,6]
c2Arr[21] : [2,7]
c2Arr[22] : [2,8]
c2Arr[23] : [2,9]
c2Arr[24] : [3,4]
c2Arr[25] : [3,5]
c2Arr[26] : [3,6]
c2Arr[27] : [3,7]
c2Arr[28] : [3,8]
c2Arr[29] : [3,9]
c2Arr[30] : [4,5]
c2Arr[31] : [4,6]
c2Arr[32] : [4,7]
c2Arr[33] : [4,8]
c2Arr[34] : [4,9]
c2Arr[35] : [5,6]
c2Arr[36] : [5,7]
c2Arr[37] : [5,8]
c2Arr[38] : [5,9]
c2Arr[39] : [6,7]
c2Arr[40] : [6,8]
c2Arr[41] : [6,9]
c2Arr[42] : [7,8]
c2Arr[43] : [7,9]
c2Arr[44] : [8,9]
cn_num = 210
cnArr[0] : [0,1,2,9,10,17]
cnArr[1] : [0,1,3,9,11,18]
cnArr[2] : [0,1,4,9,12,19]
cnArr[3] : [0,1,5,9,13,20]
cnArr[4] : [0,1,6,9,14,21]
cnArr[5] : [0,1,7,9,15,22]
cnArr[6] : [0,1,8,9,16,23]
cnArr[7] : [0,2,3,10,11,24]
cnArr[8] : [0,2,4,10,12,25]
cnArr[9] : [0,2,5,10,13,26]
cnArr[10] : [0,2,6,10,14,27]
cnArr[11] : [0,2,7,10,15,28]
cnArr[12] : [0,2,8,10,16,29]
cnArr[13] : [0,3,4,11,12,30]
cnArr[14] : [0,3,5,11,13,31]
cnArr[15] : [0,3,6,11,14,32]
cnArr[16] : [0,3,7,11,15,33]
cnArr[17] : [0,3,8,11,16,34]
cnArr[18] : [0,4,5,12,13,35]
cnArr[19] : [0,4,6,12,14,36]
cnArr[20] : [0,4,7,12,15,37]
cnArr[21] : [0,4,8,12,16,38]
cnArr[22] : [0,5,6,13,14,39]
cnArr[23] : [0,5,7,13,15,40]
cnArr[24] : [0,5,8,13,16,41]
cnArr[25] : [0,6,7,14,15,42]
cnArr[26] : [0,6,8,14,16,43]
cnArr[27] : [0,7,8,15,16,44]
cnArr[28] : [1,2,3,17,18,24]
cnArr[29] : [1,2,4,17,19,25]
cnArr[30] : [1,2,5,17,20,26]
cnArr[31] : [1,2,6,17,21,27]
cnArr[32] : [1,2,7,17,22,28]
cnArr[33] : [1,2,8,17,23,29]
cnArr[34] : [1,3,4,18,19,30]
cnArr[35] : [1,3,5,18,20,31]
cnArr[36] : [1,3,6,18,21,32]
cnArr[37] : [1,3,7,18,22,33]
cnArr[38] : [1,3,8,18,23,34]
cnArr[39] : [1,4,5,19,20,35]
cnArr[40] : [1,4,6,19,21,36]
cnArr[41] : [1,4,7,19,22,37]
cnArr[42] : [1,4,8,19,23,38]
cnArr[43] : [1,5,6,20,21,39]
cnArr[44] : [1,5,7,20,22,40]
cnArr[45] : [1,5,8,20,23,41]
cnArr[46] : [1,6,7,21,22,42]
cnArr[47] : [1,6,8,21,23,43]
cnArr[48] : [1,7,8,22,23,44]
cnArr[49] : [2,3,4,24,25,30]
cnArr[50] : [2,3,5,24,26,31]
cnArr[51] : [2,3,6,24,27,32]
cnArr[52] : [2,3,7,24,28,33]
cnArr[53] : [2,3,8,24,29,34]
cnArr[54] : [2,4,5,25,26,35]
cnArr[55] : [2,4,6,25,27,36]
cnArr[56] : [2,4,7,25,28,37]
cnArr[57] : [2,4,8,25,29,38]
cnArr[58] : [2,5,6,26,27,39]
cnArr[59] : [2,5,7,26,28,40]
cnArr[60] : [2,5,8,26,29,41]
cnArr[61] : [2,6,7,27,28,42]
cnArr[62] : [2,6,8,27,29,43]
cnArr[63] : [2,7,8,28,29,44]
cnArr[64] : [3,4,5,30,31,35]
cnArr[65] : [3,4,6,30,32,36]
cnArr[66] : [3,4,7,30,33,37]
cnArr[67] : [3,4,8,30,34,38]
cnArr[68] : [3,5,6,31,32,39]
cnArr[69] : [3,5,7,31,33,40]
cnArr[70] : [3,5,8,31,34,41]
cnArr[71] : [3,6,7,32,33,42]
cnArr[72] : [3,6,8,32,34,43]
cnArr[73] : [3,7,8,33,34,44]
cnArr[74] : [4,5,6,35,36,39]
cnArr[75] : [4,5,7,35,37,40]
cnArr[76] : [4,5,8,35,38,41]
cnArr[77] : [4,6,7,36,37,42]
cnArr[78] : [4,6,8,36,38,43]
cnArr[79] : [4,7,8,37,38,44]
cnArr[80] : [5,6,7,39,40,42]
cnArr[81] : [5,6,8,39,41,43]
cnArr[82] : [5,7,8,40,41,44]
cnArr[83] : [6,7,8,42,43,44]
cnArr[84] : [9,10,11,17,18,24]
cnArr[85] : [9,10,12,17,19,25]
cnArr[86] : [9,10,13,17,20,26]
cnArr[87] : [9,10,14,17,21,27]
cnArr[88] : [9,10,15,17,22,28]
cnArr[89] : [9,10,16,17,23,29]
cnArr[90] : [9,11,12,18,19,30]
cnArr[91] : [9,11,13,18,20,31]
cnArr[92] : [9,11,14,18,21,32]
cnArr[93] : [9,11,15,18,22,33]
cnArr[94] : [9,11,16,18,23,34]
cnArr[95] : [9,12,13,19,20,35]
cnArr[96] : [9,12,14,19,21,36]
cnArr[97] : [9,12,15,19,22,37]
cnArr[98] : [9,12,16,19,23,38]
cnArr[99] : [9,13,14,20,21,39]
cnArr[100] : [9,13,15,20,22,40]
cnArr[101] : [9,13,16,20,23,41]
cnArr[102] : [9,14,15,21,22,42]
cnArr[103] : [9,14,16,21,23,43]
cnArr[104] : [9,15,16,22,23,44]
cnArr[105] : [10,11,12,24,25,30]
cnArr[106] : [10,11,13,24,26,31]
cnArr[107] : [10,11,14,24,27,32]
cnArr[108] : [10,11,15,24,28,33]
cnArr[109] : [10,11,16,24,29,34]
cnArr[110] : [10,12,13,25,26,35]
cnArr[111] : [10,12,14,25,27,36]
cnArr[112] : [10,12,15,25,28,37]
cnArr[113] : [10,12,16,25,29,38]
cnArr[114] : [10,13,14,26,27,39]
cnArr[115] : [10,13,15,26,28,40]
cnArr[116] : [10,13,16,26,29,41]
cnArr[117] : [10,14,15,27,28,42]
cnArr[118] : [10,14,16,27,29,43]
cnArr[119] : [10,15,16,28,29,44]
cnArr[120] : [11,12,13,30,31,35]
cnArr[121] : [11,12,14,30,32,36]
cnArr[122] : [11,12,15,30,33,37]
cnArr[123] : [11,12,16,30,34,38]
cnArr[124] : [11,13,14,31,32,39]
cnArr[125] : [11,13,15,31,33,40]
cnArr[126] : [11,13,16,31,34,41]
cnArr[127] : [11,14,15,32,33,42]
cnArr[128] : [11,14,16,32,34,43]
cnArr[129] : [11,15,16,33,34,44]
cnArr[130] : [12,13,14,35,36,39]
cnArr[131] : [12,13,15,35,37,40]
cnArr[132] : [12,13,16,35,38,41]
cnArr[133] : [12,14,15,36,37,42]
cnArr[134] : [12,14,16,36,38,43]
cnArr[135] : [12,15,16,37,38,44]
cnArr[136] : [13,14,15,39,40,42]
cnArr[137] : [13,14,16,39,41,43]
cnArr[138] : [13,15,16,40,41,44]
cnArr[139] : [14,15,16,42,43,44]
cnArr[140] : [17,18,19,24,25,30]
cnArr[141] : [17,18,20,24,26,31]
cnArr[142] : [17,18,21,24,27,32]
cnArr[143] : [17,18,22,24,28,33]
cnArr[144] : [17,18,23,24,29,34]
cnArr[145] : [17,19,20,25,26,35]
cnArr[146] : [17,19,21,25,27,36]
cnArr[147] : [17,19,22,25,28,37]
cnArr[148] : [17,19,23,25,29,38]
cnArr[149] : [17,20,21,26,27,39]
cnArr[150] : [17,20,22,26,28,40]
cnArr[151] : [17,20,23,26,29,41]
cnArr[152] : [17,21,22,27,28,42]
cnArr[153] : [17,21,23,27,29,43]
cnArr[154] : [17,22,23,28,29,44]
cnArr[155] : [18,19,20,30,31,35]
cnArr[156] : [18,19,21,30,32,36]
cnArr[157] : [18,19,22,30,33,37]
cnArr[158] : [18,19,23,30,34,38]
cnArr[159] : [18,20,21,31,32,39]
cnArr[160] : [18,20,22,31,33,40]
cnArr[161] : [18,20,23,31,34,41]
cnArr[162] : [18,21,22,32,33,42]
cnArr[163] : [18,21,23,32,34,43]
cnArr[164] : [18,22,23,33,34,44]
cnArr[165] : [19,20,21,35,36,39]
cnArr[166] : [19,20,22,35,37,40]
cnArr[167] : [19,20,23,35,38,41]
cnArr[168] : [19,21,22,36,37,42]
cnArr[169] : [19,21,23,36,38,43]
cnArr[170] : [19,22,23,37,38,44]
cnArr[171] : [20,21,22,39,40,42]
cnArr[172] : [20,21,23,39,41,43]
cnArr[173] : [20,22,23,40,41,44]
cnArr[174] : [21,22,23,42,43,44]
cnArr[175] : [24,25,26,30,31,35]
cnArr[176] : [24,25,27,30,32,36]
cnArr[177] : [24,25,28,30,33,37]
cnArr[178] : [24,25,29,30,34,38]
cnArr[179] : [24,26,27,31,32,39]
cnArr[180] : [24,26,28,31,33,40]
cnArr[181] : [24,26,29,31,34,41]
cnArr[182] : [24,27,28,32,33,42]
cnArr[183] : [24,27,29,32,34,43]
cnArr[184] : [24,28,29,33,34,44]
cnArr[185] : [25,26,27,35,36,39]
cnArr[186] : [25,26,28,35,37,40]
cnArr[187] : [25,26,29,35,38,41]
cnArr[188] : [25,27,28,36,37,42]
cnArr[189] : [25,27,29,36,38,43]
cnArr[190] : [25,28,29,37,38,44]
cnArr[191] : [26,27,28,39,40,42]
cnArr[192] : [26,27,29,39,41,43]
cnArr[193] : [26,28,29,40,41,44]
cnArr[194] : [27,28,29,42,43,44]
cnArr[195] : [30,31,32,35,36,39]
cnArr[196] : [30,31,33,35,37,40]
cnArr[197] : [30,31,34,35,38,41]
cnArr[198] : [30,32,33,36,37,42]
cnArr[199] : [30,32,34,36,38,43]
cnArr[200] : [30,33,34,37,38,44]
cnArr[201] : [31,32,33,39,40,42]
cnArr[202] : [31,32,34,39,41,43]
cnArr[203] : [31,33,34,40,41,44]
cnArr[204] : [32,33,34,42,43,44]
cnArr[205] : [35,36,37,39,40,42]
cnArr[206] : [35,36,38,39,41,43]
cnArr[207] : [35,37,38,40,41,44]
cnArr[208] : [36,37,38,42,43,44]
cnArr[209] : [39,40,41,42,43,44]
1자리 - 0밀리초
2자리 - 0밀리초
3자리 - 1밀리초
4자리 - 1밀리초
5자리 - 3밀리초
6자리 - 5밀리초
7자리 - 9밀리초
8자리 - 16밀리초
9자리 - 20밀리초
10자리 - 25밀리초
11자리 - 33밀리초
12자리 - 46밀리초
13자리 - 56밀리초
14자리 - 62밀리초
15자리 - 77밀리초
16자리 - 112밀리초
17자리 - 177밀리초
18자리 - 303밀리초
19자리 - 545밀리초
20자리 - 1037밀리초
21자리 - 1823밀리초
22자리 - 3380밀리초
23자리 - 6495밀리초
24자리 - 12661밀리초
25자리 - 25027밀리초
26자리 - 49365밀리초
27자리 - 97600밀리초
28자리 - 193509밀리초
29자리 - 389292밀리초
30자리 - 777389밀리초
31자리 - 1583799밀리초
32자리 - 3106918밀리초
(임의 중단함)
*/