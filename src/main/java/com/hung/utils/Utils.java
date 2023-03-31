package com.hung.utils;

import org.springframework.util.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class Utils {
    public final static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    public final static String STANDARD_DATE_FORMAT = "dd-MM-yyyy";
    public final static DateTimeFormatter FORMATTER = DateTimeFormatter
            .ofPattern(DATE_FORMAT)
            .withZone(ZoneId.from(ZoneOffset.UTC));
    public final static DateTimeFormatter STANDARD_DATE_FORMATTER = DateTimeFormatter
            .ofPattern(STANDARD_DATE_FORMAT).withZone(ZoneId.from(ZoneOffset.UTC));

    /**
     * Function này nhận đầu vào là 1 string và trả về true nếu định dạng dd-MM-yyyy HH:mm:ss, ngược lại trả về false
     */
    public static boolean validateDateFormat(String sDate) {
        try {
            LocalDateTime.from(FORMATTER.parse(sDate));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Function này nhận đầu vào là 1 string tiếng việt có dấu và trả về 1 string mới đã remove dấu
     */
    public static String removeAccent(String s) {
        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String convertedValue = pattern.matcher(temp).replaceAll("").replaceAll("Đ", "D").replace("đ", "d");

        return removeT24Character(convertedValue).trim();
    }

    public static String removeT24Character(String s) {
        String value = s.replace("_", "'_'")
                .replace("//", "^^")
                .replace("NULL", "\\NULL")
                .replace("?", "%?%")
                .replace("|", "%|%")
                .replace("^", "%^%")
                .replace("\"", "|");

        if (value.contains(",")) {
            return "\"" + value + ("\"");
        }
        return value;
    }

    public static StringBuilder toStringBuilderLimit35Character(String key, String value) {

        List<String> listValue = parseStringToListOf35(value);
        StringBuilder builder = new StringBuilder();

        for (int index = 0; index < listValue.size(); index++) {
            builder.append(key);
            builder.append(":");
            builder.append(index + 1);
            builder.append(":=");

            if (!StringUtils.isEmpty(listValue.get(index))) {
                builder.append(Utils.removeAccent(listValue.get(index)));
                builder.append(",");
            }
        }
        return builder;
    }

    /**
     * Hàm này mục đích cắt chuỗi String thành các chuỗi con để gửi vào mutil trên t24
     * Các chuỗi con có tối đa 35 ký tự
     * Các chuỗi sẽ không được bắt đầu và kết thúc bằng dấu cách (nếu có thì số lượng cũng phải ít nhất)
     * Trả ra danh sách các chuỗi con để mapping vào T24
     *
     * @param source - dau vao
     * @return list string
     */
    static List<String> parseStringToListOf35(String source) {
        List<String> result = new ArrayList<>();
        String trimValue = trimAllSpaceInString(source);
        if (StringUtils.isEmpty(trimValue)) {
            return result;
        }
        while (trimValue.length() > 35) {
            String fisrt35 = trimValue.substring(0, 35);
            if (fisrt35.charAt(34) == ' ') {// ky tu cuoi co phai la space hay khong?
                result.add(fisrt35);
                trimValue = trimValue.substring(35).trim();
            } else {// neu khong phai thi lui lai 1 tu
                int idx = fisrt35.lastIndexOf(' ');
                if (idx < 0) { // neu la 1 tu dai qua 35 ky tu thi coi nhu nhap lieu loi va break luon. Khong cat nua
                    break;
                }
                fisrt35 = trimValue.substring(0, idx);
                result.add(fisrt35);
                trimValue = trimValue.substring(idx).trim();
            }
        }
        result.add(trimValue);
        return result;
    }

    /**
     * Trim all space last, first and between string
     *
     * @param value
     * @return
     */
    static String trimAllSpaceInString(String value) {
        if (StringUtils.isEmpty(value)) {
            return value;
        }
        value = value.replaceAll(",", ", ");
        value = value.replaceAll("-", " - ");
        return value.trim().replaceAll("  +", " ");
    }

    /**
     * @param fullName nhận vào 1 giá tri full nam -> example: Nguyen Van Cong
     * @return only name value -> Cong
     */
    public static String subCustomerName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) {
            return null;
        }

        String[] strArray = fullName.split(" ");
        return strArray[strArray.length - 1].trim();
    }

    public static String formatDateT24(String strDate) throws ParseException {

        DateFormat inputFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date da = inputFormatter.parse(strDate);

        DateFormat outputFormatter = new SimpleDateFormat("yyyyMMdd");
        return outputFormatter.format(da);
    }

    public static String integerToString(Integer i) {
        try {
            return String.valueOf(i);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    /**
     * Function này nhận đầu vào là 1 giá trị kiểu localDate và trả ra 1 string theo định dạng dd-MM-yyyy
     */
    public static String localDateToString(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return STANDARD_DATE_FORMATTER.format(localDate);
    }

    public static String longToString(Long l) {
        try {
            return String.valueOf(l);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public static String localDateTimeToString(LocalDateTime instant) {
        if (instant == null) {
            return null;
        }
        return FORMATTER.format(instant);
    }

    public static StringBuilder splitStringValueToBuildOfs(String key, String value) {
        StringBuilder builder = new StringBuilder();
        String[] values = value.split(";");

        for (int index = 0; index < values.length; index++) {
            builder.append(key);
            builder.append(":");
            builder.append(index + 1);
            builder.append(":=");

            if (!StringUtils.isEmpty(values[index])) {
                builder.append(Utils.removeAccent(values[index]));
            }
            builder.append(",");
        }
        return builder;
    }

    public static List<String> getListStringAfterSplitByCharacter(String actionT24, String splitCharacter) {
        List<String> listActionResult = new ArrayList<>();
        String[] lstActionInput = actionT24.split(splitCharacter);
        for (String s : lstActionInput) {
            if (!StringUtils.isEmpty(s)) {
                String action = s.trim();
                listActionResult.add(action);
            }
        }
        return listActionResult;
    }

    public static StringBuilder toStringBuilder(String key, String value, int index) {
        StringBuilder builder = new StringBuilder();
        builder.append(key);
        builder.append(":");
        builder.append(index);
        builder.append(":=");
        if (!StringUtils.isEmpty(value)) {
            builder.append(Utils.removeAccent(value));
        }
        builder.append(",");
        return builder;
    }

/*    public static StatusEnum getStatusResend(StatusEnum currentStatus) {
        if (currentStatus == null) return StatusEnum.PENDING;
        if (StatusEnum.APPROVED_SUCCESS.equals(currentStatus)) return StatusEnum.APPROVED_SUCCESS;
        if (StatusEnum.REJECTED.equals(currentStatus)) {
            return StatusEnum.PENDING_RESEND;
        }
        return Arrays.asList(new StatusEnum[]{StatusEnum.PENDING_RESEND, StatusEnum.APPROVED_ERROR}).contains(currentStatus) ? StatusEnum.PENDING_RESEND : StatusEnum.PENDING;
    }
    public static String localDateToStringTimeStamp(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return "";
        }
        return String.valueOf(Timestamp.valueOf(localDateTime));
    }*/
}
