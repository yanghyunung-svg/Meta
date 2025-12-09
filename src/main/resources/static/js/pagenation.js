
        /** 페이지 정보 표시 **/
        function updatePageInfo(dataAll, currentPage, page_size) {
            const totalCount = dataAll.length;
            const totalPage = Math.ceil(totalCount / page_size);
            document.getElementById("pageInfo").innerText =
                `전체 ${totalCount}건 | ${currentPage} / ${totalPage} 페이지`;
        }
/** 페이지네이션: 페이지번호 10개 고정 **/
function renderPagination(dataAll, currentPage, page_size) {
    const totalPage = Math.ceil(dataAll.length / page_size);
    if (totalPage === 0) return;

    // ✔ 10개 단위 페이징
    const blockIndex = Math.floor((currentPage - 1) / 10);
    let start = blockIndex * 10 + 1;
    let end = start + 9;
    end = Math.min(end, totalPage);

    const container = document.createElement("div");
    container.className = "pagination";
    container.style.cssText = "margin-top:15px;text-align:center;";

    // 첫/이전
    container.appendChild(makeNavBtn("first", "«", currentPage === 1));
    container.appendChild(makeNavBtn("prev", "‹", currentPage === 1));

    // ✔ 10개 고정 페이징 번호 출력
    for (let i = start; i <= end; i++) {
        const btn = document.createElement("button");
        btn.className = "page-btn";
        btn.dataset.page = i;
        btn.textContent = i;

        if (i === currentPage) {
            btn.style.background = "#1f2937";
            btn.style.color = "white";
        }

        btn.addEventListener("click", () => {
            currentPage = i;
            renderTable(currentPage);
            renderPagination(dataAll, currentPage, page_size);
            updatePageInfo(dataAll, currentPage, page_size);
        });

        container.appendChild(btn);
    }

    // 다음/마지막
    container.appendChild(makeNavBtn("next", "›", currentPage === totalPage));
    container.appendChild(makeNavBtn("last", "»", currentPage === totalPage));

    // 기존 pagination 교체
    const oldNav = document.querySelector(".pagination");
    if (oldNav) oldNav.replaceWith(container);
    else tableBody.parentElement.insertAdjacentElement("afterend", container);


    /** navigation 버튼 생성 **/
    function makeNavBtn(type, label, disabled) {
        const btn = document.createElement("button");
        btn.className = "page-btn-nav";
        btn.dataset.page = type;
        btn.textContent = label;
        btn.style.margin = "0 4px";

        if (disabled) {
            btn.disabled = true;
            btn.style.opacity = "0.4";
        }

        btn.addEventListener("click", () => {
            if (type === "first") currentPage = 1;
            else if (type === "prev") currentPage = Math.max(1, currentPage - 1);
            else if (type === "next") currentPage = Math.min(totalPage, currentPage + 1);
            else if (type === "last") currentPage = totalPage;

            renderTable(currentPage);
            renderPagination(dataAll, currentPage, page_size);
            updatePageInfo(dataAll, currentPage, page_size);
        });

        return btn;
    }
}
