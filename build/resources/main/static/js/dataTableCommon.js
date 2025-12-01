//data table 언어
var lang_kor = {
    "decimal": "",
    "emptyTable": "데이터가 없습니다.",
    "info": "_TOTAL_개 항목 중 _START_ - _END_개 표시 ",
    "infoEmpty": "0개",
    "infoFiltered": "전체 _MAX_개 표시",
    "infoPostFix": "",
    "thousands": ",",
    "lengthMenu": "_MENU_개씩 보기",
    "loadingRecords": "로딩중...",
    "processing": "처리중...",
    "search": "검색 : ",
    "zeroRecords": "조회 결과가 없습니다.",
    /*
    "paginate": {
        "first": "첫 페이지",
        "last": "마지막 페이지",
        "next": "다음",
        "previous": "이전"
    },
    */
    "aria": {
        "sortAscending": " :  오름차순 정렬",
        "sortDescending": " :  내림차순 정렬"
    }
}

function initializeDataTable(selector, ajaxUrl, methodType, columns, customOptions = {}, searchCondition , ajaxType, customDataSrc) {
    const defaultAjax = {
        "url": ajaxUrl,
        "type": methodType,
        "contentType": "application/json",
        "data": function(d) {
            if(searchCondition){ // 검색 조건이있으면 추가
                // 새로운 Object 생성 후 ... 연산자를 통해 d와 추가 데이터 병합
                const requestData = Object.assign(
                    searchCondition(), // 검색 조건 추가
                    d  // d 객체의 기본 데이터를 병합
                );
                return JSON.stringify(requestData);
            }else{
                return JSON.stringify(d);
            }
        },
        error: function(xhr, status, error) {
            console.error('DataTable AJAX Error:', xhr.status, error);

            if (xhr.status === 0) {
                alert('외부 서버에 연결할 수 없습니다. 서버 상태를 확인해주세요.');
                window.location.href = '/error/view?error=connection&message=' + encodeURIComponent('외부 서버 연결 실패');
            } else if (xhr.status === 401) {
                window.location.href = '/error/401';
            } else if (xhr.status === 403) {
                window.location.href = '/error/403';
            } else if (xhr.status === 404) {
                window.location.href = '/error/404';
            } else if (xhr.status === 500) {
                window.location.href = '/error/500';
            } else {
                alert('데이터 조회 중 오류가 발생했습니다.');
            }
        },
        dataSrc: customDataSrc || function (res) {
            console.info("initializeDataTable.defaultAjax response ==> ", res);
            if (res.code !== "SUCCESS") {
                alert(res.message);
                return false;
            } else {
                return res.data;
            }

        }
    }

    const defaultOptions = {
        "processing": true,
        "paging": true,        // 페이징 기능 활성화 (기본값: true)
        "searching": false,     // 검색 기능 활성화 (기본값: true)
        "ordering": true,      // 정렬 기능 활성화 (기본값: true)
        "lengthChange": false,  // 기본 UI 제거
        // "dom": 't',     //  테이블만 보이게 (paginate 제거)
        "pageLength": 5,       // 한 페이지에 표시할 행의 수
        "lengthMenu": [5, 10, 15, 20, 25],  // 사용자 지정 개수 메뉴
        "language": lang_kor,
    };

    if (columns) {
        defaultOptions.columns = columns
    }

    if(!ajaxType){
        ajaxType = defaultAjax;
        defaultOptions.ajax = ajaxType;
    } else if (ajaxType !== false) {
        defaultOptions.ajax = ajaxType;
    }

    // 기본 옵션에 사용자 정의 옵션을 병합
    const options = $.extend(true, {}, defaultOptions, customOptions);
    return $(selector).DataTable(options);
}

//  테이블 편집 기능 활성화
/* 사용법
enableTableEditing('#TableId', columns);
*/
function enableTableEditing(dataTableSelector, columns) {
    $(dataTableSelector + ' tbody').on('click', 'td', function () {
        const cell = $(this);
        if (cell.find('input').length > 0) return;

        const original = cell.text();
        const input = $('<input type="text" class="form-control form-control-sm" />').val(original);
        cell.empty().append(input);

        input.focus().on('blur', function () {
            const newValue = input.val();
            const row = $(dataTableSelector).DataTable().row(cell.closest('tr'));
            const colIndex = cell.index();
            const colData = columns[colIndex] ? columns[colIndex].data : null;

            if (colData && typeof colData === 'string') {
                row.data()[colData] = newValue;
                row.invalidate().draw(false);
            }
        });
    });
}
// 테이블 로우 클릭 활성화
function enableRowSelection(dataTableSelector) {
    $(dataTableSelector + ' tbody').on('click', 'tr', function () {
        $(this).toggleClass('selected');
    });
}

