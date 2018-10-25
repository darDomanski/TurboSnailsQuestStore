
function create_quest_card(){

    var title = document.getElementById("input_card_title").value;
    var description = document.getElementById("input_card_description").value;
    var value = document.getElementById("input_card_value").value;

    document.getElementById("empty_card_title").innerHTML = title;
    document.getElementById("empty_card_descritpion").innerHTML = description;
    document.getElementById("empty_card_value").innerHTML = value + " cc" ;

    document.getElementById("no_img").src = "../resources/img/heroes.jpg";

}



// function editCard(clicked_id){
//     dokument.write(clicked_id)
//     alert(clicked_id);
// }

function editCard(){

    // alert(event.srcElement.id);
    var id_button = event.srcElement.id;
    var len = id_button.length;
    var id_card = id_button.substr(0,len-2);

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














// function new()
// {

// var x = document.createElement("div");
// x.setAttribute("id","xxxxx");

// var y = document.createElement("div");

// x.appendChild(y);


// }

