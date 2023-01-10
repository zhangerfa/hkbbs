// function like(btn, entityType, entityId, entityUserId, postId) {
//     $.post(
//         CONTEXT_PATH + "/like",
//         {"entityType":entityType,"entityId":entityId,"entityUserId":entityUserId,"postId":postId},
//         function(data) {
//             data = $.parseJSON(data);
//             if(data.code == 0) {
//                 $(btn).children("i").text(data.likeCount);
//                 $(btn).children("b").text(data.likeStatus==1?'已赞':"赞");
//             } else {
//                 alert(data.msg);
//             }
//         }
//     );
// }

// 删除卡片
deleteCard = function (path){
    axios.delete(path).
    then(function (resp){
        alert(resp.data.msg)
        if (resp.data.data){
            // 如果卡片详情页面删除卡片，跳转到卡片墙，否则刷新页面
            if (location.href.includes("/details") && path.includes("/cards")){
                location.replace("/wall")
            }else{
                location.reload();
            }
        }
    });
}

// 发布卡片
postCard = function (){
    let param = {
        "content": document.getElementById("message-text").value,
        "title": document.getElementById("recipient-name").value
    };
    // 发布卡片前检测卡片是否为空
    if (param.title.length === 0 || param.content.length === 0){
        document.getElementById("hintModal").style.color="red";
        document.getElementById("hintBody").innerText="请输入要发送的内容";
        $('#hintModal').modal('show');
        return
    }
    axios.post("/cards", param).
    then(function (resp) {
        document.getElementById("hintModal").style.color="black";
        document.getElementById("hintBody").innerText="发帖成功";
        $('#hintModal').modal('show');
        $('#publishModal').modal('hide');
        // 发布成功后刷新页面
        location.reload();
    })
}

function whenPublish(){
    document.getElementById("message-text").value = "";
    document.getElementById("recipient-name").value = "";
    $('#publishModal').modal('show');
}