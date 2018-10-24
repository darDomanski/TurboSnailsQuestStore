// Collect all buttons and dialog box elements
var editButton = document.getElementsByClassName("edit_button");
var saveEditButton = document.getElementById("save_mentor_button");
var exitEditButton = document.getElementById("exit_mentor_button");
var addMentorButton = document.getElementById("add_mentor_button");
var addMentorOption = document.getElementById("add_mentor_option");

var editWindow = document.getElementById("edit_window");
var shadeBackground =  document.getElementById("shade_background");

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

// Add function for all edit buttons
var showMentorEdit = function() {    
    showDialogBox("editMentor");
};

function addEventListeners() {
    for (var i = 0; i < editButton.length; i++) {
        editButton[i].addEventListener('click', showMentorEdit, false);
    };
};

addEventListeners();

function addClassesAndButtonsForListItems() {
    var listItems = document.querySelectorAll("#mentors li");
    for (var i = 1; i < listItems.length; i++) {
        listItems[i].setAttribute("class", i);

        var newMentorEditButton = document.createElement("input");
        newMentorEditButton.setAttribute("id", i);
        newMentorEditButton.setAttribute("class", "edit_button");
        newMentorEditButton.setAttribute("type", "button");
        newMentorEditButton.setAttribute("name", "edit_mentor");
        newMentorEditButton.setAttribute("value", "EDIT");

        listItems[i].appendChild(newMentorEditButton);
    }
};

addClassesAndButtonsForListItems();

// Create new list item filled with new mentor info
addMentorButton.addEventListener('click', function() {

    var newMentorName = document.getElementById("edit_mentor_name").value;
    var newMentorClass = document.getElementById("edit_mentor_class").value;
    var newMentorEmail = document.getElementById("edit_mentor_email").value;

    var newMentorListItem = document.createElement("li");

    var mentorNameSpan = getMentorNameSpan(newMentorName);
    newMentorListItem.appendChild(mentorNameSpan);
    var mentorClassSpan = getMentorClassSpan(newMentorClass);    
    newMentorListItem.appendChild(mentorClassSpan);
    var mentorEmailSpan = getMentorEmailSpan(newMentorEmail);
    newMentorListItem.appendChild(mentorEmailSpan);
    var newMentorEditButton = getMentorEditButton();   
    newMentorListItem.appendChild(newMentorEditButton);

    document.getElementById("mentors").appendChild(newMentorListItem);

    addEventListeners();
    hideEditDialogBox();
});

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

function getMentorEditButton() {
    var newMentorEditButton = document.createElement("input");
    newMentorEditButton.setAttribute("class", "edit_button");
    newMentorEditButton.setAttribute("type", "button");
    newMentorEditButton.setAttribute("name", "edit_mentor");
    newMentorEditButton.setAttribute("value", "EDIT");

    return newMentorEditButton;
};

// 
saveEditButton.addEventListener('click', function() {
    editMentor();
    hideEditDialogBox();
});

function editMentor() {
    
};

//
exitEditButton.addEventListener('click', function() {
    hideEditDialogBox();
});

//
addMentorOption.addEventListener('click', function() {
    showDialogBox("addMentor");    
});
