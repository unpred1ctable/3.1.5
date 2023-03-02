// const userListTab = document.getElementById('user-list-tab');
document.addEventListener('DOMContentLoaded', tableBuilder);

async function tableBuilder() {

    const table = document.getElementById('users');
    let users = await getData();

    let userData = '';
    for (let user of users) {
        let roles = [];
        for (let role of user.roles) {
            roles.push(' ' + role.name.toString().replaceAll('ROLE_', ''));
        }
        userData += '<tr>';
        userData += '<td>' + user.id + '</td>';
        userData += '<td>' + user.username + '</td>';
        userData += '<td>' + user.firstName + '</td>';
        userData += '<td>' + user.lastName + '</td>';
        userData += '<td>' + user.age + '</td>';
        userData += '<td>' + user.email + '</td>';
        userData += '<td>' + roles + '</td>';
        userData += '<td>' + '<a type="button" class="btn btn-primary" data-bs-toggle="modal" ' +
            'data-bs-target="#modalFrame" onclick="editUser(' + user.id + ')">Edit</a>' + '</td>';
        userData += '<td>' + '<a type="button" class="btn btn-danger" data-bs-toggle="modal" ' +
            'data-bs-target="#modalFrame" onclick="deleteUser(' + user.id + ')">Delete</a>' + '</td>';
        userData += '</tr>';
    }
    table.innerHTML = userData;
}
async function getData() {
    const url = '/api/users';
    let response = await fetch(url);
    return response.json();
}

