
function getMyInfo() {
    $.ajax({
        type: "GET",
        url: "/api/member/info",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem('accessToken')
        },
        success: function (response) {
            $("#result").text("memberId: " + response.memberId + " || memberName: " + response.memberName);
        },
        error: function (error) {
            $("#result").text("Login Failed. Error: " + error.responseText);
        }
    });
}


function getMemberXInfo(memberName) {
    $.ajax({
        type: "GET",
        url: "/api/member/info/" + memberName,
        headers: {
            "Authorization": "Bearer " + localStorage.getItem('accessToken')
        },
        success: function (response) {
            $("#result").text("memberId: " + response.memberId + " || memberName: " + response.memberName);
        },
        error: function (error) {
            $("#result").text("Login Failed. Error: " + error.responseText);
        }
    });
}

document.addEventListener("DOMContentLoaded", function() {
    const memberNameElement = document.getElementById("memberName");
    const memberName = memberNameElement.textContent;

    if (memberName) {
        getMemberXInfo(memberName);
    } else {
        getMyInfo();
    }
});