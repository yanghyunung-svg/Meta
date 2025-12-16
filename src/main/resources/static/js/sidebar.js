// sidebar.js
(function () {
    // ADMIN 메뉴 보여주기 함수
    function applyRole() {
        const role = localStorage.getItem('role');  // role 가져오기
        const adminMenus = document.querySelectorAll('.ADMIN');  // NodeList
        if (!adminMenus || adminMenus.length === 0) return;  // 없으면 종료
        adminMenus.forEach(el => {
            el.style.display = (role === "1") ? "block" : "none";  // role=1이면 보이기
        });
    }

    // DOM 준비 시 적용
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', applyRole);
    } else {
        applyRole();
    }

    // 동적 생성되는 ADMIN 메뉴도 처리
    const observer = new MutationObserver((mutations, obs) => {
        if (document.querySelector('.ADMIN')) {
            applyRole();
            obs.disconnect();  // 적용 후 관찰 종료
        }
    });

    observer.observe(document.documentElement, { childList: true, subtree: true });

     function initSidebarToggle() {
            const sidebar = document.getElementById('sidebar');
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

            // 새로고침 시 상태 복원
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


 function toggleMenu(element) {
        // 1. 클릭된 제목 바로 다음에 있는 <ul> 태그(서브 메뉴)를 찾습니다.
        var subMenu = element.nextElementSibling;

        // 2. 서브 메뉴에 'hide' 클래스를 토글(추가/삭제)합니다.
        if (subMenu) {
            subMenu.classList.toggle('hide');
        }

        // 3. 제목 자체에도 클래스를 추가하여 화살표 방향을 바꿉니다.
        element.classList.toggle('collapsed');
    }
