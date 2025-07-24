// 로그인 유저 확인(GET)
document.addEventListener("DOMContentLoaded", function () {
  const token = localStorage.getItem("accessToken");

  fetch("/user-info", {
    method: "GET",
    headers: {
      accessToken: token,
    },
  })
    .then((res) => {
      if (!res.ok) throw new Error("인증 실패");
      return res.json();
    })
    .then((data) => {
      document.getElementById("loggedInSection").style.display = "block";
      document.getElementById("loggedOutSection").style.display = "none";
      document.getElementById("userNameSpan").innerText = data.username;
    })
    .catch((err) => {
      document.getElementById("loggedInSection").style.display = "none";
      document.getElementById("loggedOutSection").style.display = "block";
      console.error(err);
    });
});

// 로그아웃(POST)
function logout() {
  const token = localStorage.getItem("accessToken");

  fetch("/logout", {
    method: "POST",
  })
    .then((res) => {
      if (res.ok) {
        localStorage.removeItem("accessToken");
        window.location.href = "/";
      } else {
        alert("로그아웃 실패");
      }
    })
    .catch((err) => {
      console.error("로그아웃 에러:", err);
    });
}
