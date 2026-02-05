(function () {
    // 1. 초기 실행 (페이지 로드 시)
    document.addEventListener("DOMContentLoaded", () => {
        initSidebar();
    });

    // 2. 동적 로드 대응 (사이드바가 나중에 로드될 경우를 대비해 감시)
    const observer = new MutationObserver(() => {
        if (document.querySelector('.sidebar-title')) {
            initSidebar();
            // observer.disconnect(); // 상황에 따라 해제 여부 결정
        }
    });

    const container = document.getElementById('sidebarContainer');
    if (container) {
        observer.observe(container, { childList: true, subtree: true });
    }

    // 메뉴 실행 로직 (이벤트 위임)
    document.addEventListener("click", function(e) {
        // 클릭된 요소가 .menu-link 버튼인지 확인
        const btn = e.target.closest('.menu-link');
        if (btn) {
            const url = btn.getAttribute('data-url');
            if (url) {
                // 이동 전 현재 카테고리 상태를 'opened'로 강제 저장
                const parentMenu = btn.closest('.sidebar-menu');
                const title = parentMenu ? parentMenu.previousElementSibling : null;
                if (title) {
                    const menuId = title.getAttribute('data-menu-id');
                    if (menuId) localStorage.setItem('menu_' + menuId, 'opened');
                }
                // 페이지 이동 실행
                location.href = url;
            }
        }
    });

    // 전역 토글 함수 (HTML의 onclick="toggleMenu(this)" 대응)
    window.toggleMenu = function(element) {
        const menuList = element.nextElementSibling;
        const menuId = element.getAttribute('data-menu-id');
        if (!menuList) return;

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
        // applyRole(); // 필요시 활성화
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