window.CONFIG = {
    PAGE_SIZE: 20,
    PAGE_RANGE: 10,
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