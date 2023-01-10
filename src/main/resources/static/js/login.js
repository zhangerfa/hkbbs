let STUID = "stuId";
let PASSWORD = "password";
// 学号验证
checkStuId = function(){
    let stuId = document.getElementById(STUID).value.trim();
    let stuIdError = document.getElementById("stuIdError");
    if (isStuIdValid(stuId)){
        stuIdError.style.display = "none";
        // 判断学号是否注册
        axios.get("/users/isExist?stuId=" + stuId).
        then(function(resp){
            let res = resp.data;
            if (!res.data){
                stuIdError.innerHTML = "学号未注册";
                stuIdError.style.display = "";
                return false;
            }
        })
    }else{
        // 学号不符合规则
        stuIdError.innerHTML = "请输入正确的学号";
        stuIdError.style.display = "";
        return false;
    }
    return true;
}

isStuIdValid = function(stuId){
    let reg = /^[UM][0-9]{9}$/;
    return reg.test(stuId);
}

// 登录：其他信息合理并进行密码验证
function login(){
    // 学号检测
    if (!checkStuId()) return;
    // 验证密码
    let param = new URLSearchParams();
    param.append("stuId", document.getElementById(STUID).value.trim());
    param.append("password", document.getElementById(PASSWORD).value.trim());
    param.append("rememberMe", document.getElementById("remember_me").checked);
    axios.post("/users/login", param).then(function (resp) {
        if (resp.data.data){
            let url = "http://" + document.domain + "/wall";
            document.getElementById("passwordError").style.display="none";
            window.location = url;
        }else{
            alert(resp.data.msg);
            document.getElementById("passwordError").style.display="";
        }
    })
}