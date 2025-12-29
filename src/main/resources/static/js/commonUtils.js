window.CONFIG = {
    PAGE_SIZE: 25,
    PAGE_RANGE: 10
};
window.MENU = '';
window.KOR = /[가-힣]/;
window.pathname = window.location.pathname;

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

/**
 * 필수값 검증
 * @param {Array} rules - 검증 규칙 목록
 * @returns {boolean} true = 통과, false = 실패
 */
function validateRequired(rules) {
    for (const rule of rules) {
        const { value, message, element, type = 'alert' } = rule;
        if (value === undefined || value === null || value === '') {
            if (type === 'alert') {
                msgEvents(message);
                if (element) {
                    element.focus();
                    element.scrollIntoView({ behavior: 'smooth', block: 'center' });
                }
            }
            if (type === 'error') {
                showError(message);
            }
            return false;
        }
    }
    return true;
}


// 폼 전체 값을 JSON 객체로 만들기
function formToJson(form) {
    return Object.fromEntries(new FormData(form).entries());
}

// 서버에서 받은 데이터를 폼에 전체 매핑
function fillForm(form, data) {
    Object.keys(data).forEach(key => {
        let field = form.querySelector(`[name="${key}"]`);
        if (field) field.value = data[key] ?? "";
    });
}

function truncateText(text, maxLength = 100) {
    if (text.length > maxLength) {
        return text.slice(0, maxLength) + '...';
    }
    return text;
}

