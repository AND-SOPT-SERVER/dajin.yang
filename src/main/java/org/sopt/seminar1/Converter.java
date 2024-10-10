package org.sopt.seminar1;

public class Converter {

    /**
     * 문자열 ID를 Long 타입으로 변환
     */

    public static Long parseId(final String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효하지 않은 ID");
        }
    }
}
