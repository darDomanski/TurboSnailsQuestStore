
function create_quest_card_2() {

    var title = document.getElementById("input_card_title").value;
    var description = document.getElementById("input_card_description").value;
    var value = document.getElementById("input_card_value").value;

    document.getElementById("empty_card_title").innerHTML = title;
    document.getElementById("empty_card_descritpion").innerHTML = description;
    document.getElementById("empty_card_value").innerHTML = value + " cc";

    document.getElementById("no_img").src = "../resources/img/heroes.jpg";
}


function create_quest_card() {

    var title = document.getElementById("input_card_title").value;
    var description = document.getElementById("input_card_description").value;
    var value = document.getElementById("input_card_value").value;

    var emptycard = document.getElementById("new_card")

    emptycard.setAttribute("class", "card")

    var p_title = document.createElement("p");
    var div_foto = document.createElement("div");
    var foto = document.createElement("img");
    var ul = document.createElement("ul");
    var li1 = document.createElement("li");
    var p1 = document.createElement("p");
    var li2 = document.createElement("li");
    var p2 = document.createElement("p");
    var button = document.createElement("div");

    emptycard.appendChild(p_title);
    emptycard.appendChild(div_foto);
    div_foto.appendChild(foto);
    emptycard.appendChild(ul);
    ul.appendChild(li1);
    li1.appendChild(p1);
    ul.appendChild(li2);
    li2.appendChild(p2);
    emptycard.appendChild(button);

    p_title.innerHTML = title;
    p_title.setAttribute("class", "text_on_card_1");

    foto.setAttribute("src", "../resources/img/heroes.jpg");
    foto.setAttribute("class", "pictures_on_card");

    p1.setAttribute("class", "text_on_card_2");
    p1.innerHTML = description;
    p2.setAttribute("class", "text_on_card_2");
    p2.innerHTML = value + " cc";
    button.setAttribute("class", "editcardbutton");
    button.innerHTML = "EDIT CARD";

    // var x = document.createElement("div");
    // x.setAttribute("id","xxxxx");
    // var y = document.createElement("div");
    // x.appendChild(y);
}


function editCard() {

    var id_button = event.srcElement.id;
    var len = id_button.length;
    var id_card = id_button.substr(0, len - 2);

    var new_title = prompt("Please enter new title", "???");
    var card = document.getElementById(id_card);
    card.children[0].innerHTML = new_title;

    var new_description = prompt("Please enter new description", "???");
    var tempelem = card.children[2];
    var temp2 = tempelem.children[0];
    var temp3 = temp2.children[0].innerHTML = new_description;

    var new_value = prompt("Please enter new value", "???");
    var tempelem = card.children[2];
    var temp2 = tempelem.children[1];
    var temp3 = temp2.children[0].innerHTML = new_value + " cc";
}


function buy() {
    alert("You do not have enough money to buy me!!!")
}


