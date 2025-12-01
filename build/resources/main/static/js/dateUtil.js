/**
 * 날짜 문자열 (yyyy-MM-dd)을 yyyymmdd 형태로 변환
 * @param {string} dateStr - "2025-05-03"
 * @returns {string}       - "20250503"
 */
function formatToYYYYMMDD(dateStr) {
    if (!dateStr) return "";
    return dateStr.replaceAll("-", "");
}

/**
 * Date 객체를 yyyymmdd 형식으로 반환
 * @param {Date} date - 자바스크립트 Date 객체
 * @returns {string}  - "20250503"
 */
function dateToYYYYMMDD(date) {
    if (!(date instanceof Date)) return "";
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, "0");
    const dd = String(date.getDate()).padStart(2, "0");
    return `${yyyy}${mm}${dd}`;
}

/**
 * yyyymmdd 문자열을 yyyy-MM-dd 형식으로 변환
 * @param {string} compact - "20250503"
 * @returns {string}       - "2025-05-03"
 */
function formatToDashDate(compact) {
    if (!compact || compact.length !== 8) return "";
    return `${compact.slice(0, 4)}-${compact.slice(4, 6)}-${compact.slice(6)}`;
}


/**
 * 지정한 패턴으로 서버의 현재 날짜 및 시간을 반환
 * @param {string} pattern - 날짜 패턴[y: Year, M: Month, d: Day, H: Hour, m: Minute, s: Second, S: Millisecond]
 * @returns {string} - 날짜 문자열
 */
function getCurrentDate(pattern) {

    const date = new Date();

    const pad = (num, size = 2) => String(num).padStart(size, '0');

    return pattern
        .replace(/yyyy/g, date.getFullYear())
        .replace(/MM/g, pad(date.getMonth() + 1))
        .replace(/dd/g, pad(date.getDate()))
        .replace(/HH/g, pad(date.getHours()))
        .replace(/mm/g, pad(date.getMinutes()))
        .replace(/ss/g, pad(date.getSeconds()))
        .replace(/SSS/g, pad(date.getMilliseconds(), 3));
};


/**
 * 일자체크
 * @param startDateId - 시작일자 id
 * @param endDateId - 종료일자 id
 * @returns {boolean}
 */
function validatePrdDate(startDateId, endDateId) {

    let bgngYmd = $("#"+startDateId).val();
    let endYmd  = $("#"+endDateId).val();

    if(common.isEmpty(bgngYmd) || common.isEmpty(endYmd)){
        alert('일자를 확인하세요.');
        return false;
    }

    bgngYmd = formatToYYYYMMDD(bgngYmd);
    endYmd  = formatToYYYYMMDD(endYmd);

    if(bgngYmd > endYmd) {
        alert('시작일자는 종료일자보다 클 수 없습니다.');
        document.getElementById(startDateId).focus();
        return false;
    }

    return true;

}


// 한국어 언어셋 정의 (datepicker가 로드된 경우에만)
if (typeof $.fn.datepicker !== 'undefined' && $.fn.datepicker.language) {
    $.fn.datepicker.language['ko'] = {
        days: ['일','월','화','수','목','금','토'],
        daysShort: ['일','월','화','수','목','금','토'],
        daysMin: ['일','월','화','수','목','금','토'],
        months: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        monthsShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
        today: '오늘',
        clear: '초기화',
        dateFormat: 'yyyy-mm-dd',
        timeFormat: 'hh:ii',
        firstDay: 0 // 일요일 시작
    };
}

/**
 * 공통 달력 초기화 함수
 * @param {string} selector - jQuery selector (예: '.datepicker-here')
 * @param {object} options - 추가 옵션
 */
function initDatepicker(selector, options) {
    // datepicker 함수가 정의되어 있는지 확인
    if (typeof $.fn.datepicker === 'undefined') {
        console.warn('Datepicker 라이브러리가 로드되지 않았습니다. selector: ' + selector);
        return;
    }

    var defaultOptions = {
        language: 'ko',
        autoClose: true,
        dateFormat: 'yyyy-mm-dd',
        showOtherMonths: true,
        selectOtherMonths: true
    };

    $(selector).each(function() {
        if ($(this).data('datepicker')) {
            $(this).datepicker('destroy');
        }
        // destroy 후 options 병합 적용
        $(this).datepicker($.extend({}, defaultOptions, options));
    });

}
