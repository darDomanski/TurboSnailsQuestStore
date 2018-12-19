// Collect all buttons and dialog box elements
var saveEditButton = document.getElementById("save_student_button");
var exitEditButton = document.getElementById("exit_student_button");
var addStudentButton = document.getElementById("add_student_button");
var addStudentOption = document.getElementById("add_student_option");
var studentNumberInput = document.getElementById("student_number");
var studentEditButton = document.getElementById("edit_student_option");

var editWindow = document.getElementById("edit_window");
var shadeBackground = document.getElementById("shade_background");

var clickedEditButtonId;

// Show box for adding and editing mentor
function showDialogBox(addOrEdit) {

    editWindow.style.display = "block";
    shadeBackground.style.display = "block";

    if (addOrEdit == "addStudent") {
        saveEditButton.style.display = "none";
        addStudentButton.style.display = "block";
    } else if (addOrEdit == "editStudent") {
        addStudentButton.style.display = "none";
        saveEditButton.style.display = "block";
    }
};

// Clear all inputs and hide box
function hideEditDialogBox() {
    document.getElementById("edit_student_name").value = "";
    document.getElementById("Exploring a dungeon").checked = false;
    document.getElementById("Solving the puzzle").checked = false;
    document.getElementById("Slaying a dragon").checked = false;
    document.getElementById("Combat training").checked = false;
    document.getElementById("Sanctuary").checked = false;
    document.getElementById("Time travel").checked = false;
    editWindow.style.display = "none";
    shadeBackground.style.display = "none";
};

// Add classes for all list items and place that number in id span
function addClassesAndButtonsForListItems() {
    var listItems = document.querySelectorAll("#students li");
    for (var i = 1; i < listItems.length; i++) {
        listItems[i].setAttribute("class", i);
        listItems[i].children[0].innerHTML = i;
    }
};

addClassesAndButtonsForListItems();

// Create new list item filled with new student info
addStudentButton.addEventListener('click', function () {

    var listItems = document.querySelectorAll("#students li");
    var listItemsCount = 0;
    for (var i = 0; i < listItems.length; i++) {
        listItemsCount++;
    }

    var newStudentName = document.getElementById("edit_student_name").value;
    var newStudentClass = document.getElementById("edit_student_class").value;
    var newStudentEmail = document.getElementById("edit_student_email").value;

    var newStudentListItem = document.createElement("li");
    newStudentListItem.setAttribute("class", listItemsCount);

    var studentIdSpan = getstudentIdSpan(listItemsCount);
    newStudentListItem.appendChild(studentIdSpan);
    var studentNameSpan = getstudentNameSpan(newStudentName);
    newStudentListItem.appendChild(studentNameSpan);
    var studentClassSpan = getstudentClassSpan(newStudentClass);
    newStudentListItem.appendChild(studentClassSpan);
    var studentEmailSpan = getstudentEmailSpan(newStudentEmail);
    newStudentListItem.appendChild(studentEmailSpan);

    document.getElementById("students").appendChild(newStudentListItem);

    hideEditDialogBox();
});

// Get student name span from form input
function getstudentNameSpan(newStudentName) {
    var studentNameSpan = document.createElement("span");
    studentNameSpan.classList.add("student_name");
    var studentNameNode = document.createTextNode(newStudentName);
    studentNameSpan.appendChild(studentNameNode);

    return studentNameSpan;
};

// Check if there is a number in student edit input and change placeholder in dialog box
studentEditButton.addEventListener('click', function () {
    if (studentNumberInput.value != "") {

        var chosenStudentNodeId = studentNumberInput.value;

        var chosenStudentsName = document.getElementsByClassName(chosenStudentNodeId)[0].children[1].innerHTML;
        document.getElementById("edit_student_name").placeholder = chosenStudentsName;

        showDialogBox("editStudent");
    } else {
        alert("Input student's number!");
    };

});

// Override placeholder text when editing concrete student
saveEditButton.addEventListener('click', function () {
    editStudent();

    document.getElementById("edit_student_name").placeholder = "Student name";
    document.getElementById("edit_student_class").placeholder = "Student class";
    document.getElementById("edit_student_email").placeholder = "Student email";

    hideEditDialogBox();
});

// Get all inputs from input fields and override ones in students list
function editStudent() {

    var chosenStudentNodeId = studentNumberInput.value;

    var newStudentName = document.getElementById("edit_student_name").value;
    var newStudentClass = document.getElementById("edit_student_class").value;
    var newStudentEmail = document.getElementById("edit_student_email").value;

    var overridedStudentName = document.getElementsByClassName(chosenStudentNodeId)[0].children[1];
    var overridedStudentClass = document.getElementsByClassName(chosenStudentNodeId)[0].children[2];
    var overridedStudentEmail = document.getElementsByClassName(chosenStudentNodeId)[0].children[3];

    if (newStudentName == "" || newStudentClass == "" || newStudentEmail == "") {
        if (newStudentName == "") {
            alert("Input mentor's name!");
        }
        if (newStudentClass == "") {
            alert("Input mentor's class!");
        }
        if (newStudentEmail == "") {
            alert("Input mentor's e-mail!");
        };
    } else {
        overridedStudentName.innerHTML = newStudentName;
        overridedStudentClass.innerHTML = newStudentClass;
        overridedStudentEmail.innerHTML = newStudentEmail;
    };
};

exitEditButton.addEventListener('click', function () {
    hideEditDialogBox();
});

addStudentOption.addEventListener('click', function () {
    showDialogBox("addStudent");
});