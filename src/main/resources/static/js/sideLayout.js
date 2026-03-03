(function () {
    // 공통 참조 변수
    let toggleBtn;
    let sidebar;

    document.addEventListener("DOMContentLoaded", () => {
        toggleBtn = document.getElementById('sidebarToggle');
        sidebar = document.getElementById('sidebarContainer');

        if (toggleBtn && sidebar) {
            // 기존 이벤트 리스너 제거 후 새로 등록 (중복 방지)
            toggleBtn.onclick = (e) => {
                sidebar.classList.toggle('active');
                toggleBtn.classList.toggle('open');
                e.stopPropagation();
            };

            // 외부 클릭 시 닫기
            document.addEventListener('click', (e) => {
                if (sidebar.classList.contains('active')) {
                    if (!sidebar.contains(e.target) && !toggleBtn.contains(e.target)) {
                        sidebar.classList.remove('active');
                        toggleBtn.classList.remove('open');
                    }
                }
            });
        }
        initSidebar();
    });

    // 2. 동적 로드 대응 (사이드바가 나중에 로드될 경우를 대비)
    const observer = new MutationObserver(() => {
        const sidebarTitle = document.querySelector('.sidebar-title');
        if (sidebarTitle) {
            initSidebar();
            // 필요 시: toggleBtn = document.getElementById('sidebarToggle'); 재할당
        }
    });

    const container = document.getElementById('sidebarContainer');
    if (container) {
        observer.observe(container, { childList: true, subtree: true });
    }

    document.addEventListener("click", function(e) {
        const btn = e.target.closest('.menu-link');
        if (btn) {
            const url = btn.getAttribute('data-url');
            if (url) {
                const parentMenu = btn.closest('.sidebar-menu');
                const title = parentMenu ? parentMenu.previousElementSibling : null;
                if (title) {
                    const menuId = title.getAttribute('data-menu-id');
                    if (menuId) localStorage.setItem('menu_' + menuId, 'opened');
                }
                location.href = url;
            }
        }
    });

    window.toggleMenu = function(element) {
        const menuList = element.nextElementSibling;
        const menuId = element.getAttribute('data-menu-id');
        if (!menuList) return;

        document.querySelectorAll('.sidebar-title').forEach(otherTitle => {
            if (otherTitle !== element) {
                const otherList = otherTitle.nextElementSibling;
                const otherId = otherTitle.getAttribute('data-menu-id');
                if (otherList) otherList.style.display = "none";
                otherTitle.classList.remove("open");
                if (otherId) localStorage.setItem('menu_' + otherId, 'closed');
            }
        });

        const isVisible = window.getComputedStyle(menuList).display === "block";
        if (isVisible) {
            menuList.style.display = "none";
            element.classList.remove("open");
            if (menuId) localStorage.setItem('menu_' + menuId, 'closed');
        } else {
            menuList.style.display = "block";
            element.classList.add("open");
            if (menuId) localStorage.setItem('menu_' + menuId, 'opened');
        }
    };

    function initSidebar() {
        restoreMenuState();
        highlightActiveMenu();
    }

    function restoreMenuState() {
        document.querySelectorAll('.sidebar-title').forEach(title => {
            const menuId = title.getAttribute('data-menu-id');
            const state = localStorage.getItem('menu_' + menuId);
            const menuList = title.nextElementSibling;
            if (menuList && state === 'opened') {
                menuList.style.display = "block";
                title.classList.add("open");
            }
        });
    }

    function highlightActiveMenu() {
        const currentPath = window.location.pathname;
        document.querySelectorAll('.menu-link').forEach(link => {
            const menuUrl = link.getAttribute('data-url');
            if (menuUrl && currentPath.includes(menuUrl)) {
                link.classList.add('active');
                const parentMenu = link.closest('.sidebar-menu');
                if (parentMenu) {
                    parentMenu.style.display = "block";
                    const title = parentMenu.previousElementSibling;
                    if (title) title.classList.add("open");
                }
            }
        });
    }


})();