function formatDate(date) {
    const yyyy = date.getFullYear();
    const mm = String(date.getMonth() + 1).padStart(2, '0');
    const dd = String(date.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
}

function getToday() {
    const t = new Date();
    const yyyy = t.getFullYear();
    const mm = String(t.getMonth() + 1).padStart(2, '0');
    const dd = String(t.getDate()).padStart(2, '0');
    return `${yyyy}-${mm}-${dd}`;
}

// 오늘 / 7일 전 날짜 구하기
function getDefaultDates() {
    const today = new Date();

    // 오늘 - 7일
    const before7 = new Date();
    before7.setDate(today.getDate() - 7);

    return {
        start: formatDate(before7),
        end: formatDate(today)
    };
}

function addUserAuditFields(data) {
    const userId = localStorage.getItem("userId");
    const role = localStorage.getItem('role');
    return {
        ...data,
        updId: userId,
        crtId: data.crtId || userId   // crtId 없으면 자동 주입
    };
}



function openAlert(message, callback) {
    const modal = document.getElementById("commonModal");
    const msg = document.getElementById("modalMessage");
    const okBtn = document.getElementById("modalOkBtn");

    msg.textContent = message;
    modal.classList.remove("hidden");

    const close = () => {
        modal.classList.add("hidden");
        okBtn.removeEventListener("click", handler);
        if (callback) callback();
    };

    const handler = () => close();
    okBtn.addEventListener("click", handler);
}

function openConfirm(message, onYes) {
    const modal = document.getElementById("commonModal");
    const msg = document.getElementById("modalMessage");
    const footer = modal.querySelector(".modal-footer");

    footer.innerHTML = `
        <button id="modalYes" type="button" class="btn">확인</button>
        <button id="modalNo" type="button" class="btn">취소</button>
    `;

    msg.textContent = message;
    modal.classList.remove("hidden");

    document.getElementById("modalYes").onclick = () => {
        modal.classList.add("hidden");
        onYes();
    };
    document.getElementById("modalNo").onclick = () => {
        modal.classList.add("hidden");
    };
}


function getCurrentFileName() {
    const path = window.location.pathname;
    const fileName = path.substring(path.lastIndexOf('/') + 1);
    return fileName || 'login.html';
}



function msgEvents(msgString) {
    const msgInfo = document.getElementById('msgInfo');
    if (msgInfo) {
        msgInfo.innerText = msgString;
    }
 }


/**
 * [공통 함수] updateRowDataFlexible: 테이블 구조에 독립적으로 행 데이터를 업데이트합니다.
 */
window.updateRowDataFlexible = function(data, idKey, updateMap) {
    const targetId = data[idKey];
    if (!targetId) {
        console.error(`업데이트할 행의 ID 값 (${idKey})이 데이터에 없습니다.`, data);
        return;
    }

    const targetRow = document.querySelector(`.row-click[data-id="${targetId}"]`);

    if (targetRow) {
        // 1. 매핑된 필드를 순회하며 업데이트 수행
        for (const dataField in updateMap) {
            if (data.hasOwnProperty(dataField)) {
                const selector = updateMap[dataField];
                const targetCell = targetRow.querySelector(selector);
                if (targetCell) {
                    let newValue = data[dataField];
                    if (dataField === 'role') {
                        newValue =
                            newValue === '1' ? 'ADMIN' :
                            newValue === '2' ? 'USER' : newValue;
                    }
                    if (dataField === 'stat' || dataField === 'sttsCd') {
                        newValue =
                            newValue === '0' ? '신청' :
                            newValue === '1' ? '사용' :
                            newValue === '9' ? '미사용' : newValue;
                    }
                    targetCell.textContent = newValue;
                }
            }
        }

        // 3. 업데이트 시각 효과 (공통)
        targetRow.classList.add('highlight-update');
        setTimeout(() => targetRow.classList.remove('highlight-update'), 3000);
    }
}

function getStatLabel(stat) {
    const map = {
        '0': '신청',
        '1': '사용',
        '9': '미사용'
    };
    return map[stat] ?? stat;
}



function updatePageInfo(dataAll, currentPage, page_size) {
    const totalCount = dataAll.length;
    const totalPage = Math.ceil(totalCount / page_size);
    document.getElementById("pageInfo").innerText = `전체 ${totalCount} 건 | ${currentPage} / ${totalPage} 페이지`;
}

function renderPagination(dataAll, currentPage, page_size) {
    const totalPage = Math.ceil(dataAll.length / page_size);
    if (totalPage === 0) return;

    const blockIndex = Math.floor((currentPage - 1) / 10);
    let start = blockIndex * 10 + 1;
    let end = start + 9;
    end = Math.min(end, totalPage);

    const container = document.createElement("div");
    container.className = "pagination";
    container.style.cssText = "margin-top:20px;text-align:center;";

    container.appendChild(makeNavBtn("first", "«", currentPage === 1));
    container.appendChild(makeNavBtn("prev", "‹", currentPage === 1));

    for (let i = start; i <= end; i++) {
        const btn = document.createElement("button");
        btn.className = "page-btn";
        btn.dataset.page = i;
        btn.textContent = i;

        if (i === currentPage) {
            btn.style.background = "#1f2937";
            btn.style.color = "white";
        }

        btn.addEventListener("click", () => {
            currentPage = i;
            renderTable(currentPage);
            renderPagination(dataAll, currentPage, page_size);
            updatePageInfo(dataAll, currentPage, page_size);
        });

        container.appendChild(btn);
    }

    container.appendChild(makeNavBtn("next", "›", currentPage === totalPage));
    container.appendChild(makeNavBtn("last", "»", currentPage === totalPage));

    const oldNav = document.querySelector(".pagination");
    if (oldNav) oldNav.replaceWith(container);
    else tableBody.parentElement.insertAdjacentElement("afterend", container);

    function makeNavBtn(type, label, disabled) {
        const btn = document.createElement("button");
        btn.className = "page-btn-nav";
        btn.dataset.page = type;
        btn.textContent = label;
        btn.style.margin = "0 4px";

        if (disabled) {
            btn.disabled = true;
            btn.style.opacity = "0.4";
        }

        btn.addEventListener("click", () => {
            if (type === "first") currentPage = 1;
            else if (type === "prev") currentPage = Math.max(1, currentPage - 1);
            else if (type === "next") currentPage = Math.min(totalPage, currentPage + 1);
            else if (type === "last") currentPage = totalPage;

            renderTable(currentPage);
            renderPagination(dataAll, currentPage, page_size);
            updatePageInfo(dataAll, currentPage, page_size);
        });

        return btn;
    }
}
