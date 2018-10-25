function addClass() {
    var classesTable = document.getElementById("classes_table");

    var newClass = document.createElement("tr");
    var newMentor = document.createElement("td");
    var newClassName = document.createElement("td");

    newMentor.innerHTML = document.getElementById("mentor_select").value;
    newClassName.innerHTML = document.getElementById("class_name").value;

    newClass.appendChild(newClassName);
    newClass.appendChild(newMentor);

    classesTable.appendChild(newClass);
}