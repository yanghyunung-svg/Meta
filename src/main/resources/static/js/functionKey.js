
    // Function Key
    document.addEventListener('keydown', (e) => {
        switch (e.key) {
        case 'F1':
            e.preventDefault();
            document.getElementById('btnInit').click();
            break;

        case 'F2':
            e.preventDefault();
            document.getElementById('btnSearch').click();
            break;

        case 'F3':
            e.preventDefault();
            const btnReg = document.getElementById("btnReg");
            if(btnReg) document.getElementById('btnReg').click();
            break;

        default:
            break;
        }
    });