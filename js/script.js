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

function getCCAmount() {
    var ccAmount = document.getElementById("amount_of_coolcoins");
    var actualCCAmount = document.getElementById("actual_cc_amount");

    actualCCAmount.innerHTML = ccAmount.innerHTML;
}

function determineNextLvlMin(thisLvl) {
    var nextLvl = 1 + thisLvl;

    var thisLvlUpperLimit = document.getElementById("lvl" + thisLvl + "_limit");
    var nextLvlMin = document.getElementById("lvl" + nextLvl + "_min");

    nextLvlMin.innerHTML = parseInt(thisLvlUpperLimit.value) + 1;
}

function determineThisLvlUpperLimitMinimum(thisLvl) {
    var thisLvlMin = parseInt(document.getElementById("lvl" + thisLvl + "_min").innerHTML);
    document.getElementById("lvl" + thisLvl + "_limit").min = thisLvlMin + 1;
}