/* global calculateFeeds */
var regimen = "bolus";
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
        regimen = "bolus";
        $('#bolus').addClass("active");
        $('#continuous').removeClass("active");
    });

    $("#continuous").click(function () {
        fillContinuousForm();
        regimen = "continuous";
        $('#continuous').addClass("active");
        $('#bolus').removeClass("active");
    });



    $('#submit-button').click(function (event) {
        $('#errorMessage').empty();
        var formulaId = $('#formulaName').val();
        var rate = $('#rate').val();
        var hours = $('#hours').val();
        var volume = rate * hours;
        if(rate == "" || hours == ""){
            $('#errorMessage').append("Please enter valid number");
        } else {
            $.ajax({
                type: 'POST',
                url: 'calculateFeeds',
                data: JSON.stringify({
                    formulaId: formulaId,
                    rate: rate,
                    hours: hours
                }),
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                'dataType': 'json',
                success: function (data) {
                    fillResultsTable(data, volume, rate, hours);
                    $('#errorMessage').empty();
                },
                error: function () {
                    alert("error");
                }
            });
        }
    });
    
    
    $('#copy-btn').click(function (event) {
        var copyText = $('#textToCopy');
        copyText.select();
        document.execCommand("Copy");
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
    var row = '<div class="form-group"><label for="rate">Rate (ml/hr)</label>';
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
            if (formula.formulaId == selectedFormula) {
                option.selected = true;
            }
        }
    });
}

function clearDropDown() {
    $('#formulaName').empty();
}


function fillResultsTable(data, volume, rate, hours) {
    var resultsTable = $('#resultsTable');
    resultsTable.empty();
    $('#regimenHeading').empty();
    $('#volumeHeading').empty();
    var formulaId = data.formulaId;
    var formulaName = data.formulaName;
    var calories = Math.round(Number(data.concentration));
    var protein = data.protein;
    var fat = data.fat;
    var carbs = data.carbohydrate;
    var fiber = data.fiber;
    var water = data.water;
    var phos = data.phosphorus;
    var potassium = data.potassium;
    var mlToRDI = data.mlForRdi;
    var row = '<thead><tr>';
    row += '<th class="col-sm-8"><h4>Calories</h4></td><th class="col-sm-4"><h5>' + calories + '</h5></th></tr></thead>';
    row += '<tbody><tr><td>Protein</td><td>' + protein + 'g </td></tr>';
    row += '<tr><td>Fat</td><td>' + fat + 'g </td></tr>';
    row += '<tr><td>Carbohydrates</td><td>' + carbs + 'g </td></tr>';
    row += '<tr><td>Fiber</td><td>' + fiber + 'g </td></tr>';
    row += '<tr><td>Water</td><td>' + water + 'ml </td></tr>';
    row += '<tr><td>Phosphorus</td><td>' + phos + 'g </td></tr>';
    row += '<tr><td>Potassium</td><td>' + potassium + 'g </td></tr>';
    row += '<tr><td>ml required to meet 100% of RDIs</td><td>' + mlToRDI + 'ml </td></tr></tbody>';
    resultsTable.append(row);
    $('#volumeHeading').append('Total daily volume: ' + volume + 'ml');
    
    $('.copy').show();
    if (regimen === "bolus") {
        $('#regimenHeading').append(rate + 'ml ' + formulaName + ' ' + hours + 'x per day');
        $('#textToCopy').val(rate + 'ml bolus of ' + formulaName + '' + hours + 'x/day to provide ' + calories + 
                'kcals, ' + protein + 'g protein, and ' + water + 'ml free water');
    }
    if (regimen === "continuous") {
        $('#regimenHeading').append(formulaName + ' @ ' + rate + ' ml/hr x ' + hours + 'hours');
        $('#textToCopy').val(formulaName + ' @ ' + rate + 'ml/hr to provide ' + calories + 'kcals, ' + 
                protein + 'g protein, and ' + water + 'ml free water x ' + hours + ' hours');

    }
}
;
