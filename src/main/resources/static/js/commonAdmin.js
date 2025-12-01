
if (window.currentMenuSn === undefined) {
    window.currentMenuSn = null; // 최초 한 번만 초기화
}

// 안전한 focus 함수 (null 체크 포함)
window.safeFocus = function(elementId) {
    const element = document.getElementById(elementId);
    if (element && typeof element.focus === 'function') {
        element.focus();
    }
};

// 부모창 기준 currentMenuSn 가져오기
function getCurrentMenuSn() {
    return getRootWindow().currentMenuSn;
}

// 최상위 window 반환 함수
function getRootWindow() {
    let current = window;
    try {
        while (current.opener && current.opener !== current) {
            current = current.opener;
        }
    } catch (e) {
        current = window;
    }
    current.popupArr = current.popupArr || [];
    return current;
}

// 전역에서 한 번만 호출
const rootWindow = getRootWindow();

// 팝업 관리 객체
const PopupManager = {
    popupWindows: new Map(),

    addPopup(popup, name = '') {
        rootWindow.popupArr.push(popup);
        if (name) this.popupWindows.set(name, popup);
    },

    removePopup(name) {
        this.popupWindows.delete(name);
    },

    closeAll() {
        for (let popup of rootWindow.popupArr) {
            if (popup && !popup.closed) popup.close();
        }
        rootWindow.popupArr = [];
    }
};


// 시간 포맷 함수
function formatTime(seconds) {
    const mins = Math.floor(seconds / 60);
    const secs = seconds % 60;
    return `${String(mins).padStart(2, '0')}:${String(secs).padStart(2, '0')}`;
}


// 기본 팝업 열기
function openPopup(url, windowName = 'popupWindow', width = 600, height = 400, scrollable = true) {
    const left = (window.screen.width - width) / 2;
    const top = (window.screen.height - height) / 2;

    const options = `width=${width},height=${height},top=${top},left=${left},` +
        `resizable=yes,scrollbars=${scrollable ? 'yes' : 'no'},status=no`;

    const popup = window.open(url, windowName, options);
    if (!popup) {
        alert('팝업이 차단되었습니다. 브라우저 설정을 확인하세요.');
        return;
    }

    PopupManager.addPopup(popup);
    popup.focus();
}


// 데이터 포함 팝업 열기
function openPopupMultiWithData(url, windowName = '', width = 600, height = 400, scrollable = true, data = {}, position = {}) {
    const defaultLeft = window.outerWidth / 2 + window.screenX - width / 2;
    const defaultTop = window.outerHeight / 2 + window.screenY - height / 2;
    const left = position.left ?? defaultLeft;
    const top = position.top ?? defaultTop;
    const options = `width=${width},height=${height},top=${top},left=${left},resizable=yes,scrollbars=${scrollable ? 'yes' : 'no'},status=no`;

    data.popupAccess = true;
    const jsonData = JSON.stringify(data);
    const existingPopup = PopupManager.popupWindows.get(windowName);

    if (existingPopup && !existingPopup.closed) {
        existingPopup.name = jsonData;
        existingPopup.location.href = url;
        existingPopup.focus();
    } else {
        const popup = window.open(url, windowName, options);
        if (!popup) {
            alert('팝업이 차단되었습니다. 브라우저 설정을 확인하세요.');
            return;
        }

        popup.name = jsonData;
        popup.focus();
        PopupManager.addPopup(popup, windowName);

        const interval = setInterval(() => {
            if (popup.closed) {
                PopupManager.removePopup(windowName);
                clearInterval(interval);
            }
        }, 1000);
    }
}


// 팝업 닫기
function closeAllWindowPopups() {
    PopupManager.closeAll();
}


// 쿠키 설정/조회
function setCookie(name, value, days) {
    const expires = new Date();
    expires.setDate(expires.getDate() + days);
    document.cookie = `${name}=${value}; path=/; expires=${expires.toUTCString()}`;
}

function getCookie(name) {
    const cookies = document.cookie.split(';');
    for (let cookie of cookies) {
        const [key, val] = cookie.trim().split('=');
        if (key === name) return val;
    }
    return null;
}


// 팝업 숨김 처리
function handlePopupHide(checkbox) {
    const popupId = checkbox.dataset.popupId;
    const hideDays = parseInt(checkbox.dataset.hideDays);
    setCookie(`popup_hidden_${popupId}`, 'Y', hideDays);
    checkbox.closest('.popup-box')?.remove();
}


