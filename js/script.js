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
    var actualPrice = document.getElementById("item_price").innerHTML;
    var actualPriceInt = parseInt(actualPrice);
    var contribution = parseInt(document.getElementById("fund_input").value);

    actualPriceInt = actualPriceInt - contribution;

    logIt(contribution);

    if (actualPriceInt < 0) {
        document.getElementById("item_price").innerHTML = 0;
    } else {
        document.getElementById("item_price").innerHTML = actualPriceInt;
    }
    logIt(actualPriceInt);

    if (document.getElementById("item_price").innerHTML == 0) {
        document.getElementById("fund_button").disabled = true;
    }

}