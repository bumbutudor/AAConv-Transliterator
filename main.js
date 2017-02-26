//23.12.16 20:22 v0.4
function lookup() {
    var selected = document.getSelection().toString();
    $.ajax({
        type: "post",
        url: "TaggerServlet",
        data: {
            queryString: selected
        },
        success: function (data) {
            $("#content").val(data);
        }
    });
}

function translit() {
    var period = -1;
    var cyrillicText = $('#cyrTextPanel').val();
    cyrillicText = cyrillicText.replace(/%/g, "");
    cyrillicText = cyrillicText.replace(/&/g, "");

    var actualize = document.getElementById('actualizeCheckBox').checked;

    if (document.getElementById('sovietRb').selected) {
        period = 0;
    } else if (document.getElementById('tranzRb').selected) {
        period = 1;
    } else if (document.getElementById('romanianRb').selected) {
        period = 2;
    } else if (document.getElementById('slavRb').selected) {
        period = 3;
    }
    $.ajax({
        type: "post",
        //url: "http://udom.tk:8001/ProcessServlet",
        url: "ProcessServlet",
        data: "cyrillicText=" + cyrillicText + "&period=" + period + "&actualize=" + actualize,
        success: function (msg) {
            document.getElementById("latTextPanel").value = msg.toString();
        }
    });
}

function loadfile(input) {
    var form_data = new FormData();
    form_data.append("file", input.files[0]);
    console.log(form_data);
    $.ajax({
        url: "upload",
        dataType: 'html',
        cache: false,
        contentType: false,
        processData: false,
        data: form_data,
        type: 'post',
        success: function (data) {
            console.log(111);
            document.getElementById("cyrTextPanel").value = data;
            translit();
        }
    });
}

$("#btn-save-txt").click(function () {
    var text = $("#latTextPanel").val();
    text.replace('\n', '\n\r');
    var blob = new Blob([text], {type: "text/plain;charset=utf-8"});
    saveAs(blob,  "Translated.txt");
});

$("#btn-save-doc").click(function () {
    var text = $("#latTextPanel").val();
    text.replace('\n', '\n\r');
    var blob = new Blob([text], {type: "doc;charset=utf-8"});
    saveAs(blob,  "Translated.doc");
});

$("#btn-save-rtf").click(function () {
    var text = $("#latTextPanel").val();
    text.replace('\n', '\n\r');
    var blob = new Blob([text], {type: "rtf;charset=utf-8"});
    saveAs(blob,  "Translated.rtf");
});

function ClearFields() {
    document.getElementById("cyrTextPanel").value = "";
    document.getElementById("latTextPanel").value = "";
    document.getElementById("content").value = "";
}

$('#cyrTextPanel').add('#period').add('#actualizeCheckBox').on('change keyup paste', function ()
{
    translit();
});
/**
 * 21.12.16 16:53 v0.3  saddest day of my fking life.((
 *
 * @author Richard Strauss
 */
