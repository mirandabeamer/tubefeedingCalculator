/* global calculateFeeds */

$(document).ready(function () {

    $("#companyName").change(function () {
        var company = $('#companyName').val();


        $.ajax({
            type: 'POST',
            url: 'getCompany',
            data: JSON.stringify({
                company: company
            }),
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            'dataType': 'json',
            success: function (data) {
                fillDropDown(data);
            },
            error: function () {
                alert("error");
            }
        });
    });
});

function fillDropDown(data){
    clearDropDown();
    num = 0;
    var dropDown = $('#formulaName');
    $.each(data, function (index, formula) {
        num+1; 
        var formulaId = formula.formulaId;
        var formulaName = formula.formulaName;
        var option = document.createElement("option");
        option.text = formulaName;
        option.value= formulaId;
        dropDown.append(option, dropDown[num]);
    });
}

function clearDropDown(){
    $('#formulaName').empty();
}