// 행 추가 버튼 클릭 시
/* 사용법
$('#addRowBtn').on('click', () => {
    addRowToTable('#TableId', columns);
});
*/
function addRowToTable(dataTableSelector, columns) {
    const newRow = {};
    columns.forEach(col => {
        if (typeof col.data === 'string') newRow[col.data] = '';
    });
    $(dataTableSelector).DataTable().row.add(newRow).draw();
}

/*
선택된 행 삭제 버튼 클릭 시
$('#BtnId').on('click', () => {
    deleteSelectedRows('#tableId');
});
*/
function deleteSelectedRows(dataTableSelector) {
    const table = $(dataTableSelector).DataTable();
    table.rows('.selected').remove().draw();
}

/*
유효성 검사 버튼 클릭 시
$('#validateBtn').on('click', () => {
    const invalidRows = validateTableData('#TableId', ['필드1', 'aplyYmd']);
    if (invalidRows.length > 0) {
        alert(`다음 행에 필수값이 누락됨: ${invalidRows.join(', ')}`);
    } else {
        alert('모든 행이 유효합니다!');
    }
});
*/
function validateTableData(dataTableSelector, requiredFields) {
    const table = $(dataTableSelector).DataTable();
    const invalidRows = [];
    table.rows().every(function (rowIdx, tableLoop, rowLoop) {
        const rowData = this.data();
        for (const field of requiredFields) {
            if (!rowData[field] || rowData[field].toString().trim() === '') {
                invalidRows.push(rowIdx + 1); // +1 for human-readable row number
                break;
            }
        }
    });
    return invalidRows;
}

/*
저장 버튼 클릭 시
$('#saveBtn').on('click', () => {
    const modifiedData = getModifiedData('#TableId');
    console.log('저장할 데이터:', modifiedData);
    // AJAX 호출 등으로 전송 처리 가능
});
*/
function getModifiedData(dataTableSelector) {
    return $(dataTableSelector).DataTable().rows().data().toArray();
}

let pageFlag = false;

// 커스텀 페이지네이션 렌더링 함수 (공통)
function renderCustomPagination(api) {
    const pageInfo = api.page.info();
    const currentPage = pageInfo.page;
    const totalPages = pageInfo.pages;
    
    // 페이지가 0개일 때만 숨김 (1개일 때는 표시)
    if (totalPages <= 0) return;
    
    // 기존 커스텀 페이지네이션 제거
    $('#custom-pagination').remove();
    
    // DataTable 기본 스타일로 페이지네이션 생성
    let paginationHtml = '<div id="custom-pagination" class="dt-paging" style="margin-top: 15px;">';
    paginationHtml += '<nav aria-label="pagination">';
    
    // 처음/이전 버튼
    paginationHtml += `<button class="dt-paging-button first ${currentPage === 0 ? 'disabled' : ''}" type="button" data-page="0" ${currentPage === 0 ? 'disabled' : ''}>«</button>`;
    paginationHtml += `<button class="dt-paging-button previous ${currentPage === 0 ? 'disabled' : ''}" type="button" data-page="${currentPage - 1}" ${currentPage === 0 ? 'disabled' : ''}>‹</button>`;
    
    // 페이지 번호 (최대 10개 표시)
    let startPage = Math.max(0, currentPage - 4);
    let endPage = Math.min(totalPages - 1, startPage + 9);
    
    if (endPage - startPage < 9) {
        startPage = Math.max(0, endPage - 9);
    }
    
    for (let i = startPage; i <= endPage; i++) {
        paginationHtml += `<button class="dt-paging-button ${i === currentPage ? 'current' : ''}" type="button" data-page="${i}" ${i === currentPage ? 'aria-current="page"' : ''}>${i + 1}</button>`;
    }
    
    // 다음/마지막 버튼
    paginationHtml += `<button class="dt-paging-button next ${currentPage >= totalPages - 1 ? 'disabled' : ''}" type="button" data-page="${currentPage + 1}" ${currentPage >= totalPages - 1 ? 'disabled' : ''}>›</button>`;
    paginationHtml += `<button class="dt-paging-button last ${currentPage >= totalPages - 1 ? 'disabled' : ''}" type="button" data-page="${totalPages - 1}" ${currentPage >= totalPages - 1 ? 'disabled' : ''}>»</button>`;
    
    paginationHtml += '</nav></div>';
    
    // 테이블 wrapper의 selector 찾기 (동적으로 찾기)
    const tableId = api.table().node().id;
    const wrapperId = tableId ? '#' + tableId + '_wrapper' : api.table().container();
    
    // 테이블 wrapper 하단에 추가
    $(wrapperId).append(paginationHtml);
    
    // 페이지 클릭 이벤트
    $('#custom-pagination button').off('click').on('click', function(e) {
        e.preventDefault();
        if ($(this).hasClass('disabled')) return;
        
        const page = $(this).data('page');
        if (page >= 0 && page < totalPages) {
            api.page(page).draw('page');
        }
    });
}

