$(document).ready(function() {
    // Populating todo list from database
    readFromDb();

    // Insert button
    $("#insert").click(function(event) {
        event.preventDefault();
        // Check input in form
        if (validInput()) {
            insertNote();
        } else {
            msgDanger("Fill out title and content!");
        }
    });

    // Update button
    $("#update").click(function(event) {
        insert = false;
        update = true;
        event.preventDefault();
        // Check input in form
        if (validUpdate() && validInput()) {
        	// Getting the id, needed when updating.
            var id = $("#id").val();
            updateNote(id);
        } else {
            msgDanger("Fill out all fields!");
        }
    });
});

function readFromDb() {
    // Reading all elements from the server
    $.ajax({
        url: "http://localhost:8080/api/notes"
    }).then(function(data) {
        // Adding fetched data to table
        for (var i = 0; i < data.length; i++) {
            addTableTds(data[i]);
        }
    });
}

function updateNote(id) {
    // Data to submit
    var formData = {
        title: $("#title").val(),
        content: $("#content").val()
    }
    // HTTP Put to server
    $.ajax({
        type: "PUT",
        contentType: "application/json",
        url: "http://localhost:8080/api/notes/" + id,
        data: JSON.stringify(formData),
        dataType: "json",
        success: function(result) {
            msgSuccess("Updated!");
            // Updating table content
            addTableTds(result, true);
        },
        error: function(e) {
            msgDanger("Error updating!");
        }
    });
}

function insertNote() {
    // Getting data from form
    var formData = {
        title: $("#title").val(),
        content: $("#content").val()
    }
    // HTTP Post to server
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "http://localhost:8080/api/notes",
        data: JSON.stringify(formData),
        dataType: "json",
        success: function(result) {
            // Success, updating the table with returned object from server.
            addTableTds(result, false);
            msgSuccess("Inserted object!");
        },
        error: function(e) {
            msgDanger("Error inserting!");
        }
    });
}

function deleteNote(id) {
    // Deleting element from the server.
    $.ajax({
        type: "DELETE",
        url: "http://localhost:8080/api/notes/" + id,
        success: function(result) {
            // Removing the td from the table.
            $("#row-" + id).remove();
            msgSuccess("Deleted from server!");
        },
        error: function(e) {
            msgDanger("Error deleting!");
        }
    });
}

function addTableTds(data, isUpdate) {
    // Getting fields from current object.
    var id = data.id;
    var title = data.title;
    var content = data.content;

    // Setting up table content
    var tableContent = '<td>' + id + '</td>' +
        '<td class = "title">' + title + '</td>' +
        '<td class = "content">' + content + '</td>' +
        '<td class = "td-sel' + id + '"></td>';

    // Adding to table
    if (isUpdate) {
        // Appending to existing td
        var row = $("#row-" + id);
        row.html(tableContent);
    } else {
        // Creating new td
        $('.table > tbody:last-child').append(
            '<tr id="row-' + id + '">' + tableContent + '</tr>'
        );
    }
    // Adding the inline form to the current row 
    $('.td-sel' + id).append( createInlineForm(id) );
}

function createInlineForm(id) {
    // Creating inline form
    var form = $("<form class = 'form-inline'>");
    // Creating select
    var sel = $('<select class = "custom-select">');
    // Creating content for the options populating the select.
    // More can be added!
    var options = [
        { val: 'delete', text: 'Delete' }
    ];
    
    // Creating button
    var button = '<button type = "submit" class="btn btn-primary">Submit</button>';
    // Adding options to select
    $(options).each(function() {
        sel.append($("<option>").attr('value', this.val).text(this.text));
    });

    // Adding elements to the form.
    form.append($(sel), $(button));

    // Form submit-handler
    form.submit(function(event) {
        event.preventDefault();
        // Getting selected option
        var option = $(this).children().children(":selected").val();

        if (option == "delete") {
            deleteNote(id);
        }
    });
    return form;
}

function validUpdate() {
    return $("#id").val().length >= 1;
}

function validInput() {
    return $("#title").val().length >= 1 &&
        $("#content").val().length >= 1;
}

function msgDanger(message) {
    // Showing error message in GUI
    $("#msg").addClass("alert-danger").removeClass("alert-success");
    $("#msg").html(message);
}

function msgSuccess(message) {
    // Showing error message in GUI
    $("#msg").addClass("alert-success").removeClass("alert-danger");
    $("#msg").html(message);
}