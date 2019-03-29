let baseUrl = 'http://localhost:8080/api/';
let formBody = [];


function convertJsonToFormUrlEncodedFormat(user) {
    for (let p in user) {

        let encodedKey = encodeURIComponent(p);
        let encodedValue = encodeURIComponent(user[p]);
        formBody.push(encodedKey + "=" + encodedValue);
    }
}


function login() {

    let username = document.getElementById('uname').value;
    let password = document.getElementById('psw').value;

    let user = {
        'username': username,
        'password': password
    };

    convertJsonToFormUrlEncodedFormat(user);

    fetch(
        baseUrl + 'login',
        {
            method: 'POST',
            body: formBody.join('&'),
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            }
        }
    ).then(async function (res) {
        if (res.ok) {
            let token = await res.text();
            localStorage.setItem('x-auth-token', token);// add token to local storage for further requests
            window.location.href = 'http://localhost:8080'
        }
    })
        .catch(function (err) {
            console.log(err);
        });
}
