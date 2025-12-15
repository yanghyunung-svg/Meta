const MENU_DATA = {
    'menu-dash': {
        title: ' ',
        act: '/meta/dashboard',
        submenus: [
            { id: 'sub-menu', name: '신청내역 조회' , url: '/meta/dashboard' }
        ]
    },
    'menu-term': {
        title: ' ',
        act: '/meta/termSearch',
        submenus: [
            { id: 'sub-menu', name: '표준용어 조회'     , url: '/meta/termSearch' },
            { id: 'sub-menu', name: '표준용어 단어검색' , url: '/meta/termEdit' },
            { id: 'sub-menu', name: '표준용어 신청'     , url: '/meta/termReg' },
            { id: 'sub-menu', name: '표준용어 일괄등록' , url: '/meta/termRegEblc' }
        ]
    },
    'menu-word': {
        title: ' ',
        act: '/meta/wordSearch',
        submenus: [
            { id: 'sub-menu', name: '표준단어 조회'   , url: '/meta/wordSearch' },
            { id: 'sub-menu', name: '표준단어 신청'   , url: '/meta/wordReg' },
        ]
    },
    'menu-dmn': {
        title: ' ',
        act: '/meta/dmnSearch',
        submenus: [
            { id: 'sub-menu', name: '도메인 조회'        , url: '/meta/dmnSearch' },
            { id: 'sub-menu', name: '도메인 신청'        , url: '/meta/dmnReg' }
        ]
    },
    'menu-code': {
        title: ' ',
        act: '/meta/codeGroupSearch',
        submenus: [
            { id: 'sub-menu', name: '공통코드 조회'   , url: '/meta/codeGroupSearch' },
            { id: 'sub-menu', name: '상세코드 조회'   , url: '/meta/codeDetlSearch' },
            { id: 'sub-menu', name: '공통코드 등록'   , url: '/meta/codeGroupReg' },
            { id: 'sub-menu', name: '상세코드 등록'   , url: '/meta/codeDetlReg' },
            { id: 'sub-menu', name: '상세코드 일괄등록' , url: '/meta/codeDetlRegEblc' }
        ]
    },
    'menu-user': {
        title: ' ',
        act: '/meta/userSearch',
        submenus: [
            { id: 'sub-menu', name: '사용자정보 조회', url: '/meta/userSearch' },
            { id: 'sub-menu', name: '사용자정보 등록', url: '/meta/userReg' },
            { id: 'sub-menu', name: '로그인로그 조회', url: '/meta/loginLogSearch' }
        ]
    } ,
    'menu-aprv': {
        title: ' ',
        act: '/meta/aprvDsctnInq',
        submenus: [
            { id: 'sub-menu', name: '승인내역 조회', url: '/meta/aprvDsctnInq' }
        ]
    }
};


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
            setupHeaderMenuEvents();
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
            headerSpan.innerText = userNm + ' (' +  userId  + ') ' ;
            // Logout
            logoutBtn.addEventListener('click', () => {
                localStorage.removeItem('userNm');
                localStorage.removeItem('userId');
                localStorage.removeItem('role');
                localStorage.removeItem('menuId');
                window.location.href = '/meta/login';
            });
            clearInterval(interval);
        }
        if (headerSpan && userInfo) {
            userInfo.addEventListener('click', () => {
                const payload = { mode: "S", userId: userId };
                openWindowWithJSON(payload, "/meta/userChg", 700, 500);
            });
            clearInterval(interval);
        }
    }, 100);
}

// =======================================================
// 3. 헤더 메뉴 이벤트 설정 함수 (새로 추가)
// =======================================================
function setupHeaderMenuEvents() {
    document.querySelectorAll('[data-menu-id]').forEach(menuItem => {
        menuItem.addEventListener('click', function() {
            const menuId = this.getAttribute('data-menu-id');
            if (menuId && MENU_DATA[menuId]) {
                loadSidebar(menuId);
                document.querySelectorAll('[data-menu-id]').forEach(item => item.classList.remove('active'));
                this.classList.add('active');
            }

            const menuInfo = MENU_DATA[menuId];
             window.location.replace(menuInfo.act);
        });


    });
}


/* Sidebar 불러오기  */
function loadSidebar(menuId = null) {
    const sidebarContainer = document.getElementById("sidebarContainer");

    if (sidebarContainer.innerHTML === "") {
        fetch("/common/sidebar.html")
            .then(res => res.text())
            .then(html => {
                sidebarContainer.innerHTML = html;
                menuId = localStorage.getItem('menuId');
                if (menuId) {
                    renderSubmenus(menuId);
                }
            })
            .catch(err => console.error("Sidebar load error:", err));
    } else if (menuId) {
        localStorage.setItem('menuId', menuId);
        renderSubmenus(menuId);
    }
}

function renderSubmenus(menuId) {
    const sidebarMenuArea = document.getElementById('sidebar-menu-area');
    const menuInfo = MENU_DATA[menuId];

    if (!sidebarMenuArea || !menuInfo) {
        console.error("Sidebar menu area not found or menu data missing.");
        return;
    }

    let menuHtml = `<class="menu-title"> ${menuInfo.title} <ul class="sub-menu">`;

    menuInfo.submenus.forEach(sub => {
        menuHtml += `<li><a href="${sub.url}">${sub.name}</a></li>`;
    });

    menuHtml += `</ul>`;
    sidebarMenuArea.innerHTML = menuHtml;
}

function openWindowWithJSON(payload, url, width, height) {
    const left = window.screenX + (window.outerWidth - width) / 2;
    const top = window.screenY + (window.outerHeight - height) / 2;
    const features = `width=${width},height=${height},left=${left},top=${top},scrollbars=yes,resizable=yes`;
    const win = window.open(url, "Meta System", features);
    win.addEventListener('load', () => {
        win.postMessage(payload, window.location.origin);
    });
}
