package com.example.questionnaire.constants;

public enum RtnCode {

	// 列舉 200 400 401 403 404
	// 找的到200 找不到404 剩下400
	// 401 403 跟權限有關
	SUCCESSFUL(200, "Successful"), // 成功
	QNPARAM_ERROR(400, "Questionnaire_Param_error"), // 參數錯誤
	QUPARAM_ERROR(400, "Question_Param_error"), // 參數錯誤
	ID_ERROR(400,"Id_error"),//id錯誤
	ERROR(400,"_error"),//id錯誤
	QUESTIONNAIRE_ID_NOT_FOUND(400,"_Notfount"),//id錯誤
	NAME_ALREADY_EXISTS(400,"NameExist_error"),

	PHONE_NUMBER_ALREADY_EXISTS(400,"PhoneExist_error"),
	EMAIL_ALREADY_EXISTS(400, "EmailExist_error"),
	ID_NOTFOUNT(404,"ID_Notfount"),//找不到ID
	UPDATE_ERROR(400,"Update_error"),
	GETUSERINFO_ERROR(400,"Getuserinfo_error"),

	NOT_PUBLISHED(400,"Not published"),
	QNID_ERROR(400, "Questionnaire_Id_error"),  // 參數錯誤

//	ACCOUNT_EXISTED(400,"Account_existed"),//帳號已存在
//	ACCOUNT_NOTFOUNT(404,"Account_Notfount"),//找不到帳號
//	BALANCE_IS_INSUFFICIENT(400,"Balance is insufficient"),//餘額不足 
	;

	private int code;

	private String message;

	// source -->
	private RtnCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	// 只有用到getter
	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
