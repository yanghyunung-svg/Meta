// 파일업로드 JS


/**
 * 파일찾기에서 파일 선택 후  validate
 * @param fileList - 선택한 파일
 * @param selectedFiles - 최종 파일
 * @param maxFiles - 첨부가능 파일 용량
 * @param maxSize - 파일선택가능 갯수
 * @param fileType - 선택가능한 파일형식
 * @param dupChk - 중복체크 여부
 */
function validateFiles(fileList, selectedFiles, maxFiles, maxSize, fileType, dupChk) {

    var validFiles = [];
    var errors = [];

    for (var i = 0; i < fileList.length; i++) {
        var file = fileList[i];

        if (selectedFiles.length + validFiles.length >= maxFiles) {
            errors.push('최대 ' + maxFiles + '개의 파일만 업로드할 수 있습니다.');
            break;
        }

        if (file.size > maxSize) {
            errors.push('파일 "' + file.name + '"은 크기 제한(10MB)을 초과합니다.');
            continue;
        }

        var ext = file.name.split('.').pop().toLowerCase();
        if (fileType.indexOf(ext) === -1) {
            errors.push('파일 "' + file.name + '"은 허용되지 않는 형식입니다.');
            continue;
        }

        var isDuplicate = selectedFiles.some(function(existing) {
            //return existing.name === file.name && existing.size === file.size;
            return existing.name === file.name &&
                   existing.size === file.size &&
                   existing.lastModified === file.lastModified;
        });

        if(dupChk){
            if (isDuplicate) {
                errors.push('파일 "' + file.name + '"은 이미 선택된 파일입니다.');
                continue;
            } else {
                validFiles.push(file);
            }
        } else {

            // 중복 체크 비활성화 시: 중복 파일만 제외하고 나머지는 추가
            if (!isDuplicate) {
                validFiles.push(file);
            } else {
                //errors.push('파일 "' + file.name + '"은 중복되어 제외되었습니다.');
            }

        }

    }

    return {
        validFiles: validFiles,
        errors: errors
    };

}


/**
 * 등록+수정 화면에서 사용되는 선택된 파일 display
 * @param fileList - 선택된 파일
 * @param savedFiles - 조회된 파일
 * @param selectedFiles - 선탠된 파일
 * @param containerId - divID
 * @param delBtnDsp - 삭제버튼 visible
 */
window.deletedFileSnList = [];
function multiDisplayFiles(savedFiles, selectedFiles, containerId, delBtnDsp = true, fileInputEl = null) {

    var container = document.getElementById(containerId);
    if (!container) {
        console.warn('지정한 컨테이너 ID를 찾을 수 없습니다:', containerId);
        return;
    }

    container.innerHTML = ''; // 기존 내용 초기화
    const allFiles = savedFiles.concat(selectedFiles);

    for (var i = 0; i < allFiles.length; i++) {
        var file = allFiles[i];

        // 파일 div 생성
        var fileDiv = document.createElement('div');
        fileDiv.className = 'added-file';

        // 파일명 링크 생성
        var fileSpan = document.createElement('span');
        var fileLink = document.createElement('a');
        fileLink.href = 'javascript:;';
        fileLink.textContent = file.name || '파일명';

        fileSpan.appendChild(fileLink);
        fileDiv.appendChild(fileSpan);

        if(delBtnDsp){

            // 삭제 버튼 생성
            var deleteBtn = document.createElement('button');
            deleteBtn.type = 'button';
            deleteBtn.className = 'ico-delete';
            deleteBtn.setAttribute('data-index', i);

            // 삭제 이벤트 연결
            deleteBtn.addEventListener('click', function () {
                const index = parseInt(this.getAttribute('data-index'));
                if (index < savedFiles.length) {

                    // 삭제된 기존 파일 SN 추적
                    if (savedFiles[index].sn) {
                        window.deletedFileSnList.push(savedFiles[index].sn);
                    }

                    savedFiles.splice(index, 1);
                } else {
                    selectedFiles.splice(index - savedFiles.length, 1);
                }

                if (fileInputEl) {
                    fileInputEl.value = '';
                }

                multiDisplayFiles(savedFiles, selectedFiles, containerId);
            });

            fileDiv.appendChild(deleteBtn);

        }

        container.appendChild(fileDiv);
    }
}


/**
 * 선택된 파일 display
 * @param fileList - 선택된 파일
 * @param containerId - divID
 */
function displayFiles(fileList, containerId) {

    var container = document.getElementById(containerId);
    if (!container) {
        console.warn('지정한 컨테이너 ID를 찾을 수 없습니다:', containerId);
        return;
    }

    container.innerHTML = ''; // 기존 내용 초기화

    for (var i = 0; i < fileList.length; i++) {
        var file = fileList[i];

        // 파일 div 생성
        var fileDiv = document.createElement('div');
        fileDiv.className = 'added-file';

        // 파일명 링크 생성
        var fileSpan = document.createElement('span');
        var fileLink = document.createElement('a');
        fileLink.href = 'javascript:;';
        fileLink.textContent = file.name || '파일명';

        fileSpan.appendChild(fileLink);
        fileDiv.appendChild(fileSpan);

        // 삭제 버튼 생성
        var deleteBtn = document.createElement('button');
        deleteBtn.type = 'button';
        deleteBtn.className = 'ico-delete';
        deleteBtn.setAttribute('data-index', i);

        // 삭제 이벤트 연결
        deleteBtn.addEventListener('click', function() {
            var index = parseInt(this.getAttribute('data-index'));
            fileList.splice(index, 1);
            displayFiles(fileList, containerId); // 다시 렌더링
        });

        fileDiv.appendChild(deleteBtn);
        container.appendChild(fileDiv);
    }
}



