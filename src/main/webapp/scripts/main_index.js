//Глобальная переменная с основной информацией о пользователе.
var generalUser = null;

//Функции, общие для входа\регистрации.========================================

//Проверяем- заполнены ли все необходимые поля.
function validate() {
    var result = true;
    if ($('#login').val() == '') {
        result = false;
    }
    if ($('#password').val() == '') {
        result = false;
    }
    return result;
}

//Устанавливаем данные об вошедшем юезере (или null если просто гость)
//для всяких классных штук навроде другой кнопки в верхней части
// и удаления\изменения статуса заявки.
function setGlobalUser() {
    $.ajax({
        url: './gInfo',
        method: 'get',
        complete: function (data) {
            generalUser = JSON.parse(data.responseText);
            setUpperButtons();
        }
    })
}

// index.html ====================================================================

//Устанавливаем кнопки в верхнем меню.
function setUpperButtons() {
    //строка для левой части
    var result1 = '';
    //строка для правой части
    var result2 = '';
    if (generalUser != null) {
        result1 = "<input class='left_btns' value='Создать объявление' type='button' "
            + "onclick='location.href=\"/as/newAd.html\"'/>"
            + "<input type='checkbox' id='galka' onchange='loadAds()'>Показать мои объявления";
        result2 = "<input class='right_btns' value='Выход' type='button' onclick='exitFunction()'/>";
    } else {
        result1 = '';
        result2 = "<input class='right_btns' value='Регистрация' type='button' "
            + "onclick='location.href=\"/as/signup.html\"'/>"
            + "<input class='right_btns' value='Вход' type='button' onclick='location.href=\"/as/login.html\"'/>";
    }
    var buffer = document.getElementById('choice_btn_box');
    buffer.innerHTML = result1;
    buffer = document.getElementById('inner_btn_box');
    buffer.innerHTML = result2;
}

//Получаем объявления из БД.
function loadAds() {
    var dataString = $('#filterForm').serialize();
    var toSend = false;
    if (generalUser != null) {
        toSend = document.getElementById('galka').checked;
    }
    $.ajax({
        url: './getads',
        method: 'get',
        data: [dataString, $.param({flag: toSend})].join('&'),
        complete: function (data) {
            var list = JSON.parse(data.responseText);
            if (list.length > 0) {
                writeTable(list);
            } else {
                var result = "<p><h2 style='text-align: center'>Не найдено ни одного объявления.</h2></p>";
                var table = document.getElementById('ad_table');
                table.innerHTML = result;
            }
        }
    })
}

//Записываем объявления в таблицу.
function writeTable(list) {
    var result = "<table>"
        + "<thead>"
        + "<tr>"
        + "<th>Фото</th>"
        + "<th>Описание</th>"
        + "<th>Продавец</th>"
        + "<th>Статус</th>"
        + "</tr>"
        + "</thead>"
        + "<tbody>";
    for (var i = 0; i < list.length; i++) {
        var bufferAd = list[i];
        var trsAdvertisement = JSON.stringify(bufferAd);
        result += "<tr data-adv='" + trsAdvertisement + "'>"
            + "<td><img src='images/" + bufferAd.adPhoto
            + "' alt='Не удалось загрузить изображение.'"
            + "width='150px'"
            + "height='100px'/>"
            + "</td>"
            + "<td>" + bufferAd.adShortName + "</td>"
            + "<td>" + bufferAd.adCreator.ownerName + "</td>";
        if (list[i].adStatus) {
            result += "<td>Не продано</td>"
        } else {
            result += "<td>Продано</td>"
        }
        result += "</tr>";
    }
    result += "</tbody>"
        + "</table>";
    var table = document.getElementById('ad_table');
    table.innerHTML = result;
    selected();
}

// Получаем данные из строки таблицы по которой кликнули.
function selected() {
    $('tr').on('click', function () {
        var advertisement = $(this).data('adv');
        var result = "<p>Дата создания: " + advertisement.adTime.dayOfMonth + "."
            + advertisement.adTime.monthValue + "."
            + advertisement.adTime.year
            + "</p>"
            + "<img src=\"images/" + advertisement.adPhoto
            + "\"alt=\"Не удалось загрузить изображение.\""
            + "width='100%'/>";
        var buffer = document.getElementById('selected_img');
        buffer.innerHTML = result;

        result = "<p>" + advertisement.adDescription + "</br></p>";
        buffer = document.getElementById('selected_dsc');
        buffer.innerHTML = result;

        var car = advertisement.adCar;
        var engine = car.carEng;
        var carbody = car.carBody;
        var transmission = car.carTrs;
        result = "<p>";
        if (engine != null) {
            result += "Двигатель: " + engine.part_name + "</br>";
        }
        if (carbody != null) {
            result += "Кузов: " + carbody.part_name + "</br>";
        }
        if (transmission != null) {
            result += "Трансмиссия: " + transmission.part_name + "</br>";
        }
        result += "</p>";
        buffer = document.getElementById('selected_parts');
        buffer.innerHTML = result;

        if (generalUser.ownerRole.role_main && generalUser.ownerId == advertisement.adCreator.ownerId) {
            result = "<input class='left_btns' value='Изменить статус' type='button' "
                + "onclick='changeStatus(" + advertisement.adStatus + ","
                + advertisement.adId + ")'/>";
        }
        buffer = document.getElementById('selected_btns');
        buffer.innerHTML = result;
    });
}

function changeStatus(status, id) {
    $.ajax({
        url: './status',
        method: 'post',
        data: {status: status, adId: id},
        complete: function () {
            window.location.reload();
        }
    })
}

//Отправляем запрос на обнуление сессии
// и обновляем страницу.
function exitFunction() {
    $.ajax({
        url: './exit',
        method: 'post',
        complete: function () {
            window.location.reload();
        }
    })
}

//Заполняет варианты для select`ов фильтров.
function loadMainParts() {
    $.ajax({
        url: './getParts',
        method: 'get',
        complete: function (data) {
            var parts = JSON.parse(data.responseText);
            setKeySelect(parts, "outer_select");
            loadInnerOptions(parts);
        }
    })
}

//Устанавливаем в основной пункт фильтра заглавные параметры.
function setKeySelect(list, elementId) {
    var keyResult;
    var result = "<option value='empty'>ничего не выбрано</option>";
    var keyList = Object.keys(list);
    result += "<option value='period'>время создания</option>";
    for (var i = 0; i < keyList.length; i++) {
        keyResult = keyList[i];
        result += "<option value='" + keyResult + "'>" + keyResult + "</option>";
    }
    var bufferSelect = document.getElementById(elementId);
    bufferSelect.innerHTML = result;
}

//Получаем варианты для выбранного пунка фильтрации.
function loadInnerOptions(list) {
    $('#outer_select').change(function () {
        var key = $(this).val();
        var result = '';
        if (key == 'period') {
            result = "<option value='-1'>ничего не выбрано</option>";
            result += "<option value='1'>за последний день</option>";
            result += "<option value='7'>за последнюю неделю</option>";
            var bufferSelect = document.getElementById("inner_select");
            bufferSelect.innerHTML = result;
        } else if (key == 'empty') {
            result = "<option value='empty'>ничего не выбрано</option>";
            var bufferSelect = document.getElementById("inner_select");
            bufferSelect.innerHTML = result;
        } else {
            var resultList = list[key];
            setSelect(resultList, "inner_select");
        }
    });
}