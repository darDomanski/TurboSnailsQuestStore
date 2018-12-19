// Collect all buttons and dialog box elements
var saveEditButton = document.getElementById("save_mentor_button");
var exitEditButton = document.getElementById("exit_mentor_button");
var addMentorButton = document.getElementById("add_mentor_button");
var addMentorOption = document.getElementById("add_mentor_option");
var mentorNumberInput = document.getElementById("mentor_number");
var mentorEditButton = document.getElementById("edit_mentor_option");

var editWindow = document.getElementById("edit_window");
var shadeBackground = document.getElementById("shade_background");

var clickedEditButtonId;

// Show box for adding and editing mentor
function showDialogBox(addOrEdit) {

    editWindow.style.display = "block";
    shadeBackground.style.display = "block";

    if (addOrEdit == "addMentor") {
        saveEditButton.style.display = "none";
        addMentorButton.style.display = "block";
    } else if (addOrEdit == "editMentor") {
        addMentorButton.style.display = "none";
        saveEditButton.style.display = "block";
    }
};

// Clear all inputs and hide box
function hideEditDialogBox() {
    document.getElementById("edit_mentor_name").value = "";
    document.getElementById("edit_mentor_class").value = "";
    document.getElementById("edit_mentor_email").value = "";
    editWindow.style.display = "none";
    shadeBackground.style.display = "none";
};

// Add classes for all list items and place that number in id span
function addClassesAndButtonsForListItems() {
    var listItems = document.querySelectorAll("#mentors li");
    for (var i = 1; i < listItems.length; i++) {
        listItems[i].setAttribute("class", i);
        listItems[i].children[0].innerHTML = i;
    }
};

addClassesAndButtonsForListItems();

// Create new list item filled with new mentor info
addMentorButton.addEventListener('click', function () {

    var listItems = document.querySelectorAll("#mentors li");
    var listItemsCount = 0;
    for (var i = 0; i < listItems.length; i++) {
        listItemsCount++;
    }

    var newMentorName = document.getElementById("edit_mentor_name").value;
    var newMentorClass = document.getElementById("edit_mentor_class").value;
    var newMentorEmail = document.getElementById("edit_mentor_email").value;

    var newMentorListItem = document.createElement("li");
    newMentorListItem.setAttribute("class", listItemsCount);

    var mentorIdSpan = getMentorIdSpan(listItemsCount);
    newMentorListItem.appendChild(mentorIdSpan);
    var mentorNameSpan = getMentorNameSpan(newMentorName);
    newMentorListItem.appendChild(mentorNameSpan);
    var mentorClassSpan = getMentorClassSpan(newMentorClass);
    newMentorListItem.appendChild(mentorClassSpan);
    var mentorEmailSpan = getMentorEmailSpan(newMentorEmail);
    newMentorListItem.appendChild(mentorEmailSpan);
    /*
    var newMentorEditButton = getMentorEditButton(listItemsCount);   
    newMentorListItem.appendChild(newMentorEditButton);
    */

    document.getElementById("mentors").appendChild(newMentorListItem);

    hideEditDialogBox();
});

// Get mentors id from span
function getMentorIdSpan(listItemsCount) {
    var mentorIdSpan = document.createElement("span");
    mentorIdSpan.setAttribute("class", "mentor_e_mail");
    mentorIdSpan.innerHTML = listItemsCount;

    return mentorIdSpan;
};

// Get mentor name span from form input
function getMentorNameSpan(newMentorName) {
    var mentorNameSpan = document.createElement("span");
    mentorNameSpan.classList.add("mentor_name");
    var mentorNameNode = document.createTextNode(newMentorName);
    mentorNameSpan.appendChild(mentorNameNode);

    return mentorNameSpan;
};

// Get mentor class span from form input
function getMentorClassSpan(newMentorClass) {
    var mentorClassSpan = document.createElement("span");
    mentorClassSpan.classList.add("mentor_class");
    var mentorClassNode = document.createTextNode(newMentorClass);
    mentorClassSpan.appendChild(mentorClassNode);

    return mentorClassSpan;
};

// Get mentor e-mail span from form input
function getMentorEmailSpan(newMentorEmail) {
    var mentorEmailSpan = document.createElement("span");
    mentorEmailSpan.classList.add("mentor_e_mail");
    var mentorEmailNode = document.createTextNode(newMentorEmail);
    mentorEmailSpan.appendChild(mentorEmailNode);

    return mentorEmailSpan;
};

// Check if there is a number in mentor edit input and change placeholder in dialog box
mentorEditButton.addEventListener('click', function () {
    if (mentorNumberInput.value != "") {
        var mentorNumber = mentorNumberInput.value;

        var chosenMentorNodeId = mentorNumberInput.value;

        var chosenMentorsName = document.getElementsByClassName(chosenMentorNodeId)[0].children[1].innerHTML;
        document.getElementById("edit_mentor_name").placeholder = chosenMentorsName;
        var chosenMentorsClass = document.getElementsByClassName(chosenMentorNodeId)[0].children[2].innerHTML;
        document.getElementById("edit_mentor_class").placeholder = chosenMentorsClass;
        var chosenMentorsEmail = document.getElementsByClassName(chosenMentorNodeId)[0].children[3].innerHTML;
        document.getElementById("edit_mentor_email").placeholder = chosenMentorsEmail;

        showDialogBox("editMentor");
    } else {
        alert("Input mentor's number!");
    };

});

// Override placeholders text when editing concrete mentor
saveEditButton.addEventListener('click', function () {
    editMentor();

    document.getElementById("edit_mentor_name").placeholder = "Mentor name";
    document.getElementById("edit_mentor_class").placeholder = "Mentor class";
    document.getElementById("edit_mentor_email").placeholder = "Mentor email";

    hideEditDialogBox();
});

// Get all inputs from input fields and override ones in mentors list
function editMentor() {

    var chosenMentorNodeId = mentorNumberInput.value;

    var newMentorName = document.getElementById("edit_mentor_name").value;
    var newMentorClass = document.getElementById("edit_mentor_class").value;
    var newMentorEmail = document.getElementById("edit_mentor_email").value;

    var overridedMentorName = document.getElementsByClassName(chosenMentorNodeId)[0].children[1];
    var overridedMentorsClass = document.getElementsByClassName(chosenMentorNodeId)[0].children[2];
    var overridedMentorsEmail = document.getElementsByClassName(chosenMentorNodeId)[0].children[3];

    if (newMentorName == "" || newMentorClass == "" || newMentorEmail == "") {
        if (newMentorName == "") {
            alert("Input mentor's name!");
        }
        if (newMentorClass == "") {
            alert("Input mentor's class!");
        }
        if (newMentorEmail == "") {
            alert("Input mentor's e-mail!");
        };
    } else {
        overridedMentorName.innerHTML = newMentorName;
        overridedMentorsClass.innerHTML = newMentorClass;
        overridedMentorsEmail.innerHTML = newMentorEmail;
    };
};

exitEditButton.addEventListener('click', function () {
    hideEditDialogBox();
});

addMentorOption.addEventListener('click', function () {
    showDialogBox("addMentor");
});