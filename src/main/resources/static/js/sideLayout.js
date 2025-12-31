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

    function initSidebarToggle() {
        const sidebar = document.getElementById('sidebarContainer');
        const toggleBtn = document.getElementById('sidebarToggleBtn');
        const content = document.querySelector('.container') || document.getElementById('content');

        if (!sidebar || !toggleBtn) return;

        toggleBtn.addEventListener('click', () => {
            const isHidden = sidebar.classList.toggle('hidden');

            if (content) {
                content.classList.toggle('full', isHidden);
            }

            localStorage.setItem('sidebarHidden', isHidden);
        });

        if (localStorage.getItem('sidebarHidden') === 'true') {
            sidebar.classList.add('hidden');
            if (content) content.classList.add('full');
        }
    }

    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', initSidebarToggle);
    } else {
        initSidebarToggle();
    }

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
