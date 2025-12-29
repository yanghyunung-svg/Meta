document.addEventListener('DOMContentLoaded', () => {
    loadHeader();
    loadSidebar();
//    loadFooter();
});

function loadHeader() {
    const container = document.getElementById("topbarContainer");
    if (!container) return;
    if (container.dataset.loaded === "true") return;

    if (container.innerHTML === "") {
        fetch("/common/header.html")
            .then(res => res.text())
            .then(html => {
                container.innerHTML = html;
                container.dataset.loaded = "true";
                bindHeaderEvents();
            })
            .catch(err => console.error("Header load error:", err));
    }
}

function bindHeaderEvents() {
    const termSearch = document.querySelector('.topbar-left .termSearch');
    const logoutBtn = document.querySelector('.topbar-right .logout');

    const userNm = localStorage.getItem('userNm');
    const userId = localStorage.getItem('userId');
    const role = localStorage.getItem('role');

    const userInfo = document.querySelector('.topbar-right .userInfo');
    const headerUserNm = document.getElementById('headerUserNm');

    if (headerUserNm && userNm && userId) {
        headerUserNm.innerText = `${userNm} (${userId})`;
    }

    if (termSearch) {
        termSearch.addEventListener('click', () => {
            location.href = '/meta/termSearch';
        });
    }

    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            localStorage.clear();
            location.href = '/meta/login';
        });
    }

    if (userInfo && userId) {
        userInfo.addEventListener('click', () => {
            openWindowWithJSON( { mode: "S", userId },  "/meta/userChg", 800, 700 );
        });
    }

}


/* Sidebar 불러오기  */
function loadSidebar(menuId = null) {
    const container = document.getElementById("sidebarContainer");
    if (!container) return;
    if (container.dataset.loaded === "true") return;

    if (container.innerHTML === "") {
        fetch("/common/sidebar.html")
            .then(res => res.text())
            .then(html => {
                container.innerHTML = html;
                container.dataset.loaded = "true";
            })
            .catch(err => console.error("Sidebar load error:", err));
    }
}

function loadFooter() {
    const container = document.getElementById("footbarContainer");
    if (!container) return;
    if (container.dataset.loaded === "true") return;

    if (container.innerHTML === "") {
        fetch("/common/footer.html")
            .then(res => res.text())
            .then(html => {
                container.innerHTML = html;
                container.dataset.loaded = "true";
            })
            .catch(err => console.error("footer load error:", err));
    }
}

function initScreen() {
    tableBody.innerHTML = "";
    document.getElementById('pageInfo').innerText = "";
    dataAll = [];
    const old = document.querySelector(".pagination");
    if (old) old.remove();
}
let popupWin = null;

function openWindowWithJSON(sendData, url, width, height) {
    const left = window.screenX + (window.outerWidth - width) / 2;
    const top = window.screenY + (window.outerHeight - height) / 2;
    const features = [
        `width=${width}`,
        `height=${height}`,
        `left=${left}`,
        `top=${top}`,
        'scrollbars=no',
        'resizable=yes'
    ].join(',');

    // 팝업이 이미 열려 있으면 재사용
    if (popupWin && !popupWin.closed) {
        popupWin.focus();
        popupWin.postMessage(sendData, window.location.origin);
        return;
    }

    popupWin = window.open(url, 'MetaSystemPopup', features);

    if (!popupWin) {
        alert('팝업이 차단되었습니다. 팝업 차단을 해제해주세요.');
        return;
    }

    // load 이벤트 보장
    const sendsendData = () => {
        try {
            popupWin.postMessage(sendData, window.location.origin);
        } catch (e) {
            console.error('postMessage failed:', e);
        }
    };

    popupWin.addEventListener('load', sendsendData, { once: true });

    // 일부 브라우저 대응 (load 미발생 대비)
    setTimeout(sendsendData, 500);
}

function receiveCode(grpCd, grpNm) {
    document.getElementById("grpCd").value = grpCd;
    document.getElementById("grpNm").value = grpNm;
}

function receiveUser(id, nm) {
    document.getElementById("userId").value = id;
    document.getElementById("userNm").value = nm;
}