// 미리보기 팝업 (window.open 방식)
function openPopupPreview(url, windowName, scroll, data) {
    const popupUnqId = data.popupUnqId;
    const cookieName = `popup_hidden_${popupUnqId}`;
    if (getCookie(cookieName) === 'Y') {
        console.log(`팝업 ${popupUnqId}는 쿠키 설정으로 인해 표시되지 않습니다.`);
        return;
    }

    const width = parseInt(data.width) || 600;
    const height = parseInt(data.height) || 400;
    let left = parseInt(data.left);
    let top = parseInt(data.top);

    if (isNaN(left) || isNaN(top)) {
        left = Math.floor((window.screen.width - width) / 2);
        top = Math.floor((window.screen.height - height) / 2);
    }

    data.popupAccess = true;
    const jsonData = JSON.stringify(data);
    const uniqueWindowName = windowName + '_' + Date.now();
    const options = `width=${width},height=${height},left=${left},top=${top},scrollbars=${scroll ? 'yes' : 'no'}`;

    const popup = window.open(url, uniqueWindowName, options);
    if (!popup) {
        console.warn('팝업이 차단되었거나 열리지 않았습니다:', uniqueWindowName);
        return;
    }

    popup.name = jsonData;
    popup.focus();
    PopupManager.addPopup(popup, uniqueWindowName);

    const interval = setInterval(() => {
        if (popup.closed) {
            PopupManager.removePopup(uniqueWindowName);
            clearInterval(interval);
        }
    }, 1000);
}

// 공통 Ajax 호출 함수
function sendAjax(url, setData, msg, successCallback) {

    // FormData 여부 체크
    const isFormData = (setData instanceof FormData);

    $.ajax({
        url: url,
        type: 'POST',
        contentType: isFormData ? false : 'application/json',
        processData: isFormData ? false : true,
        data: isFormData ? setData : JSON.stringify(setData),
        timeout: 60000,
        success: function(response) {

            if (response.code === "SUCCESS") {

                if (msg) {
                    alert(msg);
                }

                if (successCallback) successCallback(response);

            } else {

                console.log("처리 실패 하였습니다.");

                const errCode = response.code ?? "UNKNOWN";
                const errMsg = response.message ?? "처리 중 오류가 발생했습니다.";

                alert(errMsg);
                return;

            }

        },
        error: function(xhr, status, error) {

            console.error('DataTable AJAX Error:', xhr.status, error);

            if (xhr.status === 0) {
                alert('외부 서버에 연결할 수 없습니다. 서버 상태를 확인해주세요.');
                window.location.href = '/error/view?error=connection&message=' + encodeURIComponent('외부 서버 연결 실패');
            } else if (xhr.status === 401) {
                window.location.href = '/error/401';
            } else if (xhr.status === 403) {
                window.location.href = '/error/403';
            } else if (xhr.status === 404) {
                window.location.href = '/error/404';
            } else if (xhr.status === 500) {
                window.location.href = '/error/500';
            } else {
                alert('데이터 조회 중 오류가 발생했습니다.');
            }

        }
    });
}

/**
 * select2 공통 초기화 함수
 * @param {string} selector - 초기화할 셀렉터 (예: '.select2-single')
 * @param {object} options - select2 옵션 객체 (선택)
 */
function cmbShowDropdown(selector, options) {

    if (!selector) selector = '.select2-single';
    if (!options) options = {};

    // select2
    if ($(selector).length > 0) {

        $(selector).each(function() {

            var $this = $(this);
            $this.select2(options);

            // sorting 클래스가 있으면 검색 드롭다운에 show 클래스 추가
            if ($this.hasClass('sorting')) {
                $this.on('select2:open', function() {
                    setTimeout(function() {
                        $('.select2-search--dropdown').addClass('show');
                    }, 10);
                });
            }
        });

    }

}

//최상위의 정보를 get
function getRootOpener(win) {
    let parent = win.opener;
    while (parent && parent.opener) {
        parent = parent.opener; // 계속 거슬러 올라감
    }
    return parent;
}

/**
 * url 기준으로 menuSn 찾기 (조건: prntSn != null/'' 인 메뉴만 대상)
 * @param {Array} menuList - 전체 메뉴 리스트
 * @param {String} targetUrl - 찾고 싶은 url
 * @returns {Number|null} menuSn 값 (없으면 null)
 */
function findMenuSnByUrl(menuList, targetUrl) {

    let result = null;

    function recursiveSearch(list) {
        for (const menu of list) {
            // 조건: url 일치 && prntSn 존재
            if (menu.url === targetUrl && menu.prntSn !== null && menu.prntSn !== '') {
                result = menu.menuSn;
                return true; // 찾았으니 종료
            }
            // children 있으면 재귀 탐색
            if (menu.children && menu.children.length > 0) {
                if (recursiveSearch(menu.children)) return true;
            }
        }
        return false;
    }

    recursiveSearch(menuList);
    return result;
}