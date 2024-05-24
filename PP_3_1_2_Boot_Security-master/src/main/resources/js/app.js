document.addEventListener('DOMContentLoaded', async function () {
    await showUserEmailOnNavbar()
    await fillTableOfAllUsers();
    await fillTableAboutCurrentUser();
    await addNewUserForm();
    await DeleteModalHandler();
    await EditModalHandler();
});

async function showUserEmailOnNavbar() {
    const currentUserEmailNavbar = document.getElementById("currentUserEmailNavbar")
    const currentUser = await dataAboutCurrentUser();
    currentUserEmailNavbar.innerHTML =
                `<strong>${currentUser.email}</strong>
                 with roles: 
                 ${currentUser.roles.map(role => role.roleNameWithoutRole).join(' ')}`;
}
