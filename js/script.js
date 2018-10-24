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

function fund(item_number) {
    var actualPrice = document.getElementById("item_price" + item_number).innerHTML;
    var actualPriceInt = parseInt(actualPrice);
    var contribution = parseInt(document.getElementById("fund_input" + item_number).value);

    actualPriceInt = actualPriceInt - contribution;

    if (actualPriceInt < 0) {
        document.getElementById("item_price" + item_number).innerHTML = 0;
    } else {
        document.getElementById("item_price" + item_number).innerHTML = actualPriceInt;
    }

    if (document.getElementById("item_price" + item_number).innerHTML == 0) {
        document.getElementById("fund_button" + item_number).disabled = true;
    }

}