function logIn() {
    var username = document.getElementById("login").value;
    var subpage = "";

    if (username == "student") {
        subpage = "student/store.html";
    } else if (username == "mentor") {
        subpage = "mentor/codecoolers.html";
    } else if (username == "creepyguy") {
        subpage = "creepyguy/mentors.html";
    }

    if (subpage.length > 0) {
        window.open("./" + subpage, "_self");
    } else {
        window.alert("Login or password is not correct, try again...");
    }

}

function logIt(name) {
    console.log(name);
}  