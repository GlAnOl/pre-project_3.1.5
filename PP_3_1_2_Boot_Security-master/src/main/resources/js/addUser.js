





////Я убрал обращение к переменным ROLE_USER и ROLE_ADMIN
///Сделал обращение к базе данных, чтобы из нее доставались все роли,
//Что позволит добавлять еще роли в роект и не менять при этом фронт



async function getRoles() {
    const response = await fetch(`/api/admin/role`);
    return await response.json();
}


async function createNewUser(user) {
    await fetch("/api/admin",
        {method: 'POST', headers: {'Content-Type': 'application/json'}, body: JSON.stringify(user)})
}

async function addNewUserForm() {
    const newUserForm = document.getElementById("newUser");

    newUserForm.addEventListener('submit', async function (event) {
        event.preventDefault();

        const name = newUserForm.querySelector("#firstName").value.trim();
        const surname = newUserForm.querySelector("#lastName").value.trim();
        const age = newUserForm.querySelector("#age").value.trim();
        const email = newUserForm.querySelector("#email").value.trim();
        const password = newUserForm.querySelector("#password").value.trim();

        const rolesSelected = document.getElementById("roles");



        let allRole = await getRoles();
        let AllRoles = {};
        for (let x of allRole) {
            AllRoles[x.roleName] = x.id;
        }
        let roles = [];
        for (let option of rolesSelected.selectedOptions) {
            if (Object.keys(AllRoles).indexOf(option.value) != -1) {
                roles.push({roleId: AllRoles[option.value], roleName: option.value});
            }
        }


        const newUserData = {
            name: name,
            surname: surname,
            age: age,
            email: email,
            password: password,
            roles: roles
        };

        await createNewUser(newUserData);
        newUserForm.reset();

        document.querySelector('a#show-users-table').click();
        await fillTableOfAllUsers();
    });
}