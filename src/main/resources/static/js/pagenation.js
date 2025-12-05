

        /** 페이지 정보 표시 **/
        function updatePageInfo(dataAll, currentPage, page_size) {
            const totalCount = dataAll.length;
            const totalPage = Math.ceil(totalCount / page_size);
            document.getElementById("pageInfo").innerText =
                `전체 ${totalCount}건 | ${currentPage} / ${totalPage} 페이지`;
        }

        /** 페이지네이션 **/
        function renderPagination(dataAll, currentPage, page_size) {
            const totalPage = Math.ceil(dataAll.length / page_size);
            if (totalPage === 0) return;
            let html = `<div class="pagination" style="margin-top:15px;text-align:center;">`;
            html += `<button class="page-btn-nav" data-page="first" style="margin:0 4px;">«</button>`;
            html += `<button class="page-btn-nav" data-page="prev" style="margin:0 4px;">‹</button>`;
            const range = window.CONFIG.PAGE_RANGE;
            let start = Math.max(1, currentPage - range);
            let end = Math.min(totalPage, currentPage + range);

            for (let i = start; i <= end; i++) {
                html += `
                    <button class="page-btn"
                        data-page="${i}"
                        style="${i === currentPage ? 'background:#1f2937; color:white;' : ''}">
                        ${i}
                    </button>
                `;
            }
            html += `<button class="page-btn-nav" data-page="next" style="margin:0 4px;">›</button>`;
            html += `<button class="page-btn-nav" data-page="last" style="margin:0 4px;">»</button>`;
            html += `</div>`;
            const oldNav = document.querySelector(".pagination");
            if (oldNav) oldNav.remove();
            tableBody.parentElement.insertAdjacentHTML("afterend", html);

            document.querySelectorAll(".page-btn").forEach(btn => {
                btn.addEventListener("click", () => {
                    currentPage = Number(btn.dataset.page);
                    renderTable(currentPage);
                    renderPagination(dataAll, currentPage, PAGE_SIZE);
                    updatePageInfo(dataAll, currentPage, PAGE_SIZE);
                });
            });

            document.querySelectorAll(".page-btn-nav").forEach(btn => {
                btn.addEventListener("click", () => {
                    const type = btn.dataset.page;
                    if (type === "first") currentPage = 1;
                    else if (type === "prev") currentPage = Math.max(1, currentPage - 1);
                    else if (type === "next") currentPage = Math.min(totalPage, currentPage + 1);
                    else if (type === "last") currentPage = totalPage;
                    renderTable(currentPage);
                    renderPagination(dataAll, currentPage, PAGE_SIZE);
                    updatePageInfo(dataAll, currentPage, PAGE_SIZE);
                });
            });
        }
