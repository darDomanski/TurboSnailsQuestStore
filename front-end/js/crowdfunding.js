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