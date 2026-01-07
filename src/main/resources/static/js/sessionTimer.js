async function updateSessionTimer() {
    const res = await fetch('/meta/remaining-time');
    const data = await res.json();

    let sec = data.remaining;

    if (sec <= 0) {
        alert('세션이 만료되었습니다.');
        location.href = '/login?expired=true';
        return;
    }

    const m = Math.floor(sec / 60);
    const s = sec % 60;

    const el = document.getElementById('sessionTimer');
    if (el) {
        el.innerText = `세션 만료까지 ${m}:${s.toString().padStart(2, '0')}`;
    }
}

/* ▶ 호출 */
//setInterval(updateSessionTimer, 1000);
updateSessionTimer();
