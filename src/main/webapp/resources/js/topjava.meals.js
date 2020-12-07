var ctx;
var mealAjaxUrl = "profile/meals/";

    $('#startDate').datetimepicker({
        timepicker:false,
        // format:'d-m-Y'
        format:'Y-m-d'
    });

    $('#startTime').datetimepicker({
        datepicker:false,
        format:'H:m'
    });

    $('#endDate').datetimepicker({
        timepicker:false,
        format:'Y-m-d'
    });

    $('#endTime').datetimepicker({
        datepicker:false,
        format:'H:m'
    });

    $('#dateTime').datetimepicker({
        // dateformat:'Y-m-d',
        // timeformat:'H:m'
    });

function updateFilteredTable() {
    $.ajax({
        type: "GET",
        url: "profile/meals/filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    ctx = {
        ajaxUrl: mealAjaxUrl,
        datatableApi: $("#datatable").DataTable({
            "ajax": {
              "url": mealAjaxUrl,
              "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date.substring(0, 10) + " " + date.substring(11, 19);
                        }
                    }
                },
                {
                    "data": "description",
                    "render": function (data, type, row) {
                        if (type === "display") {
                            return data;
                        }
                    }
                },
                {
                    "data": "calories",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return date;
                        }
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "Delete",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (data.excess) {
                    $(row).attr("data-mealExcess", true);
                } else {
                    $(row).attr("data-mealExcess", false);
                }
            }
        }),
        updateTable: updateFilteredTable
    };
    makeEditable();
});