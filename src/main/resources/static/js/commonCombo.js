
function loadCodeCombo(grpCd, cd) {
    const reqData = {grpCd: grpCd };
    fetch('/meta/getCodeAllData', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reqData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error(`HTTP Error: ${response.status} ${response.statusText}`);
        }
        return response.json();
    })
    .then(data => { populateCombo(data, cd); })
    .catch(error => {
        console.error('콤보 데이터 로드 실패', {
            reqData,
            error
        });
    });
}

async function loadTelgmCombo(instCdEl, taskSeCdEl, telgmKndCdEl, cdEl) {
        const reqData = {
            instCd     : instCdEl?.value || '',
            taskSeCd   : taskSeCdEl?.value || '',
            telgmKndCd : telgmKndCdEl?.value || '',
            func       : cdEl || ''
        };

    try {
        const response = await fetch('/meta/getTelgmComboData', {
            method  : 'POST',
            headers : { 'Content-Type': 'application/json' },
            body    : JSON.stringify(reqData)
        });

        if (!response.ok) {
            throw new Error(`HTTP ${response.status} : ${response.statusText}`);
        }

        const data = await response.json();
        populateCombo(data, cdEl);

    } catch (error) {
        console.error('콤보 데이터 로드 실패', {
            reqData,
            error
        });
    }
}



function populateCombo(dataArray, cd) {
    const selectElement = document.getElementById(cd);
    while (selectElement.options.length > 1) {
        selectElement.remove(1);
    }
    dataArray.forEach(item => {
        const option = document.createElement('option');
        option.value = item.cd;
        option.text =  item.cd + "-" + item.cdNm ;
        selectElement.appendChild(option);
    });
}