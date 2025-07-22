// JWT 토큰에서 페이로드 추출하는 함수
function parseJwt(token) {
  try {
    const base64Url = token.split(".")[1];
    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map(function (c) {
          return "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2);
        })
        .join("")
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
}

// 토큰 만료 체크
function isTokenExpired(payload) {
  if (!payload.exp) return true;
  const expiry = payload.exp * 1000; // exp는 초 단위이므로 밀리초로 변환
  return Date.now() >= expiry;
}

// 로그인 상태 표시 함수
function showLoggedInState(userName) {
  const loggedInSection = document.getElementById("loggedInSection");
  const loggedOutSection = document.getElementById("loggedOutSection");
  document.getElementById("userNameSpan").textContent = userName;
  loggedInSection.style.display = "block";
  loggedOutSection.style.display = "none";
}

// 로그아웃 상태 표시 함수
function showLoggedOutState() {
  const loggedInSection = document.getElementById("loggedInSection");
  const loggedOutSection = document.getElementById("loggedOutSection");
  loggedInSection.style.display = "none";
  loggedOutSection.style.display = "block";
}

// 로그인 상태 체크 및 UI 업데이트
async function checkLoginStatus() {
  const token = localStorage.getItem("token");
  if (!token) {
    showLoggedOutState();
    return false;
  }

  try {
    // 서버에 토큰 유효성 검증 요청
    const response = await fetch("/api/auth/status", {
      headers: {
        Authorization: token,
      },
    });

    if (response.ok) {
      const result = await response.json();
      if (result.success) {
        showLoggedInState(result.data.name);
        return true;
      }
    }

    // 토큰이 유효하지 않은 경우
    localStorage.removeItem("token");
    showLoggedOutState();
    return false;
  } catch (error) {
    console.error("인증 확인 실패:", error);
    showLoggedOutState();
    return false;
  }
}

// 로그아웃 처리
function logout() {
  localStorage.removeItem("token");
  window.location.href = "/login";
}

// 보호된 페이지 접근 제어
function requireAuth() {
  const token = localStorage.getItem("token");
  if (!token) {
    window.location.href = "/login";
    return false;
  }
  return true;
}

// 페이지 로드 시 로그인 상태 체크
document.addEventListener("DOMContentLoaded", checkLoginStatus);
