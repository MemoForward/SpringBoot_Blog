
/*新增标签, 写繁琐了，这个暂时用不到了*/
function addNewType() {
    var newType = $("#newTag").val();
    $.ajax({
        url: "/admin/addNewType",
        data: 'newType='+newType,
        success:function (response) {
            console.log(response);
            if(response.code === 200){
                window.location.href=response.data + '?sMessage=添加成功';
            }else{
                window.location.href=response.data + '?eMessage=此类别已存在或不合法';
            }
        }
    });
}