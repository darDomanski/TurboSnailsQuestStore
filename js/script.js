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

function fund() {
    var actualPrice = parseInt(document.getElementById("item_price").innerHTML);
    var contribution = parseInt(document.getElementById("fund_input").value);

    actualPrice = actualPrice - contribution;

    if (actualPrice < 0) {
        document.getElementById("item_price").innerHTML = 0;
    } else {
        document.getElementById("item_price").innerHTML = actualPrice;
    }
}