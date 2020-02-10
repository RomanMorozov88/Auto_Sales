// newAd.html ===================================================================

//Заполняет варианты для select`ов.
function loadParts() {
    $.ajax({
        url: './getParts',
        method: 'get',
        complete: function (data) {
            var parts = JSON.parse(data.responseText);
            setSelect(parts.engines, "engine_select");
            setSelect(parts.carBodies, "carbody_select");
            setSelect(parts.transmissions, "transmission_select");
        }
    })
}

//Избавляет от дублирующего кода.
function setSelect(partsList, elementId) {
    var part;
    var result = "<option value='empty'>ничего не выбрано</option>";
    for (var i = 0; i < partsList.length; i++) {
        part = partsList[i].part_name;
        result += "<option value='" + part + "'>" + part + "</option>";
    }
    var bufferSelect = document.getElementById(elementId);
    bufferSelect.innerHTML = result;
}

//Проверяем- заполнены ли все необходимые поля.
function createValidate() {
    var result = true;
    if ($('#shortdesc').val() == '') {
        result = false;
    }
    if ($('#fulldesc').val() == '') {
        result = false;
    }
    return result;
}

//т.к. форма, которую хватаем, имеет enctype="multipart/form-data"
//то вместо .serialize() используем new FormData($('#id_Your_Form')[0])
//и модификаторы: cache: false, contentType: false, processData: false
function addAdvertisement() {
    if (createValidate()) {
        var dataString = new FormData($('#createAdForm')[0]);
        $.ajax({
            url: './create',
            method: 'post',
            cache: false,
            contentType: false,
            processData: false,
            data: dataString,
            complete: function () {
                loadAds();
                window.location.href = "/as/index.html";
            }
        });
    } else {
        alert("Заполните все поля.");
    }
}