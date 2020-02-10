// login.html ====================================================================

//Отправляем логин\пароль для входа.
function entryUser() {
    if (validate()) {
        var dataString = $('#signInForm').serialize();
        $.ajax({
            url: './sIn',
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
