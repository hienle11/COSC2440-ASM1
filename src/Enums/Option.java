package Enums;

import java.util.HashMap;
import java.util.Map;

public enum Option {
    INVALID(0),
    VIEW_ALL_ENROLMENTS(1),
    SEARCH_ENROLMENTS_BY_STUDENT_AND_SEMESTER(2),
    SEARCH_ENROLMENTS_BY_COURSE_AND_SEMESTER(3),
    SEARCH_ENROLMENTS_BY_SEMESTER(4),
    ENROLMENTS_ADD(5),
    ENROLMENTS_UPDATE(6),
    ENROLMENTS_DELETE(7),
    ENROLMENTS_EXPORT(8),
    EXIT(9);

    private final int value;
    private static final Map<Integer, Option> map = new HashMap<>();

    Option(int value) {
        this.value = value;
    }

    static {
        for (Option pageType : Option.values()) {
            map.put(pageType.value, pageType);
        }
    }

    public static Option valueOf(int val) {
        return map.get(val);
    }

    public int getValue() {
        return value;
    }

}
