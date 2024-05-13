<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
 <%@ include file="NavBar.jsp" %> <!-- 네비게이션 바를 포함 -->
<title>Join</title>
 	<link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">

<script>
function doCheck() { // 전송하기 버튼 누르면 유효성 검사 시작 함수
    if (checkId() == false) {
        return;
    }
    if (checkPw() == false) {
        return;
    }
    if (checkSecondPw() == false) {
        return;
    }
    if (checkEmail() == false) {
        return;
    }
    if (checkHobby() == false) {
        return;
    }
    if (checkIntro() == false) {
        return;
    }
}

function checkLen(element) { // 입력 길이 확인 함수 
    var len = element.length;

    if (len < 4 || len > 13) {
        alert("4자리 이상 12자리 이하로 입력해주세요. ");
    }

    // document.getElementById("form").reset();
    return len;
}

function checkCode(element, len) { // 숫자(48~57), 영문 대(65~90)소문자(97~122) 검사 

    for (var i = 0; i < len; i++) {
        var temp = element.charCodeAt(i);
        //console.log("아스키코드 값은"+temp);
        if (temp >= 48 && temp <= 57) {
            return true;
        } else if (temp >= 65 && temp <= 90) {
            return true;
        } else if (temp >= 97 && temp <= 122) {
            return true;
        } else
            return false;
    }

}

function checkId() { // 아이디 유효성 검사 
    var inputId = document.getElementById("id").value;
    var lenId = checkLen(inputId);
    //console.log("아이디의 길이는"+lenId);
    if (checkCode(inputId, lenId) == false) {
        alert("아이디를 숫자 및 영문 대소문자로 입력해주세요.");
        return false;
    } else {
        //alert("아이디 성공");
        return true;
    }
}

function checkPw() { // 비밀번호 유효성 검사
    var inputPw = document.getElementById("pw").value;
    var lenPw = checkLen(inputPw);
    if (checkCode(inputPw, lenPw) == false) {
        alert("비밀번호를 숫자 및 영문 대소문자로 입력해주세요.");
        return false;
    } else {
        return true;
    }
}

function checkSecondPw(inputPw) { // 비밀번호 확인 유효성 검사 
    var inputPw = document.getElementById("pw").value;
    var inputSecPw = document.getElementById("secondPw").value;
    // console.log(inputPw);
    // console.log(inputSecPw);
    // console.log(inputPw == inputSecPw);
    if (inputPw != inputSecPw) {
        alert("비밀번호가 일치하지 않습니다. ");
        return false;
    } else {
        return true;
    }

}

function checkEmail() { // 메일 유효성 검사 
    var inputEmail = document.getElementById("mail").value;

    var mailArray = inputEmail.split("@", 2);
    if (mailArray.length != 2) {
        alert("올바른 메일 주소를 입력하세요.");
        return false;
    } else {
        var twiceArray = mailArray[1].split(".", 2);
        if (twiceArray.length != 2) {
            alert("올바른 메일 주소를 입력하세요.");
            return false;
        } else {
            return true;
        }
    }
}

function checkIdnum() { // 주민등록번호 유효성 검사 
    var inputIdnum = document.getElementById("Idnum").value;

    var numArray = inputIdnum.split("", 13);
    var sum = 0;

    for (var i = 0; i <= 7; i++) {
        numArray[i] = numArray[i] * (i + 2);
    }

    for (var i = 8; i <= 11; i++) {
        numArray[i] = numArray[i] * (i - 6);
    }

    for (var i = 0; i < 12; i++) {
        sum += numArray[i];
    }
    //console.log((11 - (sum % 11)) + ", " + numArray[12]); // 10, 0
    var temp = 11 - (sum % 11);

    if (temp >= 10) {
        temp = temp - 10;
    }

    if (temp != numArray[12]) {
        alert("올바른 주민등록번호를 입력하세요.");
        return false;
    } else {
        showBirth(inputIdnum, numArray);
        return true;
    }

}

function showBirth(inputIdnum, numArray) { // 생년월일 보여주기
    var inputIdnum = document.getElementById("Idnum").value;
    var numArray = inputIdnum.split("", 13);

    if (numArray[7] == 3 || numArray[7] == 4) {
        var y = numArray.slice(0, 1);
        var year = "20" + y.join("");
        var month = numArray[2] + numArray[3];
        var day = numArray[4] + numArray[5];

    } else {
        var y = numArray.slice(0, 2);
        var year = "19" + y.join("");
        var month = numArray[2] + numArray[3];
        var day = numArray[4] + numArray[5];
    }

    document.getElementById("year").value = year;
    document.getElementById("month").value = month;
    document.getElementById("day").value = day;
}

