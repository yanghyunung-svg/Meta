// sidebar.js
(function() {
    // ADMIN 메뉴 보여주기 함수
    function applyRole() {
        const role = localStorage.getItem('role');  // role 가져오기
        const adminMenus = document.querySelectorAll('.ADMIN');  // NodeList

        if (!adminMenus || adminMenus.length === 0) return;  // 없으면 종료

        adminMenus.forEach(el => {
            el.style.display = (role === "1") ? "block" : "none";  // role=1이면 보이기
        });

        console.debug('Role applied:', role, '| ADMIN 메뉴 갯수:', adminMenus.length);
    }

    // 1) DOMContentLoaded 이벤트 처리
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', applyRole);
    } else {
        applyRole();
    }

    // 2) 동적 생성되는 ADMIN 메뉴도 처리(MutationObserver)
    const observer = new MutationObserver((mutations, obs) => {
        if (document.querySelector('.ADMIN')) {
            applyRole();
            obs.disconnect();  // 적용 후 관찰 종료
        }
    });

    observer.observe(document.documentElement, { childList: true, subtree: true });
})();
