package com.shoki.lib.log;

import android.util.Log;

/**
 * 공통 로그 클래스
 * @author 이민석
 *
 */
public class Loger {
	
	private static boolean mFlag = true;
	
	/**
	 * 문자열 메세지 로그
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, String msg) {
		if(mFlag) {
			String log = buildLogMsg(msg);
			Log.d(tag, log);	
		}
	}

	/**
	 * 정수형 메세지 로그
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag, int msg) {
		if(mFlag) {
			String log = buildLogMsg(String.valueOf(msg));
			Log.d(tag, log);	
		}
	}
	
	
	/**
	 * 에러 발생한 클래스, 메소드, 라인넘버 출력하는 함수
	 * @param message
	 * @return
	 */
	private static String buildLogMsg(String message) {

        StackTraceElement ste = Thread.currentThread().getStackTrace()[4];         
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(ste.getFileName());
        sb.append(" > ");
        sb.append(ste.getMethodName());
        sb.append(" > #");
        sb.append(ste.getLineNumber());
        sb.append("] ");
        sb.append(message);
        return sb.toString();
    }
}