// 테이블 세팅
function tableCustomOptions(responsive, lengthChange, scrollX, autoWidth, pageLength, customButtons, columnDefs, pageYn) {

    pageFlag = typeof pageYn !== 'undefined' ? pageYn : true;

    return {
        responsive: responsive,
        lengthChange: lengthChange,
        scrollX: scrollX,
        autoWidth: autoWidth,
        pageLength: pageLength, // n개씩 표시
        dom: '<"dt-toolbar-wrapper"Bi<"custom-length">>frtp',
        buttons: Array.isArray(customButtons) && customButtons.length > 0 ? customButtons :
		        [
                    {
                        text: '엑셀 다운로드',
                        className: 'btn my-excel-btn',
                        action: function (e, dt, button, config) {
                            console.log('=== 서버사이드 Excel 다운로드 시작 ===');
                            
                            // DataTables 라이브러리 체크
                            if (typeof $.fn.dataTable === 'undefined') {
                                alert('DataTables 라이브러리가 로드되지 않았습니다.');
                                return;
                            }
                            
                            // 현재 DataTable이 어떤 테이블인지 찾기
                            let currentTableId = '';
                            $('table').each(function() {
                                const tableId = $(this).attr('id');
                                if (tableId && $.fn.dataTable.isDataTable('#' + tableId)) {
                                    const dt = $('#' + tableId).DataTable();
                                    if (dt && dt.table().node() === this) {
                                        currentTableId = tableId;
                                        return false; // break
                                    }
                                }
                            });
                            
                            console.log('현재 테이블 ID:', currentTableId);
                            
                            if (currentTableId) {
                                // 서버사이드 Excel 다운로드 호출
                                downloadServerSideExcel(currentTableId);
                            } else {
                                alert('테이블을 찾을 수 없습니다.');
                            }
                            
                            console.log('=== 서버사이드 Excel 다운로드 종료 ===');
                        }
                    },
                    {
                        extend: 'print',
                        text: '인쇄',
                        className: 'btn my-print-btn',
                        customize: function (win) {
                            console.log('=== 인쇄 커스터마이징 시작 ===');
                            const $doc = $(win.document);
                            
                            // 인쇄창의 테이블을 찾기
                            const $printTbl = $doc.find('table.dataTable').first();
                            console.log('인쇄 테이블 발견:', $printTbl.length > 0);
                            
                            if ($printTbl.length) {
                                // DataTables 라이브러리 체크
                                if (typeof $.fn.dataTable === 'undefined') {
                                    console.warn('DataTables 라이브러리가 로드되지 않았습니다.');
                                    return;
                                }
                                
                                // 현재 DataTable이 어떤 테이블인지 찾기
                                let currentTableId = '';
                                let $originalTable = null;
                                
                                // 모든 테이블을 확인해서 현재 DataTable 찾기
                                $('table').each(function() {
                                    const tableId = $(this).attr('id');
                                    if (tableId && $.fn.dataTable.isDataTable('#' + tableId)) {
                                        // 이 테이블이 DataTable로 초기화되어 있는지 확인
                                        const dt = $('#' + tableId).DataTable();
                                        if (dt && dt.table().node() === this) {
                                            currentTableId = tableId;
                                            $originalTable = $(this);
                                            return false; // break
                                        }
                                    }
                                });
                                
                                console.log('현재 테이블 ID:', currentTableId);
                                
                                if ($originalTable && $originalTable.length > 0) {
                                    // 원본 테이블의 헤더 구조를 읽어서 그대로 적용
                                    const originalTheadHtml = $originalTable.find('thead').html();
                                    console.log('원본 헤더 HTML:', originalTheadHtml);
                                    
                                    // 실제 테이블의 헤더를 인쇄창에 덮어씌우기
                                    $printTbl.find('thead').html(originalTheadHtml);
                                    
                                    // 헤더 스타일 적용
                                    $printTbl.find('thead th').css({
                                        'border': '1px solid #E7E9EF',
                                        'padding': '8px',
                                        'text-align': 'center',
                                        'background-color': '#F5F6FA',
                                        'vertical-align': 'middle'
                                    });
                                    
                                    console.log('헤더 교체 완료');
                                    console.log('교체 후 인쇄창 헤더:', $printTbl.find('thead').html());
                                } else {
                                    console.log('원본 테이블을 찾을 수 없음');
                                }
                            } else {
                                console.log('인쇄 테이블을 찾을 수 없음');
                            }
                            
                            console.log('=== 인쇄 커스터마이징 종료 ===');
                        }
                    }
                ],
        language: {
            infoEmpty: '표시할 데이터가 없습니다',
            infoFiltered: '(전체 _MAX_ 개 중 검색결과)',
            zeroRecords: '검색된 데이터가 없습니다',
        },
        infoCallback: function(settings, start, end, max, total, pre) {
            if (!pageFlag) return ''; // 페이지 정보 숨김

            var api = this.api();
            var pageInfo = api.page.info();

            var page = 0;
            if(pageInfo.pages != '0'){
                page = (Number(pageInfo.page) + 1);
            }

            return '총 <strong>' + total + '</strong> 건  <span class="page-info">(' + page + ' / ' + (pageInfo.pages) + ' 페이지)</span>';
        },
        initComplete: function() {
            var api = this.api();

            if (!pageFlag) {
                $('.custom-length').hide(); // 페이지 길이 선택 숨김
                return;
            }
            var lengthMenu = $('<div class="custom-length-menu"><select class="form-select"><option value="10">10개씩 보기</option><option value="25">25개씩 보기</option><option value="50">50개씩 보기</option><option value="100">100개씩 보기</option></select></div>');

            $('.custom-length').html(lengthMenu);

            lengthMenu.find('select').on('change', function() {
                api.page.len($(this).val()).draw();
            });
        },
        drawCallback: function(settings) {
            // 새로운 drawCallback 설정: 각 페이지 렌더링 후 작업 수행
            // console.log("테이블이 새로 그려졌습니다.");

            var api = this.api();
            var tableNode = api.table().node();   // 현재 테이블의 DOM 노드
            var $table = $(tableNode);
            $table.find("span.notice-link").off("mouseenter").on("mouseenter", function () {
                $(this).css("cursor", "pointer");
            });

        },  //customOptions end
        columnDefs: Array.isArray(columnDefs) && columnDefs.length > 0 ? columnDefs :
                    [
                        { orderable: false, targets: '_all' }
                    ]
	};

}

