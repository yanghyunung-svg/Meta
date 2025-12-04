// sessionTimer.js
export class SessionTimer {
    constructor(timerElementId, expireTime) {
        this.timerEl = document.getElementById(timerElementId);
        this.expireTime = expireTime;
        this.timerInterval = null;
    }

    start() {
        this.update(); // 즉시 표시
        this.timerInterval = setInterval(() => this.update(), 1000);
    }

    update() {
        const now = Date.now();
        const diff = this.expireTime - now;

        const timerTextEl = this.timerEl.querySelector("#timerText");

        if (diff <= 0) {
            clearInterval(this.timerInterval);
            timerTextEl.innerText = "세션 만료됨";

            // 서버 로그아웃 후 로그인 페이지 이동
            fetch("/logout", { method: "POST" }).finally(() => {
                window.location.href = "/login";
            });
            return;
        }

        const min = Math.floor(diff / 1000 / 60);
        const sec = Math.floor((diff / 1000) % 60);

        timerTextEl.innerText = `${String(min).padStart(2, '0')}:${String(sec).padStart(2, '0')}`;

        // 만료 5분 전 경고 색 변경
        if (diff <= 5 * 60 * 1000) {
            this.timerEl.classList.add("timer-warning");
        } else {
            this.timerEl.classList.remove("timer-warning");
        }
    }
}
