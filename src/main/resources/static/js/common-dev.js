const common = {
    /**
     * 시작일자, 종료일자 설정
     * @param startDateId - 시작일자 id
     * @param endDateId - 종료일자 id
     * @param month - 3,6,9,12
     */
    searchDateFormat : (startDateId, endDateId, month) => {
        const today = new Date();
        const endDate = new Date();

        switch (month) {
            case "1":
                // 1개월 전 날짜 구하기
                endDate.setMonth(today.getMonth() - 1);
                break;
            case "3":
                // 3개월 전 날짜 구하기
                endDate.setMonth(today.getMonth() - 3);
                break;
            case "6":
                // 6개월 전 날짜 구하기
                endDate.setMonth(today.getMonth() - 6);
                break;
            case "9":
                // 9개월 전 날짜 구하기
                endDate.setMonth(today.getMonth() - 9);
                break;
            case "12":
                // 12개월 전 날짜 구하기
                endDate.setMonth(today.getMonth() - 12);
                break;
        }
        // 날짜 포맷 (yyyy-MM-dd)
        const nowYear = today.getFullYear();
        const nowMonth = String(today.getMonth() + 1).padStart(2, '0');
        const nowDay = String(today.getDate()).padStart(2, '0');

        const endYear = endDate.getFullYear();
        const endMonth = String(endDate.getMonth() + 1).padStart(2, '0');
        const endDay = String(endDate.getDate()).padStart(2, '0');

        $("#"+startDateId).val(`${endYear}-${endMonth}-${endDay}`);
        $("#"+endDateId).val(`${nowYear}-${nowMonth}-${nowDay}`);
    },

    /**
     * 시작일자, 종료일자 default -7일 설정
     * @param startDateId - 시작일자 id
     * @param endDateId - 종료일자 id
     * @Desc : 각 메뉴별 조회기간 default 설정 하는 함수
     */
    defaultSettingDate : (startDateId, endDateId) => {
        const today = new Date();
        const startDate = new Date();
        startDate.setDate(today.getDate() - 7); // ✔️ 7일 전으로 설정

        // 날짜 포맷 (yyyy-MM-dd)
        const startYear = startDate.getFullYear();
        const startMonth = String(startDate.getMonth() + 1).padStart(2, '0');
        const startDay = String(startDate.getDate()).padStart(2, '0');

        const nowYear = today.getFullYear();
        const nowMonth = String(today.getMonth() + 1).padStart(2, '0');
        const nowDay = String(today.getDate()).padStart(2, '0');

        $("#" + startDateId).val(`${startYear}-${startMonth}-${startDay}`);
        $("#" + endDateId).val(`${nowYear}-${nowMonth}-${nowDay}`);
    },

    /**
     * 사용자 핸드폰번호 유효성 체크
     * @param userMobileNo
     * @returns {boolean}
     */
    isMobileNo: (userMobileNo) => {
        const regNum = /^[0-9]+$/;

        // 시작이 010 인지 체크 && 11자리인지 체크 && 숫자만 입력받기 체크
        if (
            "010" !== userMobileNo.substr(0, 3) ||
            userMobileNo.length !== 11 ||
            !regNum.test(userMobileNo)
        ) {
            return true;
        }

        return false;
    },
    /**
     * 사용자 이메일 유효성 체크
     * @param userEmail
     * @returns {boolean}
     */
    isEmail: (userEmail) => {
        const regEmail = new RegExp("[a-z0-9._]+@[a-z]+.[a-z]{2,3}");

        if (!regEmail.test(userEmail)) {
            return true;
        }

        return false;
    },
    /**
     * 빈 값 체크
     * @param val
     * @returns {boolean}
     */
    isEmpty: (val) => {
        if (
            val === undefined ||
            val === "undefined" ||
            val === null ||
            val === "" ||
            (val !== null && typeof val === "object" && !Object.keys(val).length) ||
            (val !== null && Array.isArray(val) && !val.length)
        ) {
            return true;
        } else {
            return false;
        }
    },
    /**
     * 넘버 유효성 체크
     * @param val
     * @returns {boolean}
     */
    isNumberic: (val) => {
        if (
            val === undefined ||
            val === null ||
            val === "" ||
            (val !== null && typeof val === "object" && !Object.keys(val).length) ||
            (val !== null && Array.isArray(val) && !val.length)
        ) {
            return false;
        } else {
            return !isNaN(val) && !isNaN(parseFloat(val));
        }
    },
    /**
     * 핸드폰번호 값에 하이푼 추가
     * @param phoneNumber
     * @returns {*}
     */
    getDashAddPhoneNumber: (phoneNumber) => {
        try {
            const digitsOnly = phoneNumber.replace(/\D/g, "");
            if (common.isNumberic(digitsOnly)) {
                if (digitsOnly.length === 10) {
                    return digitsOnly.replace(/(\d{3})(\d{3})(\d{4})/, "$1-$2-$3");
                } else if (digitsOnly.length === 11) {
                    return digitsOnly.replace(/(\d{3})(\d{4})(\d{4})/, "$1-$2-$3");
                }
            }
            return phoneNumber;
        } catch (e) {
            return phoneNumber;
        }
    },
    /**
     * 천자리 콤마 처리
     * @param x
     * @returns {string}
     */
    getNumWithCommas: (x) => {
        if (common.isEmpty(x) || isNaN(x)) return "0";
        return Number(x).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },
    /**
     * 날짜 데이터 'yyyy-MM-dd HH:mm' 포맷팅
     * @param dateString
     * @returns {string}
     */
    formatDateYmdHmString: (dateString) => {
        if (common.isEmpty(dateString) || !isNaN(dateString)) return "-";

        // dateString을 Date 객체로 변환
        const date = new Date(dateString.replace(" ", "T"));

        // 년, 월, 일, 시간, 분을 개별적으로 추출하고 2자리 숫자로 만듦
        const yyyy = date.getFullYear();
        const MM = String(date.getMonth() + 1).padStart(2, "0");
        const dd = String(date.getDate()).padStart(2, "0");
        const HH = String(date.getHours()).padStart(2, "0");
        const mm = String(date.getMinutes()).padStart(2, "0");

        // 원하는 형식으로 조합
        return `${yyyy}-${MM}-${dd} ${HH}:${mm}`;
    },
    /**
     * 날짜 데이터 'yyyy-MM-dd' 포맷팅
     * @param dateString - yyyyMMdd
     * @returns {string} - yyyy-MM-dd hh:mi
     */
    formatDateShortString: (dateString) => {
        if (common.isEmpty(dateString) || isNaN(dateString) || dateString.length !== 8) return "-";
        // dateString을 Date 객체로 변환
        return dateString.replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3');
    },
    /**
     * 날짜 데이터 'yyyy-MM-dd HH:mm:ss' 포맷팅
     * @param dateString
     * @returns {string}
     */
    formatDateTimeString: (dateString) => {
      // null, undefined, 빈 문자열, 숫자 아님, 0 처리
      if (common.isEmpty(dateString) || isNaN(dateString) || Number(dateString) === 0) return '-';

      // 숫자로 들어올 경우 문자열로 변환
      const str = String(dateString).padEnd(14, '0'); // 자리수 부족 시 0으로 보정

      // yyyy-MM-dd HH:mm[:ss] 형식으로 변경
      return str.replace(
        /(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})(\d{2})/,
        '$1-$2-$3 $4:$5:$6'
      );
    },


    /**
   * 날자 데이터 'yyyyMMddHHmiss'를 'yyyy-MM-dd HH:mm' 형식으로 변경
   * @param dateString - yyyyMMddHHmiss
   * @returns {string} - yyyy-MM-dd HH:mm
   */
    convertDateTimeString: (dateString) => {
      // null이거나 빈값, 숫자가 아닐 경우 - 반환
      if (common.isEmpty(dateString) || isNaN(dateString) || Number(dateString) === 0) return '-'
      // yyyy-MM-dd HH:mm 형식으로 변경
      return dateString.substring(0, 12).replace(/(\d{4})(\d{2})(\d{2})(\d{2})(\d{2})/, '$1-$2-$3 $4:$5')
    },
    
    /**
     * 시간 데이터 'HH:MM:SS' 포맷팅
     * @param dateString - hhmiss
     * @returns {string} - hh:mi:ss
     */
    formatTimeString: (timeString) => {
        if (common.isEmpty(timeString) || isNaN(timeString) || timeString.length !== 6) return "-";
        // timeString Date 객체로 변환
        return timeString.replace(/(\d{2})(\d{2})(\d{2})/, '$1:$2:$3');
    },
    /**
     * 사업자번호 '123-45-67890' 포맷팅
     * @param bizNumber
     * @returns {*|string}
     */
    formatBizNumber: (bizNumber) => {
        if (common.isEmpty(bizNumber) || isNaN(bizNumber)) {
            return "-";
        } else if (bizNumber.length === 10) {
            return bizNumber.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
        } else {
            return bizNumber;
        }
    },
    /**
     * 법인번호 '123456-1234567' 포맷팅
     * @param corpNumber
     * @returns {*|string}
     */
    formatCorpNumber: (corpNumber) => {
        if (common.isEmpty(corpNumber) || isNaN(corpNumber)) {
            return "-";
        } else if (corpNumber.length === 13) {
            return corpNumber.replace(/(\d{6})(\d{7})/, '$1-$2');
        } else {
            return corpNumber;
        }
    },

    /**
     * 법인번호, 사업자번호 통합 '123456-1234567' 포맷팅
     * @param number
     * @returns {*|string}
     */
    formatTotalNumber: (number) => {
        if (common.isEmpty(number) || isNaN(number)) {
            return "-";
        } else if (number.length === 10) {
                     return number.replace(/(\d{3})(\d{2})(\d{5})/, '$1-$2-$3');
        } else if (number.length === 13) {
            return number.replace(/(\d{6})(\d{7})/, '$1-$2');
        } else {
            return number;
        }
    },
    /**
     * 현재 일자
     * @returns {string} - yyyy-mm-dd
     */
    getTodayToShortString : () => {
        const today = new Date();

        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0'); // 0-based → +1
        const day = String(today.getDate()).padStart(2, '0');

        return`${year}-${month}-${day}`;
    },
    /**
     * 현재 일자
     * @returns {string} - yyyy-mm-dd
     */
    getYearMonthToShortString : () => {
        const today = new Date();

        const year = today.getFullYear();
        const month = String(today.getMonth() + 1).padStart(2, '0'); // 0-based → +1

        return`${year}-${month}`;
    },
    /**
     * 현재 일시
     * @returns {string} - yyyy-mm-dd hh:mi
     */
    getTodayToFullString : () => {
        const now = new Date();

        const year = now.getFullYear();
        const month = String(now.getMonth() + 1).padStart(2, '0');
        const day = String(now.getDate()).padStart(2, '0');
        const hours = String(now.getHours()).padStart(2, '0');
        const minutes = String(now.getMinutes()).padStart(2, '0');

        return `${year}-${month}-${day} ${hours}:${minutes}`;
    },

    /**
     * 금액에 소수점 제거
     * @param value
     * @returns {number}
     * @Desc 10000.123 -> 10000
     * @author ckr
     * @date 2025.05.30
     */
    formatAmount : (value) => {
        if (value === null || value === undefined || value === '') return '';

        const num = Number(value);
        const convertedNumber =  isNaN(num) ? '' : parseInt(num).toString();
        return convertedNumber.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    },

    /**
     * 전화번호 값에 하이푼 추가
     * @param phoneNumber
     * @returns {*}
     */
    getDashAddPhoneNum: (phoneNumber) => {

        try {

            if (!phoneNumber) {
                phoneNumber = "";
            }

            phoneNumber = phoneNumber.trim();
            const digitsOnly = phoneNumber.replace(/\D/g, "");

            if (phoneNumber.includes("*")) {
                  // 마스킹된 전화번호 처리: '*' 문자만 존재하는 경우 하이픈 처리
                  const masked = phoneNumber.replace(/\*/g, "");
                  const len = phoneNumber.length;

                  if (len === 11) {
                    return phoneNumber.replace(/^(...)(....)(....)$/, "$1-$2-$3"); // 예: *** **** ****
                  } else if (len === 10) {
                    return phoneNumber.replace(/^(...)(...)(....)$/, "$1-$2-$3");
                  } else if (len === 8) {
                    return phoneNumber.replace(/^(....)(....)$/, "$1-$2");
                  } else if (len === 4) {
                    return phoneNumber.replace(/^(....)$/, "$1");
                  }

                  return phoneNumber; // 기본 반환

                } else if (common.isNumberic(digitsOnly)) {

                if (digitsOnly.length > 4 && digitsOnly.length <= 8 &&
                    (digitsOnly.indexOf("13") == 0 || digitsOnly.indexOf("14") == 0 || digitsOnly.indexOf("15") == 0
                      || digitsOnly.indexOf("16") == 0 || digitsOnly.indexOf("17") == 0 || digitsOnly.indexOf("18") == 0
                      || digitsOnly.indexOf("19") == 0)) {

                    // 13YY 공공기관 생활정보, 안내, 상담
                    // 14YY (예비; 기간통신사업자 부가서비스)
                    // 15YY 기간통신사업자 공통부가서비스 및 자율부가서비스 (전국대표번호 등)
                    // 16YY 기간통신사업자 공통부가서비스 (전국대표번호 등)
                    // 18YY 기간통신사업자 공통부가서비스 (전국대표번호 등)
                    // 17YY·19YY (예비)
                    return digitsOnly.replace(/\D/g, "").replace(/^(\d{4})(\d{1,4})*$/, "$1-$2");

                } else {
                 			// 기타
                    return digitsOnly.replace(/\D/g, "").replace(/^(02|0[0-1][1-9]|0[0-9]0|0[0-6]\d|1\d{2,3})(\d{3,4})(\d{4})*$/, "$1-$2-$3").replace(/--/, "-").replace(/-$/, "");

                }

            }

            return phoneNumber;

        } catch (e) {
            return phoneNumber;
        }
    },


    /**
     * 시간 combo
     * @param divId, selId
     * @returns
     */
    setHourSelect: (divId, selId, tabIdx) => {

        const select = document.createElement("select");
        select.className = "select2-single form-control wx-175";
        select.id = selId;
        select.name = selId;

        if (typeof tabIdx !== "undefined" && tabIdx !== null) {
            select.tabIndex = tabIdx;
        }

        const blankOption = document.createElement("option");
        blankOption.value = "";
        select.appendChild(blankOption);

        for (let i = 0; i < 24; i++) {
            const option = document.createElement("option");
            const hour = String(i).padStart(2, "0");
            option.value = hour;
            option.textContent = `${hour}:00`;
            select.appendChild(option);
        }

        const container = document.getElementById(divId);

        if (container) {
            container.appendChild(select);
            $('.select2-single').select2(); // CSS 재초기화
        } else {
            console.warn(`${containerId} 요소가 존재하지 않습니다.`);
        }

    },

    /**
     * 숫자만 입력
     * @param e
     * @returns
     */
    onlyNumberKeydownHandler: (e) => {
        const allowedKeys = ["Backspace", "Tab", "ArrowLeft", "ArrowRight", "Delete", "Home", "End"];
        if (!/[0-9]/.test(e.key) && !allowedKeys.includes(e.key)) {
            e.preventDefault();
        }
    },

    /**
     * 숫자만 입력
     * @param
     * @returns
     */
    bindOnlyNumberInputs: () => {
        const inputs = document.querySelectorAll('.onlyNumber');
        inputs.forEach(function(input) {
            input.addEventListener('keydown', common.onlyNumberKeydownHandler);
            input.addEventListener('input', function () {
                this.value = this.value.replace(/\D/g, '');
            });
        });
    },

    /**
     * 비밀번호 체크
     * @param
     * @returns
     */
    isValidChkPwd: (pwd) => {

        const lengthCheck = /^.{8,14}$/;
        const types = [
          /[A-Z]/,         // 대문자
          /[a-z]/,         // 소문자
          /[0-9]/,         // 숫자
          /[!@#$%^&*(),.?":{}|<>]/ // 특수문자
        ];

        // 포함된 문자 종류 개수 확인
        let typeCount = types.reduce((count, regex) => regex.test(pwd) ? count + 1 : count, 0);

        return lengthCheck.test(pwd) && typeCount >= 2;

    },

     /**
     * 서버에서 가져온 ymd, hr 값을 넣어 화면에  'yyyy-MM-dd HH:mm' 포맷팅
     * @param ymd
     * @param hr
     * @returns {string}
     */
    formatDateTime: (ymd, hr) => {
        if (typeof ymd !== 'string' || ymd.length !== 8) return 'YYYY-MM-DD HH:MM';

        const yyyy = ymd.substring(0, 4);
        const mm = ymd.substring(4, 6);
        const dd = ymd.substring(6, 8);

        let hh = '00';
        let min = '00';

        if (typeof hr === 'string' && hr.length > 0) {
            const paddedHr = hr.padStart(4, '0'); // '930' → '0930'
            if (/^\d{3,4}$/.test(hr)) {
                hh = paddedHr.substring(0, 2);
                min = paddedHr.substring(2, 4);
            }
        }

        return `${yyyy}-${mm}-${dd} ${hh}:${min}`;
    },

    /**
     * 빈 문자열 처리
     * @param value
     * @param defaultValue
     * @returns {String}
     * @Desc null, undefined, 빈 문자열 처리 → "-" 반환
     * @author ckask512
     * @date 2025.06.27
     */
    nvl : (value, defaultValue = '-') => {
        return (value === null || value === undefined || value === '') ? defaultValue : value;
    },

}