// 테이블 초기화-tableClear('faqTable')
function tableClear(tableId) {
    // DataTables 라이브러리 체크
    if (typeof $.fn.dataTable === 'undefined') {
        console.warn('DataTables 라이브러리가 로드되지 않았습니다.');
        return;
    }

    const $table = $('#' + tableId);

    if ($.fn.dataTable.isDataTable($table)) {
        $table.DataTable().clear().destroy();
    } else {
        $table.empty(); // 초기화되지 않은 경우 DOM만 정리
    }
}


function initByAjaxDataTable(selector, ajaxUrl, methodType, columns, customOptions = {}, searchCondition , ajaxType, customDataSrc) {
    // DataTables 라이브러리 체크
    if (typeof $.fn.DataTable === 'undefined' || typeof $.fn.dataTable === 'undefined') {
        console.warn('DataTables 라이브러리가 로드되지 않았습니다. selector: ' + selector);
        return null;
    }

    let defaultAjax = null;
    if (ajaxUrl) {
         defaultAjax = {
             "url": ajaxUrl,
             "type": methodType,
             "contentType": "application/json",
             "data": function(d) {
                 if(searchCondition){ // 검색 조건이있으면 추가
                     // 새로운 Object 생성 후 ... 연산자를 통해 d와 추가 데이터 병합
                     const requestData = Object.assign(
                         searchCondition(), // 검색 조건 추가
                         d  // d 객체의 기본 데이터를 병합
                     );
                     return JSON.stringify(requestData);
                 }else{
                     return JSON.stringify(d);
                 }
             },
            error: function(xhr, status, error) {
                console.error('DataTable AJAX Error:', xhr.status, error);

                if (xhr.status === 0) {
                    alert('외부 서버에 연결할 수 없습니다. 서버 상태를 확인해주세요.');
                    window.location.href = '/error/view?error=connection&message=' + encodeURIComponent('외부 서버 연결 실패');
                } else if (xhr.status === 401) {
                    window.location.href = '/error/401';
                } else if (xhr.status === 403) {
                    window.location.href = '/error/403';
                } else if (xhr.status === 404) {
                    window.location.href = '/error/404';
                } else if (xhr.status === 500) {
                    window.location.href = '/error/500';
                } else {
                    alert('데이터 조회 중 오류가 발생했습니다.');
                }
            },
             dataSrc: customDataSrc || function (res) {
                 console.info("initializeDataTable.defaultAjax response ==> ", res);
                 if (res.code !== "SUCCESS") {
                     alert(res.message);
                     return false;
                 } else {
                     return res.data;
                 }

             }
         }

    }

    const defaultOptions = {
        "processing": true,
        "paging": pageFlag,        // 페이징 기능 활성화 (기본값: true)
        "pagingType": "simple_numbers",  // 페이지 번호 표시
        "searching": false,     // 검색 기능 활성화 (기본값: true)
        "ordering": true,      // 정렬 기능 활성화 (기본값: true)
        "lengthChange": false,  // 기본 UI 제거
        // "dom": 't',     //  테이블만 보이게 (paginate 제거)
        "pageLength": 5,       // 한 페이지에 표시할 행의 수
        "lengthMenu": [5, 10, 15, 20, 25],  // 사용자 지정 개수 메뉴
        "language": lang_kor
    };

    if (columns) {
        defaultOptions.columns = columns
    }

    if (ajaxUrl) {
        if(!ajaxType){
            ajaxType = defaultAjax;
            defaultOptions.ajax = ajaxType;
        } else if (ajaxType !== false) {
            defaultOptions.ajax = ajaxType;
        }
    }

    // 사용자 정의 콜백 저장
    const userInitComplete = customOptions.initComplete;
    const userDrawCallback = customOptions.drawCallback;

    // 기본 옵션에 사용자 정의 옵션을 병합
    const options = $.extend(true, {}, defaultOptions, customOptions);
    
    // 콜백 래핑 (커스텀 페이지네이션 + 사용자 정의 콜백)
    options.initComplete = function() {
        if (pageFlag) {
            renderCustomPagination(this.api());
        }
        if (userInitComplete) {
            userInitComplete.call(this);
        }
    };
    
    options.drawCallback = function(settings) {
        if (pageFlag) {
            renderCustomPagination(this.api());
        }
        if (userDrawCallback) {
            userDrawCallback.call(this, settings);
        }
    };

    //기존 테이블이 초기화되어 있다면 먼저 제거
    const $table = typeof selector === 'string' ? $(selector) : selector;
    if ($.fn.dataTable && $.fn.dataTable.isDataTable($table)) {
        $table.DataTable().clear().destroy();
    }

    // 새로 초기화
    //return $(selector).DataTable(options);
    return $table.DataTable(options);
}

