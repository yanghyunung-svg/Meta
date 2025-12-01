document.addEventListener("DOMContentLoaded", function () {

    /** =============================
     *  권한 설정 (Server에서 전달 가능)
     * ============================= */
    const userRole = sessionStorage.getItem("userRole") || "USER";
    // 서버에서 JSP로 전달 시: const userRole = "${sessionScope.userRole}";

    /** 메뉴 권한 체크 */
    document.querySelectorAll(".sidebar-group").forEach(group => {
        const allowedRoles = group.getAttribute("data-role").split(",");

        if (!allowedRoles.includes(userRole)) {
            group.style.display = "none";   // 권한 없는 메뉴 숨김
        }
    });

    /** =============================
     *  아코디언 처리
     * ============================= */
    document.querySelectorAll(".accordion").forEach(title => {
        title.addEventListener("click", () => {
            const submenu = title.nextElementSibling;
            const isOpen = submenu.style.display === "block";

            // 모든 submenu 닫기 (원하면 적용)
            document.querySelectorAll(".submenu").forEach(s => s.style.display = "none");

            // 클릭된 메뉴만 toggle
            submenu.style.display = isOpen ? "none" : "block";
        });
    });

});