/**
 * 파일 삭제
 * @param bytes - 파일size
 */
function fileDelete(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}


/**
 * 파일 크기 포맷팅
 * @param bytes - 파일size
 */
function formatFileSize(bytes) {
    if (bytes === 0) return '0 Bytes';
    const k = 1024;
    const sizes = ['Bytes', 'KB', 'MB', 'GB'];
    const i = Math.floor(Math.log(bytes) / Math.log(k));
    return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
}

/**
 * 업로드 버튼 상태 업데이트
 * @param btnId - 버튼ID
 * @param selectedFiles - 선택된 파일
 */
function updateUploadButton(btnId, selectedFiles) {
    var uploadBtn = document.getElementById(btnId);
    if (!uploadBtn) return;

    if (validFiles.length > 0) {
        uploadBtn.style.display = 'inline-block'; // 또는 'block'
        uploadBtn.textContent = '업로드 시작 (' + validFiles.length + '/5)';
    } else {
        uploadBtn.style.display = 'none';
    }
}

/**
 * 파일 업로드
 * @param selectedFiles - 선택된 파일
 * @param btnId - 업로드버튼ID
 * @param progressBarId - 프로그래스바 ID
 */
function fileUpload(selectedFiles, btnId, progressBarId){

    if (!selectedFiles || selectedFiles.length === 0) {
        alert('업로드할 파일을 선택해주세요.');
        return;
    }

    var formData = new FormData();

    // pathSe와 SEQ 값을 FormData에 추가
    var pathSeInput = document.querySelector('.pathSe');
    var seqInput = document.querySelector('.SEQ');
    var pathSe = pathSeInput ? pathSeInput.value : 'TST';
    var seq = seqInput ? seqInput.value : '001';

    formData.append('pathSe', pathSe);
    formData.append('SEQ', seq);

    console.log('업로드 파라미터:', { pathSe: pathSe, seq: seq });

    // 파일 추가
    for (var i = 0; i < selectedFiles.length; i++) {
        formData.append('file', selectedFiles[i]);
    }

    // 업로드 버튼 처리
    var uploadBtn = document.getElementById(btnId);
    if (uploadBtn) {
        uploadBtn.disabled = true;
        uploadBtn.textContent = '업로드 중...';
    }

    // 프로그래스바 표시
    var progressBar = document.getElementById(progressBarId);
    if (progressBar) {
        progressBar.style.display = 'block';
    }


    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/common/multiFileUpload', true);

    xhr.upload.addEventListener('progress', function(evt) {
        if (evt.lengthComputable) {
            var percentComplete = (evt.loaded / evt.total) * 100;
            var progressFill = document.getElementById('progressFill');
            if (progressFill) {
                progressFill.style.width = percentComplete + '%';
            }
        }
    }, false);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4) {
            if (xhr.status === 200) {
                try {
                    var response = JSON.parse(xhr.responseText);
                    console.log(response);

                    if (response.code === 'SUCCESS') {
                        alert('파일 업로드가 완료되었습니다!');
                        console.log('업로드 결과:', response.data);

                        selectedFiles.length = 0;
                        displayFiles();
                        updateUploadButton(btnId);
                    } else {
                        alert('업로드 실패: ' + response.message);
                    }
                } catch (e) {
                    alert('응답 처리 중 오류가 발생했습니다.');
                    console.error('파싱 오류:', e);
                }
            } else {
                alert('업로드 중 오류가 발생했습니다: ' + xhr.statusText);
                console.error('업로드 오류:', xhr.responseText);
            }

            // 완료 후 UI 복구
            if (uploadBtn) {
                uploadBtn.disabled = false;
                uploadBtn.textContent = '업로드 시작';
            }

            if (progressBar) {
                progressBar.style.display = 'none';
            }

            var progressFillReset = document.getElementById('progressFill');
            if (progressFillReset) {
                progressFillReset.style.width = '0%';
            }
        }
    };

    xhr.send(formData);

}


/**
 * 파일 다운로드
 * @param pathSe - 파일경로구분
 * @param fileCount - 파일갯수
 */
function fileDownload(pathSe, fileCount){

    if(fileCount == null){
        fileCount = 0;
    }

    if (pathSe && fileCount > 0) {

        // PATH_SE로 파일 목록 조회 후 개별 다운로드
        $.ajax({
            url: '/common/files/' + pathSe,
            type: 'POST',
            contentType: 'application/json',
            success: function(response) {
                if (response.code === 'SUCCESS' && response.data && response.data.length > 0) {
                    // 각 파일을 개별적으로 다운로드
                    response.data.forEach(function(file, index) {
                        setTimeout(function() {
                            downloadFileBySn(file.sn);
                        }, index * 1000); // 1초 간격으로 다운로드
                    });
                } else {
                    alert('파일 정보를 가져올 수 없습니다.');
                }
            },
            error: function() {
                alert('파일 목록 조회에 실패했습니다.');
            }
        });
    } else {
        alert('첨부파일이 없습니다.');
    }

}

/**
 * 파일 URL
 * @param sn - 파일순번
 */
function downloadFileBySn(sn) {
    location.href = '/common/download/sn/' + sn;
}