// 테이블 세팅
function tableCustomOptionsExcel(responsive, lengthChange, scrollX, autoWidth, pageLength, customButtons, columnDefs, pageYn) {

    pageFlag = true;

    return {
        responsive: responsive,
        lengthChange: lengthChange,
        scrollX: scrollX,
        autoWidth: autoWidth,
        pageLength: pageLength, // n개씩 표시
        dom: '<"dt-toolbar-wrapper"Bi<"custom-length">>frtp',
        buttons: Array.isArray(customButtons) && customButtons.length > 0 ? customButtons :
		        [
                    {
                        text: '엑셀 다운로드',
                        className: 'btn my-excel-btn',
                        action: function (e, dt, button, config) {
                            console.log('=== 서버사이드 Excel 다운로드 시작 ===');

                            // DataTables 라이브러리 체크
                            if (typeof $.fn.dataTable === 'undefined') {
                                alert('DataTables 라이브러리가 로드되지 않았습니다.');
                                return;
                            }

                            // 현재 DataTable이 어떤 테이블인지 찾기
                            let currentTableId = '';
                            $('table').each(function() {
                                const tableId = $(this).attr('id');
                                if (tableId && $.fn.dataTable.isDataTable('#' + tableId)) {
                                    const dt = $('#' + tableId).DataTable();
                                    if (dt && dt.table().node() === this) {
                                        currentTableId = tableId;
                                        return false; // break
                                    }
                                }
                            });

                            console.log('현재 테이블 ID:', currentTableId);

                            if (currentTableId) {
                                // 서버사이드 Excel 다운로드 호출
                                downloadServerSideExcel(currentTableId);
                            } else {
                                alert('테이블을 찾을 수 없습니다.');
                            }

                            console.log('=== 서버사이드 Excel 다운로드 종료 ===');
                        }
                    }
                ],
        language: {
            infoEmpty: '표시할 데이터가 없습니다',
            infoFiltered: '(전체 _MAX_ 개 중 검색결과)',
            zeroRecords: '검색된 데이터가 없습니다',
        },
        infoCallback: function(settings, start, end, max, total, pre) {
            if (!pageFlag) return ''; // 페이지 정보 숨김

            var api = this.api();
            var pageInfo = api.page.info();

            var page = 0;
            if(pageInfo.pages != '0'){
                page = (Number(pageInfo.page) + 1);
            }

            return '총 <strong>' + total + '</strong> 건  <span class="page-info">(' + page + ' / ' + (pageInfo.pages) + ' 페이지)</span>';
        },
        initComplete: function() {
            var api = this.api();

            if (!pageFlag) {
                $('.custom-length').hide(); // 페이지 길이 선택 숨김
                return;
            }
            var lengthMenu = $('<div class="custom-length-menu"><select class="form-select"><option value="10">10개씩 보기</option><option value="25">25개씩 보기</option><option value="50">50개씩 보기</option><option value="100">100개씩 보기</option></select></div>');

            $('.custom-length').html(lengthMenu);

            lengthMenu.find('select').on('change', function() {
                api.page.len($(this).val()).draw();
            });
        },
        drawCallback: function(settings) {
            // 새로운 drawCallback 설정: 각 페이지 렌더링 후 작업 수행
            // console.log("테이블이 새로 그려졌습니다.");

            var api = this.api();
            var tableNode = api.table().node();   // 현재 테이블의 DOM 노드
            var $table = $(tableNode);
            $table.find("span.notice-link").off("mouseenter").on("mouseenter", function () {
                $(this).css("cursor", "pointer");
            });

        },  //customOptions end
        columnDefs: Array.isArray(columnDefs) && columnDefs.length > 0 ? columnDefs :
                    [
                        { orderable: false, targets: '_all' }
                    ]
	};

}


