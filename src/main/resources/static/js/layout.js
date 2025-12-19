document.addEventListener('DOMContentLoaded', () => {
    loadHeader();
    loadSidebar();
    loadFooter();
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

    if (dashboard) {
        dashboard.addEventListener('click', () => {
            location.href = '/meta/dashboard';
        });
    }

    if (logoutBtn) {
        logoutBtn.addEventListener('click', () => {
            localStorage.clear();
            location.href = '/meta/login';
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
                bindFooterEvents();
            })
            .catch(err => console.error("footer load error:", err));
    }
}


function bindFooterEvents() {
    const userNm = localStorage.getItem('userNm');
    const userId = localStorage.getItem('userId');
    const userInfo = document.querySelector('.footbar-left .userInfo');
    const footerSpan = document.getElementById('footerUserNm');

    if (footerSpan && userNm && userId) {
        footerSpan.innerText = `${userNm} (${userId})`;
    }

    if (userInfo && userId) {
        userInfo.addEventListener('click', () => {
            openWindowWithJSON( { mode: "S", userId },  "/meta/userChg", 700, 500 );
        });
    }
}


function initScreen() {
    tableBody.innerHTML = "";
    document.getElementById('pageInfo').innerText = "";
    dataAll = [];
    const old = document.querySelector(".pagination");
    if (old) old.remove();
}

function openWindowWithJSON(payload, url, width, height) {
    const left = window.screenX + (window.outerWidth - width) / 2;
    const top = window.screenY + (window.outerHeight - height) / 2;
    const features = `width=${width},height=${height},left=${left},top=${top},scrollbars=no,resizable=yes`;
    const win = window.open(url, "Meta System", features);
    win.addEventListener('load', () => {
        win.postMessage(payload, window.location.origin);
    });
}

function receiveCode(grpCd, grpNm) {
    document.getElementById("grpCd").value = grpCd;
    document.getElementById("grpNm").value = grpNm;
}

function receiveUser(id, nm) {
    document.getElementById("userId").value = id;
    document.getElementById("userNm").value = nm;
}
