function signIn(){
    // 学号是否合理且注册、两次密码是否一致、验证码是否正确
    if (!isStuIdValid() || !confirmPasswordValid()){
        alert("请输入正确信息");
        return;
    }
    let param = new URLSearchParams();
    param.append("stuId", document.getElementById("stuId").value.trim());
    param.append("password", document.getElementById("password").value.trim());
    param.append("username", document.getElementById("username").value.trim())
    param.append("cod   e", document.getElementById("code").value.trim())
    axios.post("/users/register", param).then(function (resp) {
        // 弹出后端响应信息
        alert(resp.data.msg);
        if (resp.data.data){
            // 注册成功跳转到登录页面
            let url = "http://" + document.domain + "/login";
            window.location = url;
        }
    })
}

// 学号合法验证
isStuIdValid = function(){
    let stuId = document.getElementById("stuId").value.trim().toUpperCase();
    let reg = /^[UMD][0-9]{9}$/;

    let stuIdError = document.getElementById("stuIdError");
    let res;
    if (reg.test(stuId)){
        stuIdError.style.display = "none";
        // 判断学号是否注册
        res = axios.get("/users/isExist?stuId=" + stuId).
        then(function(resp){
            if (resp.data.data){
                stuIdError.innerHTML = "学号已注册";
                stuIdError.style.display = "";
                return false;
            }
            return true;
        })
    }else{
        stuIdError.style.display = "";
        return false;
    }
    return res;
}

// 密码验证
isPasswordValid = function(){
    let password = document.getElementById("password").value.trim();

    let reg = /^[A-Za-z0-9]{6,16}$/;
    let passwordError = document.getElementById("passwordError");
    if (reg.test(password)){
        passwordError.style.display = "none";
        return true;
    }else{
        passwordError.style.display = "";
        return false;
    }
}

// 再次输入密码验证
function confirmPasswordValid(){
    let password = document.getElementById("password").value.trim();
    let confirmPassword = document.getElementById("confirmPassword").value.trim();
    let confirmPasswordError = document.getElementById("confirmPasswordError");
    if (password == confirmPassword){
        confirmPasswordError.style.display = "none";
        return true;
    }else {
        confirmPasswordError.style.display = "";
        return false;
    }
}


sendCode = function (){
    // 设置60s后才能再次发送验证码的定时器
    let btn = document.getElementById("code_button");
    let code_prompt = document.getElementById("code_prompt")
    let TIME = 60; // 再次发送验证码间隔时间
    let time = TIME;
    // 学号是否合理且注册、两次密码是否一致
    if (isStuIdValid() && confirmPasswordValid()){
        btn.disabled = true;
        // 添加定时器，TIME s后能够重新获取验证码
        let timer = setInterval(function () {
            if (time === 0) {
                time = TIME;
                btn.innerHTML = '获取验证码';
                // 清除定时器
                clearInterval(timer);
                timer = null;
                btn.disabled = false;
                code_prompt.style.display = "none";
            }else{
                btn.innerHTML = time + "s后重新获取";
                time--;
            }
        }, 1000);
        // 发送验证码
        let param = new URLSearchParams();
        param.append("stuId", document.getElementById("stuId").value.trim());
        axios.post("/users/sendCode", param);
        code_prompt.style.display = "";
    }
}