// 테이블 세팅
function tableCustomOptionsNone(responsive, lengthChange, scrollX, autoWidth, pageLength, customButtons, columnDefs, pageYn) {

    pageFlag = typeof pageYn !== 'undefined' ? pageYn : true;

    return {
        responsive: responsive,
        lengthChange: lengthChange,
        scrollX: scrollX,
        autoWidth: autoWidth,
        pageLength: pageLength, // n개씩 표시
        dom: '<"dt-toolbar-wrapper"Bi<"custom-length">>frtp',
        buttons: Array.isArray(customButtons) && customButtons.length > 0 ? customButtons :
	    [
        ],
        language: {
            infoEmpty: '표시할 데이터가 없습니다',
            infoFiltered: '(전체 _MAX_ 개 중 검색결과)',
            zeroRecords: '검색된 데이터가 없습니다',
        },
        infoCallback: function(settings, start, end, max, total, pre) {
            if (!pageFlag) return ''; // 페이지 정보 숨김

            var api = this.api();
            var pageInfo = api.page.info();

            var page = 0;
            if(pageInfo.pages != '0'){
                page = (Number(pageInfo.page) + 1);
            }

            return '총 <strong>' + total + '</strong> 건  <span class="page-info">(' + page + ' / ' + (pageInfo.pages) + ' 페이지)</span>';
        },
        initComplete: function() {
            var api = this.api();

            if (!pageFlag) {
                $('.custom-length').hide(); // 페이지 길이 선택 숨김
                return;
            }
            var lengthMenu = $('<div class="custom-length-menu"><select class="form-select"><option value="10">10개씩 보기</option><option value="25">25개씩 보기</option><option value="50">50개씩 보기</option><option value="100">100개씩 보기</option></select></div>');

            $('.custom-length').html(lengthMenu);

            lengthMenu.find('select').on('change', function() {
                api.page.len($(this).val()).draw();
            });
        },
        drawCallback: function(settings) {
            // 새로운 drawCallback 설정: 각 페이지 렌더링 후 작업 수행
            // console.log("테이블이 새로 그려졌습니다.");

            var api = this.api();
            var tableNode = api.table().node();   // 현재 테이블의 DOM 노드
            var $table = $(tableNode);
            $table.find("span.notice-link").off("mouseenter").on("mouseenter", function () {
                $(this).css("cursor", "pointer");
            });

        },  //customOptions end
        columnDefs: Array.isArray(columnDefs) && columnDefs.length > 0 ? columnDefs :
                    [
                        { orderable: false, targets: '_all' }
                    ]
	};

}

