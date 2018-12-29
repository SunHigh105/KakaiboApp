//   $('#myTab a').on('click', function (e) {
//     e.preventDefault()
//     $(this).tab('show')
//   })

//alert("custom.jsだよ！");

function formCheck(){
    var date = document.getElementById("date").value;
    var cost = document.getElementById("cost").value;
    var category = document.getElementById("category").value;
    var memo = document.getElementById("memo").value;
    var submitButton = document.getElementById("submitButton");

    if(date == "" || cost == "" || category == "選択してください"){
        submitButton.disabled = 1;
    }else{
        submitButton.disabled = 0;
    }
}