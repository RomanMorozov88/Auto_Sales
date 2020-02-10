// signup.html ===================================================================

//Получаем роли.
function loadRoles() {
    $.ajax({
        url: './sUp',
        method: 'get',
        complete: function (data) {
            var roles = JSON.parse(data.responseText);
            var result = '';
            for (var i = 0; i < roles.length; i++) {
                var buffer = roles[i].role_name;
                result += "<option value='" + buffer + "'>" + buffer + "</option>";
            }
            var roleSelect = document.getElementById("role_select");
            roleSelect.innerHTML = result;
        }
    })
}

//Отправляем логин\пароль для входа.
function addUser() {
    if (validate()) {
        var dataString = $('#signUpForm').serialize();
        $.ajax({
            url: './sUp',
            method: 'post',
            data: dataString,
            complete: function () {
                setGlobalUser();
                window.location.reload();
            }
        });
    } else {
        alert("Заполните все поля.");
    }
}