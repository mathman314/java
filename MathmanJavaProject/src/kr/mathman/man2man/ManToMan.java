package kr.mathman.man2man;

/**
 * 
 * @author mathman
 *
 */
public class ManToMan {
	static final boolean infoLog = false;	// 조합 배열정보 로그 여부
	
	static int[][] c2Arr;	// 2인 조합 배열
	static int[][] cnArr;	// n인 조합 배열
	
	static long nCheck;		// 체크 회수
	static long startTime;	// 시작 시각(밀리초)
	
	static int mm;	// m값
	
	public static void main(String[] args) {
		calc(5,4);
		print("걸린 시간 : " + leftTime());
		print("체크수 : " + nCheck);
		
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
			print("체크수 : " + nCheck);
			print("-----------------------------");
		}
	}
	
	/**
	 * 배열 정보 출력
	 */
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
		
		mm = m;
		
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
		nCheck = 0;
		
		// 2인 조합 배열에 모든 경우를 넣어 계산
//		int ja = 0;	// 자리수 체크
//		for (i=1; i<Math.pow(2,c2Arr.length)-1; i++) {
//			boolean[] bl = new boolean[c2Arr.length];	// 기본값은 모두 false
//			String bin = Integer.toBinaryString(i);	// 2진수
//			
//			if (bin.length()>ja) {
//				print(bin.length() + "자리 - " + leftTime());
//				ja = bin.length();
//			}
//			for (j=0; j<ja; j++) {
//				if (bin.substring(j,j+1).equals("1")) bl[c2Arr.length-ja+j] = true;
//			}
//			
//			boolean chk = chkAll_0or1_5(bl);
//			
//			// 전체 출력
//			//print("chk["+bin+"] : " + chk);
//			
//			// 모두 0이거나 모두 1인 경우에 해당되지 않는 방법이 있으면 체크 종료
//			if (!chk) {
//				while(bin.length()<c2Arr.length) {
//					bin = "0" + bin;
//				}
//				print("예외 사례 발견 ==> chk["+bin+"] : " + chk);
//				return true;
//			}
//		}
		
		// 전수 조사가 아닌 쌍 체크 방식
		for (i=1; i<=c2Arr.length/2; i++) {
			boolean[] bl = new boolean[c2Arr.length];	// 기본값은 모두 false
			
			print(i + "쌍 체크 시작 - " + leftTime());
			
			bl[0] = true;
			
			if (i>1) {
				// 재귀함수 호출
				if (chkMethod2(i, 1, 0, bl)) return true;
			} else {
//				print("	check(1)");	// 쌍체크 확인
				if (chkMethod(bl)) return true;
			}
			
		}
		return false;
	}
	
	/**
	 * 쌍 체크
	 * @param i	: 목표 쌍
	 * @param c	: 현재 쌍
	 * @param j	: 체크 시작점(이전쌍 위치)
	 * @param bl	: 관계 배열
	 * @return
	 */
	private static boolean chkMethod2(int i, int c, int j, boolean[] bl) {
//		print("chkMethod2("+i+","+c+","+j+")");
		for (int k=j+1; k<c2Arr.length; k++) {
//			print("chkMethod2("+i+","+c+","+j+","+k+")");
			bl[k] = true;
			if (i>c+1) {
				if (chkMethod2(i, c+1, k, bl)) return true;
			} else {
//				print("	check("+i+","+c+","+j+","+k+")");	// 쌍체크 확인
				if (chkMethod(bl)) return true;
			}
			bl[k] = false;
			
			// 쌍체크 개선 - 중복 통과
			if (c==1) {
				if (k==1)
					k= 2 * (mm-2);
				else
					break;
			}
		}
		return false;
	}
	
	/**
	 * 조건 확인
	 * @param bl
	 * @return	예외사례 발견시 true
	 */
	private static boolean chkMethod(boolean[] bl) {
		nCheck++;		// 체크 회수
		boolean chk = chkAll_0or1_5(bl);
		
		// 모두 0이거나 모두 1인 경우에 해당되지 않는 방법이 있으면 체크 종료
		if (!chk) {
			String bin = "";	// 2진수
			for (int i=0; i<bl.length; i++) bin += bl[i] ? "1" : "0";
			print("예외 사례 발견 ==> chk["+bin+"] : " + chk);
			return true;
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
		String ret = "";
		long ms = System.currentTimeMillis() - startTime;
		if (ms<1000)
			ret = ms + " 밀리초";
		else if (ms<3600000)
			ret = String.format("%.3f", (float)ms/1000) + " 초";
		else
			ret = String.format("%.3f", (float)ms/3600000) + " 시간";
		return ret;
	}
	
	/**
	 * 문자열 출력
	 */
	private static void print(String str) {
		System.out.println(str);
	}
	
}
