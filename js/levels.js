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