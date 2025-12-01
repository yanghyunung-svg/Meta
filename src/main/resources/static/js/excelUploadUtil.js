function generateTitleToDataMap(columns) {
    const map = {};
    columns.forEach(col => {
        if (col.title && col.data !== null) {
            map[col.title] = col.data;
        }
    });
    return map;
}

function parseDateTime(datetimeStr) {
    const result = { ymd: '', tm: '' };
    if (!datetimeStr || typeof datetimeStr !== 'string') return result;
    const parts = datetimeStr.trim().split(' ');
    if (parts.length === 2) {
        const [dateStr, timeStr] = parts;
        const ymd = dateStr.replace(/-/g, '');
        const tm = timeStr.replace(/:/g, '').padStart(4, '0');
        if (ymd.length === 8 && tm.length === 4) {
            result.ymd = ymd;
            result.tm = tm;
        }
    }
    return result;
}

function mapExcelToUploadFormat(excelRows, titleToDataMap) {
    return excelRows.map(row => {
        const mapped = {
            fnstCd: '',
            ntslEntNo: '',
            pblcnAplyNo: ''
        };

        for (const header in row) {
            const dataKey = titleToDataMap[header];
            const value = row[header];

            if (dataKey) {
                mapped[dataKey] = value;
            } else if (header === '예약일시' && value) {
                const dateTime = parseDateTime(value);
                mapped['rsvtPblcnYmd'] = dateTime.ymd;
                mapped['rsvtPblcnTm'] = dateTime.tm;
            }
        }

        return mapped;
    });
}

function bindExcelUpload(inputSelector, dataTableSelector, columns) {
    const titleMap = generateTitleToDataMap(columns);
    const table = $(dataTableSelector).DataTable();

    $(inputSelector).on("change", function(e) {
        const file = e.target.files[0];
        const reader = new FileReader();
        reader.onload = function(evt) {
            const data = evt.target.result;
            const workbook = XLSX.read(data, { type: 'binary' });
            const sheetName = workbook.SheetNames[0];
            const jsonData = XLSX.utils.sheet_to_json(workbook.Sheets[sheetName]);

            const converted = mapExcelToUploadFormat(jsonData, titleMap);
            table.clear();
            table.rows.add(converted);
            table.draw();
        };
        reader.readAsBinaryString(file);
    });
}

// 예시 사용:
// bindExcelUpload('#fileUpload', '#rsvtDsctnInqTable', columns);