package com.hmyu.place.vo;

import com.hmyu.place.constant.MessageConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Desc : 메시지 VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageVo {
	private String code;
	private String message;
	private String extraMessage;
	private String delYn = "N";
	
	public MessageVo(String code, String message, String extraMessage) {
		super();
		this.code = code;
		this.message = message;
		this.extraMessage = extraMessage;
	}
	
	public MessageVo(MessageConstant mc) {
		super();
		this.code = mc.getCode();
		this.message = mc.getMessage();
		this.extraMessage = mc.getExtraMessage();
	}

}
