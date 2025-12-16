

document.addEventListener("DOMContentLoaded", () => {
    loadHeader();
    loadSidebar();

});
/* Header 불러오기 */
function loadHeader() {
    fetch("/common/header.html")
        .then(res => res.text())
        .then(html => {
            document.getElementById("topbarContainer").innerHTML = html;
        })
        .catch(err => console.error("Header load error:", err)
     );

    const userNm = localStorage.getItem('userNm');
    const userId = localStorage.getItem('userId');
    const role = localStorage.getItem('role');

    const interval = setInterval(() => {
        const dashboard = document.querySelector('.topbar-left .dashboard');
        const headerSpan = document.getElementById('headerUserNm');
        const userInfo = document.querySelector('.topbar-right .userInfo');
        const logoutBtn = document.querySelector('.topbar-right .logout');

        if (dashboard) {
            dashboard.addEventListener('click', () => {
                window.location.href = '/meta/dashboard';
            });
        }


        if (headerSpan && logoutBtn) {
            headerSpan.innerText = userNm + ' (' +  userId  + ') ' ;
            // Logout
            logoutBtn.addEventListener('click', () => {
                localStorage.removeItem('userNm');
                localStorage.removeItem('userId');
                localStorage.removeItem('role');
                localStorage.removeItem('menuId');
                window.location.href = '/meta/login';
            });
        }
        if (headerSpan && userInfo) {
            userInfo.addEventListener('click', () => {
                const payload = { mode: "S", userId: userId };
                openWindowWithJSON(payload, "/meta/userChg", 700, 500);
            });
        }
    }, 100);
}


/* Sidebar 불러오기  */
function loadSidebar(menuId = null) {
    const sidebarContainer = document.getElementById("sidebarContainer");

    if (sidebarContainer.innerHTML === "") {
        fetch("/common/sidebar.html")
            .then(res => res.text())
            .then(html => {
                sidebarContainer.innerHTML = html;
            })
            .catch(err => console.error("Sidebar load error:", err));
    }
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