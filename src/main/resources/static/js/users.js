let baseUrl = 'http://localhost:8080/api/';


fetch(baseUrl + 'users', {
    method: 'GET',
})
    .then(async function (res) {
        if (res.ok) {
            let jsonUsers = await res.text();
            let users = JSON.parse(jsonUsers);
            console.log(users);
        }
    })
    .catch(function (err) {
        console.log(err);
    });