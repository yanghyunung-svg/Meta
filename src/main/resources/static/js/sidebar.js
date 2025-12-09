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

        console.debug('Role applied:', role, '| ADMIN 메뉴 갯수:', adminMenus.length);
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
})();

 document.addEventListener("DOMContentLoaded", () => {
     document.querySelectorAll(".menu-title").forEach(title => {
         title.addEventListener("click", () => {

             const submenu = title.nextElementSibling;  // 바로 아래 sub-menu
             const arrow = title.querySelector(".arrow");

             if (!submenu) return;

             submenu.classList.toggle("collapsed");

             if (submenu.classList.contains("collapsed")) {
                 arrow.textContent = "▲";  // 접힘
             } else {
                 arrow.textContent = "▼";  // 펼침
             }
         });
     });
 });


