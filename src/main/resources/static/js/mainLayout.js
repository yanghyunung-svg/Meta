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
    const dashboard = document.querySelector('.topbar-left .dashboard');
    const logoutBtn = document.querySelector('.topbar-right .logout');
    const userNm = localStorage.getItem('userNm');
    const userId = localStorage.getItem('userId');
    const userInfo = document.querySelector('.topbar-right .userInfo');
    const headerUserNm = document.getElementById('headerUserNm');

    if (headerUserNm && userNm && userId) {
        headerUserNm.innerText = `${userNm} (${userId})`;
    }

    if (dashboard) {
        dashboard.addEventListener('click', () => {
            location.href = '/meta/termEdit';
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

function openWindowWithJSON(payload, url, width, height) {
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
        popupWin.postMessage(payload, window.location.origin);
        return;
    }

    popupWin = window.open(url, 'MetaSystemPopup', features);

    if (!popupWin) {
        alert('팝업이 차단되었습니다. 팝업 차단을 해제해주세요.');
        return;
    }

    // load 이벤트 보장
    const sendPayload = () => {
        try {
            popupWin.postMessage(payload, window.location.origin);
        } catch (e) {
            console.error('postMessage failed:', e);
        }
    };

    popupWin.addEventListener('load', sendPayload, { once: true });

    // 일부 브라우저 대응 (load 미발생 대비)
    setTimeout(sendPayload, 500);
}

function receiveCode(grpCd, grpNm) {
    document.getElementById("grpCd").value = grpCd;
    document.getElementById("grpNm").value = grpNm;
}

function receiveUser(id, nm) {
    document.getElementById("userId").value = id;
    document.getElementById("userNm").value = nm;
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
    container.style.cssText = "margin-top:15px;text-align:center;";

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