function checkHobby() { // 관심분야 1개 이상 체크 확인하기 
    var hobby = document.getElementsByName("hobby");
    var cnt = 0;
    var hobbyArr = new Array();

    for (var i = 0; i < hobby.length; i++) { // 배열의 길이만큼 반복문
        if (hobby[i].checked) {
            hobbyArr.push(hobby[i].value);
            cnt++;
        }
    }

    if (cnt == 0) {
        alert("관심분야를 1개 이상 선택해주세요.");
        return false;
    } else {
        return true;
    }

}

function checkIntro() { // 자기소개 글자 수 10자 이상 체크 
    var inputIntro = document.getElementById("intro").value;
    console.log(inputIntro.length);
    if (inputIntro.length < 10) {
        alert("자기소개 10자 이상 적어주세요. ");
        return false;
    } else {
        return true;
    }
}
</script>
</head>
<body>

    <style>
        #title {
            text-align: center;
            font-size: 15pt;
            font-weight: bold;
            text-decoration: #380B61;
        }

        #self {
            text-align: center;
        }

        #image {
            height: 150px;
            text-align: center;
        }

        img {
            height: 100%;
        }

        input {
            border-style: solid;
            border-color: #BDBDBD;
        }

        table {
            background-color: #FAFAFA;
        }

        .subtitle {
            text-decoration: #380B61;
            font-weight: bold;
            text-align: center;
        }

        .font {
            font-family: "Gowun Dodum", sans-serif;
            font-weight: 400;
            font-style: normal;
        }
    </style>
	
    <form id="form" action="./JoinUser.lo" method="post">
        <table class="font" width="800 height=" 700" border="1" style="border-collapse: collapse" bordercolor="#A4A4A4"
            align="center">
            <tr id="title" class="font">
                <td colspan="3" , bgcolor="#E6E0F8" height="40">회원 기본 정보</td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">아이디</td>
                <td colspan="2"><input type="text" id="Id" name="id" size"20" maxlength="14"> 4~12자의 영문 대소문자와 숫자로만 입력</td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">비밀번호</td>
                <td colspan="2"><input type="password" id="pw" name="pw" size"20" maxlength="14"> 4~12자의 영문 대소문자와 숫자로만 입력</td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">비밀번호 확인</td>
                <td colspan="2"><input type="password" id="secondPw" name="secondPw" size"20" maxlength="14"></td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">메일주소</td>
                <td colspan="2"><input type="text" id="mail" name="mail" size"30" maxlength="20"> 예)id@domain.com</td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">이름</td>
                <td colspan="2"><input type="text" name="name" size"30" maxlength="20"></td>
            </tr>

            <tr id="title">
                <td colspan="3" , bgcolor="#E6E0F8" height="40">개인 신상 정보</td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">주민등록번호</td>
                <td colspan="2"><input type="text" id="Idnum" name="IDnum" size"30" maxlength="20" onkeyup="checkIdnum();"> 예)123456-1234567</td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">생일</td>
                <td colspan="2"><input type="text" id="year" name="birth" size="5" maxlength="4" readonly>년
                    <input type="text" id="month" name="birth" size="5" maxlength="4" readonly>월
                    <input type="text" id="day" name="birth" size="5" maxlength="4" readonly>일
               
                </td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">관심분야</td>
                <td colspan="2"><input type="checkbox" name="hobby" value="컴퓨터">컴퓨터
                    <input type="checkbox" name="hobby" value="인터넷">인터넷
                    <input type="checkbox" name="hobby" value="여행">여행
                    <input type="checkbox" name="hobby" value="영화감상">영화감상
                    <input type="checkbox" name="hobby" value="음악감상">음악감상
                </td>
            </tr>

            <tr>
                <td class="subtitle" bgcolor="#E6E6E6">자기소개</td>
                <td id="self"><textarea id="intro" name="introduction" cols="60" rows="7"></textarea></td>
                
            </tr>

        </table>

        <p style align="center">
            <input type="submit" value="전송하기" onclick="doCheck();">
            <input type="reset" value="다시입력">
        </p>
    </form>
   
</body>


</body>
</html>