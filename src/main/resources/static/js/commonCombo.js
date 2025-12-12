

   function loadCodeCombo(grpCd, cd) {
        const payload = {grpCd: grpCd };
        fetch('/meta/getCodeAllData', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        })
        .then(response => {
            if (!response.ok) { throw new Error(`HTTP Error: ${response.status} ${response.statusText}`); }
            return response.json();
        })
        .then(data => { populateCombo(data, cd); })
        .catch(error => {
            console.error("콤보 데이터 로드 실패:", error);
        });
    }

    function populateCombo(dataArray, cd) {
        const selectElement = document.getElementById(cd);
        while (selectElement.options.length > 1) {
            selectElement.remove(1);
        }
        dataArray.forEach(item => {
            const option = document.createElement('option');
            option.value = item.cd;
            option.text =  item.cdNm ;
            selectElement.appendChild(option);
        });
    }