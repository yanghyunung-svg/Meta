export class SessionTimer {
    constructor(timerElementId) {
        this.timerEl = document.getElementById(timerElementId);
        this.timerTextEl = this.timerEl.querySelector("#timerText");
        this.expireTime = 0;
        this.timerInterval = null;
    }

    async start() {
        await this.syncExpireTime();
        this.update();
        this.timerInterval = setInterval(() => this.update(), 1000);
    }

    async syncExpireTime() {
        const res = await fetch("/session/expire-time");
        const data = await res.json();
        this.expireTime = data.expireTime;
    }

    update() {
        const diff = this.expireTime - Date.now();

        if (diff <= 0) {
            localStorage.setItem("SESSION_EXPIRED", Date.now());
            location.replace("/logout?timeout=true");
            return;
        }

        const min = Math.floor(diff / 60000);
        const sec = Math.floor((diff % 60000) / 1000);

        this.timerTextEl.innerText =
            `${String(min).padStart(2, "0")}:${String(sec).padStart(2, "0")}`;

        this.timerEl.classList.toggle(
            "timer-warning",
            diff <= 5 * 60 * 1000
        );
    }
}
