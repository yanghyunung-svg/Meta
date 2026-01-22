// sidebar.js
(function () {
    function applyRole() {
        const role = localStorage.getItem('role');
        const adminMenus = document.querySelectorAll('.ADMIN');
        if (!adminMenus || adminMenus.length === 0) return;
        adminMenus.forEach(el => {
            el.style.display = (role === "2") ? "none" : "block";
        });
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', applyRole);
    } else {
        applyRole();
    }

    const observer = new MutationObserver((mutations, obs) => {
        if (document.querySelector('.ADMIN')) {
            applyRole();
            obs.disconnect();
        }
    });

    observer.observe(document.documentElement, { childList: true, subtree: true });
})();
function toggleMenu(titleEl) {
    const currentMenu = titleEl.nextElementSibling;
    if (!currentMenu || !currentMenu.classList.contains('sidebar-menu')) return;

    const isOpen = currentMenu.classList.contains('open');

    // 전체 닫기
    document.querySelectorAll('.sidebar-menu').forEach(menu => {
        menu.classList.remove('open');
        menu.style.maxHeight = null;
    });

    document.querySelectorAll('.sidebar-title').forEach(title => {
        title.classList.remove('active');
    });

    // 현재 메뉴 열기
    if (!isOpen) {
        currentMenu.classList.add('open');
        currentMenu.style.maxHeight = currentMenu.scrollHeight + 'px';
        titleEl.classList.add('active');
    }
}
document.getElementById('sidebarContainer').addEventListener('click', (e) => {
    const btn = e.target.closest('.menu-link');
    if (!btn) return;

    const url = btn.dataset.url;
    if (url) location.href = url;
});

(function setActiveMenu() {
    const path = location.pathname;
    document.querySelectorAll('.menu-link').forEach(btn => {
        if (btn.dataset.url === path) {
            btn.classList.add('active');

            // 상위 메뉴 자동 오픈
            const menu = btn.closest('.sidebar-menu');
            if (menu) menu.style.display = 'block';
        }
    });
})();

