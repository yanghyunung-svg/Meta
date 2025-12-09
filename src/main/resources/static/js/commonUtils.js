window.CONFIG = {
    PAGE_SIZE: 20,
    PAGE_RANGE: 5,
    URL: {
        GET_CODE: '/meta/getCodeGroupListData'
    }
};

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
    return {
        ...data,
        updId: userId,
        crtId: data.crtId || userId   // regId 없으면 자동 주입
    };
}
