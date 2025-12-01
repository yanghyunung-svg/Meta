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
        const logoutBtn = document.querySelector('.topbar-right .logout');
        if (headerSpan && logoutBtn) {
            headerSpan.innerText = userNm + ' (' +  userId  + ') ' + role;   // userNm 표시
            // Logout
            logoutBtn.addEventListener('click', () => {
                localStorage.removeItem('userNm');
                localStorage.removeItem('userId');
                localStorage.removeItem('role');
                window.location.href = '/meta/login';
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

function truncateText(text, maxLength = 100) {
    if (text.length > maxLength) {
        return text.slice(0, maxLength) + '...';
    }
    return text;
}
