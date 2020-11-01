/* global calculateFeeds */

$(document).ready(function () {
    var company = $('#companyName').val();
    var selectedFormula = $('#formulaName').val();
    var formulas;
    $.ajax({
        type: 'GET',
        url: 'formulas',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        'dataType': 'json',
        async: false,
        success: function (data) {
            formulas = data;
            fillDropDown(formulas, company, selectedFormula);
        },
        error: function () {
            alert("error");
        }
    });

    $("#companyName").change(function () {
        fillDropDown(formulas, company);
    });


    $("#bolus").click(function () {
        fillBolusForm();
    });

    $("#continuous").click(function () {
        fillContinuousForm();
    });
});


function fillBolusForm() {
    var form = $('#rateHoursInput');
    form.empty();
    var row = '<div class="form-group"><label for="rate">Bolus Volume (ml)</label>';
    row += '<input id="rate" class="form-control" name="rate" type="number" value="${rate}" required>';
    row += '</div><div class="form-group"><label for="hours">Boluses per day</label>';
    row += '<input id="hours" class="form-control" name="hours" type="number" value="${hours}" required></div>';
    form.append(row);
    $('#regimen').val("bolus");
    $('#bolus').addClass("active");
    $('#continuous').removeClass("active");
}

function fillContinuousForm() {
    var form = $('#rateHoursInput');
    form.empty();
    var row = '<div class="form-group"><label for="rate">Rate (ml)</label>';
    row += '<input id="rate" class="form-control" name="rate" type="number" value="${rate}" required>';
    row += '</div><div class="form-group"><label for="hours">Hours per day</label>';
    row += '<input id="hours" class="form-control" name="hours" type="number" value="${hours}" required></div>';
    form.append(row);
    $('#regimen').val("continuous");
    $('#continuous').addClass("active");
    $('#bolus').removeClass("active");
}

function fillDropDown(data, company, selectedFormula) {
    company = $('#companyName').val();
    clearDropDown();
    num = 0;
    var dropDown = $('#formulaName');
    $.each(data, function (index, formula) {
        if (formula.company.companyId == company || company === 'default') {
            num + 1;
            var formulaId = formula.formulaId;
            var formulaName = formula.formulaName;
            var option = document.createElement("option");
            option.text = formulaName;
            option.value = formulaId;
            dropDown.append(option, dropDown[num]);
            if(formula.formulaId == selectedFormula){
                option.selected = true;
            }
        }
    });
}

function clearDropDown() {
    $('#formulaName').empty();
}
