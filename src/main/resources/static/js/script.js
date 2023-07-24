// loginForm이 submit되었을 때
$("#loginForm").submit(function (event) {
    // 기본 동작 (페이지 새로고침)을 막음
    event.preventDefault();

    // 사용자가 입력한 값을 가져옴
    var username = $("#username").val();
    var password = $("#password").val();

    // 서버에 보낼 데이터
    var data = {
        "memberName": username,
        "password": password
    };

    // 서버에 로그인 요청 보내기
    sendAjaxRequest("POST", "/api/auth/login", data, handleLoginSuccess, handleLoginError);
});

// Ajax 요청 보내는 함수
function sendAjaxRequest(type, url, data, successCallback, errorCallback) {
    $.ajax({
        type: type,
        url: url,
        data: JSON.stringify(data),
        contentType: "application/json",
        success: successCallback,
        error: errorCallback
    });
}

// 로그인 성공시 처리
function handleLoginSuccess(response) {
    $("#result").text("Login Successful! Token: " + response.accessToken);
}

// 로그인 실패시 처리
function handleLoginError(error) {
    $("#result").text("Login Failed. Error: " + error.responseText);
}