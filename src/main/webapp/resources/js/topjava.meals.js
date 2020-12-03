var ctx;

// function getDataTable() {
$(function () {
    ctx = {
        ajaxUrl: "ui/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Update",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "asc"
                ]
            ]
        })
    };
    makeEditable();
});

// }


function clearFilter() {
    $("#filter").find(":input").val("");
}

function filter() {
    console.log("filter");

    $.ajax({
        url: "ui/meals/filter",
        type: "GET",
        data: {
            "startDate": $('#startDate').val(),
            "endDate": $('#endDate').val(),
            "startTime": $('#startTime').val(),
            "endTime": $('#endTime').val()
        }
    }).done(function (result) {
        alert("before foreach")
        console.log("success, data: " + result.forEach())
        alert("after foreach")
    })
        .fail(function (error) {
            console.log("error: " + error)
        });
}
