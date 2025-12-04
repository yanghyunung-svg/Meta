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
        const headerSpan = document.getElementById('headerUserNm');
        const userInfo = document.querySelector('.topbar-right .userInfo');
        const logoutBtn = document.querySelector('.topbar-right .logout');

        if (headerSpan && logoutBtn) {
            headerSpan.innerText = userNm + ' (' +  userId  + ') ' ;   // userNm 표시
            // Logout
            logoutBtn.addEventListener('click', () => {
                localStorage.removeItem('userNm');
                localStorage.removeItem('userId');
                localStorage.removeItem('role');
                window.location.href = '/meta/login';
            });
            clearInterval(interval); // 더 이상 반복하지 않음
        }
        if (headerSpan && userInfo) {
            userInfo.addEventListener('click', () => {
                const width = 700;
                const height = 450;
                const left = window.screenX + (window.outerWidth - width) / 2;
                const top = window.screenY + (window.outerHeight - height) / 2;
                const features = `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`;
                const win = window.open("/meta/userReg", "사용자정보", features);
                const payload = { mode: "S", userId: userId };
                win.addEventListener('load', () => {
                    win.postMessage(payload, window.location.origin);
                });
            });
            clearInterval(interval); // 더 이상 반복하지 않음
        }
    }, 100);  // 0.1초 간격으로 topbar 로드 완료 확인
}

/* Sidebar 불러오기 */
function loadSidebar() {
    fetch("/common/sidebar.html")
        .then(res => res.text())
        .then(html => {
            document.getElementById("sidebarContainer").innerHTML = html;
        })
        .catch(err => console.error("Sidebar load error:", err));
}

