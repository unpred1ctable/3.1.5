document.addEventListener('DOMContentLoaded', handleUserPageLoader);


async function getData() {
    const url = 'api/user';
    let response = await fetch(url);
    return response.json();
}
async function buildPage(user, table) {
    let roles = [];
    for (let role of user.roles) {
        roles.push(' ' + role.name.toString().replaceAll('ROLE_', ''));
    }
    let tableRow = document.createElement('tr');
    tableRow.innerHTML = '<td>' + user.id + '</td>' +
        '<td>' + user.username + '</td>' +
        '<td>' + user.firstName + '</td>' +
        '<td>' + user.lastName + '</td>' +
        '<td>' + user.age + '</td>' +
        '<td>' + user.email + '</td>' +
        '<td>' + roles + '</td>';
    table.appendChild(tableRow);
}
async function handleUserPageLoader(event) {
    event.preventDefault();
    let table = document.getElementById('user-info');
    let user = await getData();
    await buildPage(user, table);
}