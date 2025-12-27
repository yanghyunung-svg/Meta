package com.common.constants;

/**
 * @file : AppConstants
 * @desc : Constants 
 */
public class AppConstants {

    public static final class EXCN_RSLT_CD {    // 실행결과
        public static final Short NOR = 0;     // 실행결과정상
        public static final Short ERR = 1;     // 실행결과오류
    }
    public static final class FUNC_SE { //DB FUNCTION
        public static final String SEL = "S";     // SELECT
        public static final String INS = "I";     // INSERT
        public static final String UPD = "U";     // UPDATE
        public static final String DEL = "D";     // DELETE
        public static final String LCK = "L";     // LOCK SELECT
        public static final String ALL = "A";     // ALL SELECT
    }

}