// 서버사이드 Excel 다운로드 함수
function downloadServerSideExcel(tableId) {
    const $originalTable = $('#' + tableId);
    const dt = $originalTable.DataTable();
    
    // 전체 데이터 가져오기
    const allRowsData = dt.rows({page: 'all'}).data().toArray();
    
    // 헤더 구조 동적 생성
    let headerRows = [];
    
    // 원본 HTML에서 헤더 구조 읽기
    const $thead = $originalTable.find('thead');
    if ($thead.length > 0) {
        $thead.find('tr').each(function(rowIndex) {
            const rowHeaders = [];
            $(this).find('th').each(function() {
                const $th = $(this);
                const text = $th.text().trim();
                const rowspan = parseInt($th.attr('rowspan')) || 1;
                const colspan = parseInt($th.attr('colspan')) || 1;
                
                rowHeaders.push({
                    text: text,
                    rowspan: rowspan,
                    colspan: colspan
                });
            });
            if (rowHeaders.length > 0) {
                headerRows.push(rowHeaders);
            }
        });
    }
    
    // 헤더가 비어있으면 기본 구조 사용
    if (headerRows.length === 0) {
        // DataTable의 columns 설정에서 헤더 생성
        const columns = dt.settings()[0].aoColumns;
        if (columns && columns.length > 0) {
            const headerRow = [];
            columns.forEach(function(col) {
                headerRow.push({
                    text: col.sTitle || '',
                    rowspan: 1,
                    colspan: 1
                });
            });
            headerRows.push(headerRow);
        }
    }
    
    // 바디 데이터 처리
    const body = [];
    
    // 헤더 구조에서 컬럼 순서 추출 (원본 HTML 구조 기준)
    const headerColumnOrder = [];
    
    // 첫 번째 헤더 행에서 컬럼 순서 추출
    if (headerRows.length > 0) {
        console.log('첫 번째 헤더 행:', headerRows[0]);
        headerRows[0].forEach(function(headerCell, index) {
            const text = headerCell.text;
            const colspan = headerCell.colspan || 1;
            
            console.log('첫 번째 행 셀 ' + index + ':', text, 'colspan:', colspan);
            
            if (text && text.trim() !== '') {
                headerColumnOrder.push(text.trim());
            } else if (text === '') {
                // 빈 셀도 추가 (체크박스용)
                headerColumnOrder.push('');
            }
            
            // colspan이 1보다 크면 추가 컬럼들도 추가
            for (let i = 1; i < colspan; i++) {
                headerColumnOrder.push('');
            }
        });
    }
    
    // 두 번째 헤더 행에서 추가 컬럼 추출 (올바른 위치에 삽입)
    if (headerRows.length > 1) {
        const secondRow = headerRows[1];
        let secondRowIndex = 0;
        
        console.log('두 번째 헤더 행:', secondRow);
        
        // 첫 번째 행의 각 셀에 대해 두 번째 행의 해당 셀들을 삽입
        headerRows[0].forEach(function(headerCell, index) {
            const rowspan = headerCell.rowspan || 1;
            const colspan = headerCell.colspan || 1;
            
            console.log('첫 번째 행 셀 ' + index + ' 처리:', headerCell.text, 'rowspan:', rowspan, 'colspan:', colspan);
            
            if (rowspan === 1) {
                // rowspan=1인 경우에만 두 번째 행의 셀 추가
                if (colspan === 1) {
                    // 단일 셀인 경우, 두 번째 행의 해당 셀 추가
                    if (secondRowIndex < secondRow.length) {
                        const secondCellText = secondRow[secondRowIndex].text;
                        console.log('두 번째 행 셀 ' + secondRowIndex + ' 추가:', secondCellText);
                        if (secondCellText && secondCellText.trim() !== '') {
                            headerColumnOrder.push(secondCellText.trim());
                        } else {
                            headerColumnOrder.push('');
                        }
                        secondRowIndex++;
                    }
                } else {
                    // colspan이 있는 경우, 두 번째 행의 여러 셀 추가
                    for (let i = 0; i < colspan && secondRowIndex < secondRow.length; i++) {
                        const secondCellText = secondRow[secondRowIndex].text;
                        console.log('두 번째 행 셀 ' + secondRowIndex + ' 추가 (colspan):', secondCellText);
                        if (secondCellText && secondCellText.trim() !== '') {
                            headerColumnOrder.push(secondCellText.trim());
                        } else {
                            headerColumnOrder.push('');
                        }
                        secondRowIndex++;
                    }
                }
            } else {
                console.log('rowspan=2인 셀 건너뜀:', headerCell.text);
            }
            // rowspan=2인 경우는 두 번째 행에 해당하는 셀이 없으므로 건너뜀
        });
    }
    
    // DataTable 컬럼 정보 가져오기
    const columns = dt.settings()[0].aoColumns;
    console.log('헤더 컬럼 순서:', headerColumnOrder);
    console.log('DataTable 컬럼 정보:', columns.map(function(col, index) {
        return {
            index: index,
            data: col.mData || col.data,
            title: col.sTitle || ''
        };
    }));
    
    allRowsData.forEach(function(rowData, index) {
        const processedRow = {};
        
        if (typeof rowData === 'object' && rowData !== null) {
            console.log('행 ' + index + ' 원본 데이터:', rowData);
            
            // 헤더 컬럼 순서대로 데이터 처리
            headerColumnOrder.forEach(function(headerText, colIndex) {
                let value = '';
                
                if (headerText === '번호') {
                    // 번호는 행 인덱스 + 1
                    value = (index + 1).toString();
                } else if (headerText === '') {
                    // 빈 셀 (체크박스용)
                    value = '';
                } else {
                    // DataTable 컬럼에서 직접 데이터 가져오기
                    const col = columns[colIndex];
                    if (col) {
                        const dataKey = col.mData || col.data;
                        if (dataKey && rowData.hasOwnProperty(dataKey)) {
                            value = rowData[dataKey] || '';
                        }
                    }
                }
                
                // HTML 태그 및 엔티티 제거
                if (typeof value === 'string') {
                    // HTML 태그 제거
                    value = value.replace(/<[^>]*>/g, '');
                    // HTML 엔티티 디코딩
                    value = value.replace(/&gt;/g, '>')
                                 .replace(/&lt;/g, '<')
                                 .replace(/&amp;/g, '&')
                                 .replace(/&quot;/g, '"')
                                 .replace(/&#39;/g, "'")
                                 .replace(/&nbsp;/g, ' ')
                                 .trim();
                }
                
                processedRow[headerText] = value;
                console.log('컬럼 ' + colIndex + ' (' + headerText + '): "' + value + '"');
            });
        }
        
        body.push(processedRow);
    });
    
    console.log('처리된 바디 데이터:', body);
    
    // 서버로 전송할 데이터 구성
    const payload = {
        tableId: tableId,
        headers: headerRows,
        body: body
    };
    
    console.log('서버로 전송할 데이터:', payload);
    
    // 서버로 POST 요청
    fetch('/api/common/download/excel', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(payload)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('서버 응답 오류');
        }
        return response.blob();
    })
    .then(blob => {
        // 파일 다운로드
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = tableId + '_export.xlsx';
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);
        
        console.log('서버사이드 엑셀 다운로드 완료');
    })
    .catch(error => {
        console.error('서버사이드 엑셀 다운로드 오류:', error);
        alert('엑셀 다운로드 중 오류가 발생했습니다.');
    });
    
    console.log('=== 서버사이드 Excel 다운로드 종료 ===');
}

// 예시 사용:
// bindExcelUpload('#fileUpload', '#rsvtDsctnInqTable', columns);
// enableTableEditing('#rsvtDsctnInqTable', columns);
// addRowToTable('#rsvtDsctnInqTable', columns);
// deleteSelectedRows('#rsvtDsctnInqTable');
// const invalids = validateTableData('#rsvtDsctnInqTable', ['필드1', '필드2']);
// const modified = getModifiedData('#rsvtDsctnInqTable');


