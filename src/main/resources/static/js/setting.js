function upload() {
    let formData = new FormData();
    // 建立一个upload表单项，值为上传的文件
    formData.append('headerImage', document.getElementById('head-image').files[0]);
    axios({
        method: "post",
        url: "/users/header",
        data: formData,
        onUploadProgress: (progressEvent) => {
            this.showProcess = true
            let process = (progressEvent.loaded / progressEvent.total * 100 | 0)
            this.progress = `上传进度：${process}%`
            if(process === 100){
                document.getElementById("upload-percent").innerText="";
            }else{
                document.getElementById("upload-percent").innerText=this.progress;
            }
        }
    }).then(function (resp){
        alert(resp.data.msg);
        location.reload();
    })
}

changeUsername = function (){
    let data = {
        username: document.getElementById("new-username").value
    }
    axios.put("/users", data).
    then(function (resp){
        location.reload();
        alert(resp.data.msg);
    })
}

changePassword = function (newPassword, oldPassword, confirmPassword){
    if (isPasswordValid(newPassword) && confirmPasswordValid(newPassword, confirmPassword)) {
        let data = new URLSearchParams();
        data.append("newPassword", newPassword);
        data.append("oldPassword", oldPassword);
        console.log(111)
        axios.put("/users", data).
        then(function (resp){
            alert(resp.data.msg);
        })
    }else {
        alert("请输入正确信息")
    }
}

changeHeadLable = function (_this){
    $("#head-image-label")[0].innerHTML = _this.files[0].name;
}