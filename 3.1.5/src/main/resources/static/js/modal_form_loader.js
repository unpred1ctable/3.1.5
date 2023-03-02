const modalForm = document.getElementById('modalForm');

async function getUser(id) {
    const url = 'api/user/' + id;
    let response = await fetch(url);
    return response.json();
}
async function editUser(id) {

    let data = await getUser(id);

    const { elements } = modalForm;

    const editTitle = document.getElementById('modal-title');
    editTitle.innerHTML = 'Edit user';
    const editButton = document.getElementById('modal-submit');
    editButton.innerHTML = 'Edit';
    modalForm.setAttribute('action', 'api/user/' + id);
    modalForm.addEventListener("submit", patchData);

    for (const [ key, value ] of Object.entries(data) ) {
        const field = elements.namedItem(key);
        if (key !== 'id' && field !== null) {
            field.removeAttribute('disabled');
        }
        if (key !== 'password') {
            field && (field.value = value);
        }
        if (key === 'roles') {
            let options = Array.from(field);
            for (let role of value) {
                for (let option of options) {
                    if (option.value === role.name) {
                        option.selected = true;
                    }
                }
            }
        }
    }
}

async function deleteUser(id) {

    let data = await getUser(id);
    const { elements } = modalForm;

    const deleteTitle = document.getElementById('modal-title');
    deleteTitle.innerHTML = 'Delete user';
    const deleteButton = document.getElementById('modal-submit');
    deleteButton.innerHTML = 'Delete';


    modalForm.setAttribute('action', 'api/user/' + id);
    modalForm.addEventListener("submit", deleteData);

    let fields = modalForm.elements;
    for (let field of fields) {
        if (field !== document.getElementById('modal-submit')) {
            field.setAttribute('disabled', 'disabled');
        }
    }


    for (const [ key, value ] of Object.entries(data) ) {
        const field = elements.namedItem(key);
        if (key !== 'password') {
            field && (field.value = value);
        }
        if (key === 'roles') {
            let options = Array.from(field);
            for (let role of value) {
                for (let option of options) {
                    if (option.value === role.name) {
                        option.selected = true;
                    }
                }
            }
        }
    }
}

async function patchData(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;

    const formData = new FormData(form);
    let object = {};
    formData.forEach((value, key) => {
        if (key !== 'roles') {
            object[key] = value;
        } else {
            if (!Array.isArray(object[key])) {
                object[key] = [];
                object[key].push(value);
            } else {
                object[key].push(value);
            }
        }
    });

    let json = JSON.stringify(object);

    const fetchOptions = {
        method: "PATCH",
        headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
        },
        body: json,
    };

    await fetch(url, fetchOptions);



    form.reset();
    $('#close').click();
    await tableBuilder();
}

async function deleteData(event) {
    event.preventDefault();

    const form = event.currentTarget;
    const url = form.action;


    const fetchOptions = {
        method: "DELETE",
    };

    await fetch(url, fetchOptions);

    form.reset();
    $('#close').click();
    await tableBuilder();
}