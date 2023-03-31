package com.hung.enums;


/**
 * @author itsol.hungtt
 */
public enum MessageEnum  {
    OK("00", "Success"),
    UNHANDLED_ERROR("0001", "Unhandled error", "Unhandled error"),
    OBJECT_NOT_FOUND("0002", "Object not found", "%s không tồn tại"),
    SQL_EXCEPTION("0003", "Sql exception", "Lỗi thao tác với Database %s"),
    NOT_ALLOW_UPDATE_RECORD("SEATELLER-018", "bạn không được phép thực hiện request này"),
    QUERY_ODS("0004","Không tồn tại bản ghi ODS"),
    RECORD_NOT_EXISTED("SEATELLER-004", "Bản ghi không tồn tại"),
    INPUTTER_USERMAN("INPUTTER_USERMAN","Inputter không hợp lệ"),
    AUTHORISER_USERMAN("AUTHORISER_USERMAN","Authoriser không hợp lệ"),
    INAU_T24("INAU-T24","T24 - Người dùng không có quyền duyệt"),
    AU_T24("AU_T24","T24 - Duyệt thất bại"),
    STATUS_INVALID("STATUS_INVALID","Trạng thái bản ghi không hợp lệ"),
    CONNECT_T24_ERROR("SEATELLER-003", "CONNECT TO CORE T24 ERROR"),
    QUERY_DATABASE("0005","Không tồn tại bản ghi thỏa mã");

    private final String code;
    private String message;
    private String messageFormat;

    MessageEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    MessageEnum(String code, String message, String messageFormat) {
        this.code = code;
        this.message = message;
        this.messageFormat = messageFormat;
    }



    public MessageEnum format(Object... str) {
        if (this.messageFormat != null) {
            this.message = String.format(this.messageFormat, str);
        }
        return this;
    }
}
