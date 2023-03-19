package com.hmyu.place.vo;

import com.hmyu.place.constant.MessageConstant;
import com.hmyu.place.constant.StringConstant;
import org.json.simple.JSONObject;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;


/**
 * Desc : 공통 응답 오브젝트
 */
public class ResponseVo extends JSONObject {
	
	private static final long serialVersionUID = -4106296996913677632L;
	
	private HashMap<String,Object> result; 
	private Object data;
	
	public ResponseVo() {
		result = new HashMap<String,Object>();
		result.put(StringConstant.TXID, UUID.randomUUID().toString().replaceAll("-", ""));
		result.put(StringConstant.TIMESTAMP, LocalDateTime.now().toString());
		
		data = new HashMap<String, Object>();
		
		this.put(StringConstant.RESULT, result);
		this.put(StringConstant.DATA, data);
		
		this.setResultMessage(MessageConstant.OK.getCode(), MessageConstant.OK.getMessage(), "");
	}
	
	public ResponseVo(MessageConstant message) {
		result = new HashMap<String,Object>();
		result.put(StringConstant.TXID, UUID.randomUUID().toString().replaceAll("-", ""));
		result.put(StringConstant.TIMESTAMP, LocalDateTime.now().toString());
		
		data = new HashMap<String, Object>();
		
		this.put(StringConstant.RESULT, result);
		this.put(StringConstant.DATA, data);
		
		this.setResultMessage(message.getCode(), message.getMessage(), "");
	}
	
	public ResponseVo(MessageConstant message, String extraMessage) {
		result = new HashMap<String,Object>();
		result.put(StringConstant.TXID, UUID.randomUUID().toString().replaceAll("-", ""));
		result.put(StringConstant.TIMESTAMP, LocalDateTime.now().toString());
		
		data = new HashMap<String, Object>();
		
		this.put(StringConstant.RESULT, result);
		this.put(StringConstant.DATA, data);
		
		this.setResultMessage(message.getCode(), message.getMessage(), extraMessage);
	}
	
	@SuppressWarnings("unchecked")
	public ResponseVo(JSONObject json) {
		result = (HashMap<String, Object>) json.get("result");
		data = json.get("data");
		
		this.put(StringConstant.RESULT, result);
		this.put(StringConstant.DATA, data);
	}

	/**
	 * Desc : 결과 정보 오브젝트 반환
	 */
	public HashMap<String, Object> getResult() {
		return result;
	}

	/**
	 * Desc : 결과 정보 설정
	 */
	public void setResult(HashMap<String, Object> result) {
		this.result.put(StringConstant.CODE, result.get(StringConstant.CODE));
		this.result.put(StringConstant.MESSAGE, result.get(StringConstant.MESSAGE));
		this.result.put(StringConstant.EXTRA_MESSAGE, result.get(StringConstant.EXTRA_MESSAGE));
		this.result = result;
	}

	/**
	 * Desc : 데이터 오브젝트 반환
	 */
	public Object getData() {
		return data;
	}
	
	/**
	 * Desc : 결과 메시지 정보 설정
	 */
	public void setResultMessage(String errCode, String errMsg, String extraMsg) {
		this.result.put(StringConstant.CODE, errCode);
		this.result.put(StringConstant.MESSAGE, errMsg);
		this.result.put(StringConstant.EXTRA_MESSAGE, extraMsg);
	}
	
	/**
	 * Desc : 결과 메시지 정보 설정
	 */
	public void setResultMessage(MessageConstant message) {
		this.result.put(StringConstant.CODE, message.getCode());
		this.result.put(StringConstant.MESSAGE, message.getMessage());
		this.result.put(StringConstant.EXTRA_MESSAGE, message.getExtraMessage());
	}

	/**
	 * Desc : 결과 메시지 정보 설정
	 */
	public void setResultMessage(MessageConstant message, String extraMessage) {
		this.result.put(StringConstant.CODE, message.getCode());
		this.result.put(StringConstant.MESSAGE, message.getMessage());
		this.result.put(StringConstant.EXTRA_MESSAGE, extraMessage);
	}
	
	/**
	 * Desc : 결과 메시지 정보 설정
	 */
	public void setResultMessage(MessageVo vo) {
		this.result.put(StringConstant.CODE, vo.getCode());
		this.result.put(StringConstant.MESSAGE, vo.getMessage());
		this.result.put(StringConstant.EXTRA_MESSAGE, vo.getExtraMessage());
	}
	
	/**
	 * Desc : 트랜잭션 아이디 설정
	 */
	public void setTxId(String txid) {
		this.result.put(StringConstant.TXID, txid);
	}
	
	/**
	 * Desc : 데이터 설정
	 */
	public void setData(Object param) {
		data = param;
		this.put(StringConstant.DATA, data);
	}

	@Override
	public String toString() {
		return "ResponseObject [result=" + result + ", data=" + data + "]";
	}
}
