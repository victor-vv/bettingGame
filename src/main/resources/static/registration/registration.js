$(document).ready(function() {
    $("#register").click(function() {
        debugger;
        var name = $("#name").val();
        var password = $("#password").val();
        var cpassword = $("#cpassword").val();
        if (name === ''  || password === '' || cpassword === '') {
            alert("Пожалуйста, заполните все поля");
        } else if (!(password).match(cpassword)) {
            alert("Введенные пароли не совпадают");
        } else {
            // $.post("register.php", {
            //     name1: name,
            //     password1: password
            // }, function(data) {
            //     if (data === 'You have Successfully Registered.....') {
            //         $("form")[0].reset();
            //     }
            //     alert(data);
            // });
            var userDto = {
                "username": name,
                "password": password
            };
            $.ajax({
                type: "POST",
                url: "v1/users",
                contentType: "application/json",
                data: JSON.stringify(userDto),
                success: function () {
                    console.log("Successful sign-in!");
                    $("form")[0].reset();
                    alert("Вы успешно зарегистрировались!");
                },
                failure: function () {
                    console.log("Not successful sign-in!");
                    alert("Что-то пошло не так, попробуйте еще раз");
                }
            })
        }
    });
});
