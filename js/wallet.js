function getCCAmount() {
    var ccAmount = document.getElementById("amount_of_coolcoins");
    var actualCCAmount = document.getElementById("actual_cc_amount");

    actualCCAmount.innerHTML = ccAmount.innerHTML;
}