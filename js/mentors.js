var editButton = document.getElementsByClassName("edit_button");
var saveEditButton = document.getElementById("save_mentor_button");
var exitEditButton = document.getElementById("exit_mentor_button");
var addMentorButton = document.getElementById("add_mentor");

var editWindow = document.getElementById("edit_window");
var shadeBackground =  document.getElementById("shade_background");

function showEditDialogBox() {
    editWindow.style.display = "block";
    shadeBackground.style.display = "block";
}
function hideEditDialogBox() {
    editWindow.style.display = "none";
    shadeBackground.style.display = "none";
}
var editMentor = function(){
    showEditDialogBox();
};

for (var i = 0; i < editButton.length; i++) {
    editButton[i].addEventListener('click', editMentor, false);
};
saveEditButton.addEventListener('click', function(){
    hideEditDialogBox();
});
exitEditButton.addEventListener('click', function(){
    hideEditDialogBox();
});
addMentorButton.addEventListener('click', function(){
    showEditDialogBox();
    
});