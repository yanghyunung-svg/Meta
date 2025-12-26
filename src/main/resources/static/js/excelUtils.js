/**
 * HTML Table → Excel 다운로드 (공용)
 * @param {string} tableId  테이블 ID
 * @param {string} fileName 다운로드 파일명 (확장자 제외)
 */
function downloadTableToExcel(tableId, fileName = 'excel_download') {
    const table = document.getElementById(tableId);
    if (!table) {
        alert('테이블을 찾을 수 없습니다.');
        return;
    }

    // 테이블 복제 (원본 영향 방지)
    const clonedTable = table.cloneNode(true);

    // tbody에 데이터 없을 경우 방지
    if (clonedTable.querySelectorAll('tbody tr').length === 0) {
        alert('다운로드할 데이터가 없습니다.');
        return;
    }

    const html = `
        <html xmlns:x="urn:schemas-microsoft-com:office:excel">
        <head>
            <meta charset="UTF-8">
        </head>
        <body>
            ${clonedTable.outerHTML}
        </body>
        </html>
    `;

    const blob = new Blob([html], {
        type: 'application/vnd.ms-excel;charset=utf-8;'
    });

    const url = URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = `${fileName}_${getTodayExcel()}.xls`;
    document.body.appendChild(a);
    a.click();

    document.body.removeChild(a);
    URL.revokeObjectURL(url);
}


/** YYYYMMDD */
function getTodayExcel() {
    const d = new Date();
    return d.getFullYear()
        + String(d.getMonth() + 1).padStart(2, '0')
        + String(d.getDate()).padStart(2, '0');
}
