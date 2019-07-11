$(document).ready(
    function() {
        $('#tourNumber').keyup(function(e){
            if(e.keyCode === 13) {
                $('#fillTable').click();
                return false;
            }
        });
    }
);
$(document).ready(
    function() {
        $.ajax({
            type: "GET",
            url: "/v1/tours?tournamentId=1",
            success: function(data) {
                helpers.buildSimpleIntegerDropdown(data, $('#tourNumber'), 'Выберите тур');
            }
        });
    }
);
$(document).ready(
    function() {
        $.ajax({
            type: "GET",
            url: "/v1/tournaments",
            success: function(data) {
                helpers.buildDropdown(data, $('#tournamentId'), 'Выберите турнир');
            }
        });
    }
);
$(document).ready(
    function() {
        $.ajax({
            type: "GET",
            url: "/v1/users/current",
            success: function(data) {
                debugger;
                $("#username").html(data);
            }
        });
    }
);

function fillGamesTable() {
    var $gamesTable = $("#games_table");
    var tourNumber = $("#tourNumber").val();

    function formatDate(date) {
        date = new Date(date);
        var HH = date.getHours();
        var MM = date.getMinutes();
        var dd = date.getDate();
        var mm = date.getMonth() + 1;
        var yyyy = date.getFullYear();


        if (MM < 10) {
            MM = '0' + MM;
        }
        if (dd < 10) {
            dd = '0' + dd;
        }
        if (mm < 10) {
            mm = '0' + mm;
        }

        var weekday = new Array(7);
        weekday[0] = "ВС";
        weekday[1] = "ПН";
        weekday[2] = "ВТ";
        weekday[3] = "СР";
        weekday[4] = "ЧТ";
        weekday[5] = "ПТ";
        weekday[6] = "СБ";
        var day = weekday[date.getDay()];
        return dd+'/'+mm+'/'+yyyy+' - ' +HH+':'+MM+' ('+day+')';
    }

    $.ajax({
        url: "v1/games?tourNumber=" + tourNumber,
        type: "GET",
            success: function(data) {
                //clearing the table
                $gamesTable.find("tr:gt(0)").remove();
                if (data.length === 0) {
                    $('<tr>').append($('<td>').text("Нет доступных игр для введенных параметров")).appendTo('#games_table');
                    return;
                }
                $.each(data, function(i, item) {
                    var formattedDate = formatDate(item.date);
                    $('<tr id="games_table_line">').append(
                        $('<td>').addClass('gameIdCell').text(item.id).hide(),
                        $('<td>').addClass('gameDateCell').text(formattedDate),
                        $('<td>').addClass('homeTeamIdCell').text(item.homeTeam.id).hide(),
                        $('<td>').addClass('homeTeamNameCell').text(item.homeTeam.name),
                        $('<td>').addClass('awayTeamIdCell').text(item.awayTeam.id).hide(),
                        $('<td>').addClass('awayTeamNameCell').text(item.awayTeam.name),
                        $('<td>').addClass('homeTeamBetCell').text(item.homeTeamBet === null ? "" : item.homeTeamBet).attr('contenteditable', 'true'),
                        $('<td>').addClass('awayTeamBetCell').text(item.awayTeamBet === null ? "" : item.awayTeamBet).attr('contenteditable', 'true'),
                        $('<td>').addClass('homeTeamScoreCell').text(item.homeTeamScore === null ? "0" : item.homeTeamScore),
                        $('<td>').addClass('awayTeamScoreCell').text(item.awayTeamScore === null ? "0" : item.awayTeamScore)
                    ).appendTo('#games_table');
                })
            },
            failure: function() {
                $gamesTable.find("tr:gt(0)").remove();
                //TODO:
            }
        }
    );
}


function saveBets() {
    var gamesTable = $("#games_table");
    gamesTable.find('tr#games_table_line').each(function() {
        saveBet($(this)
//                    , { Example of transferring thing on the up level
//                    savedSuccess: function () {
//                        console.log("The bet was saved")
//                    },
//                    savedFailure: function() {
//                        console.log("Something went wrong, the bet was not saved")
//                    }
//                }
        )
    })
}

function saveBet(gameLine) {
    var homeTeamScore = gameLine.find(".homeTeamBetCell").html();
    var awayTeamScore = gameLine.find(".awayTeamBetCell").html();
    if (!$.isNumeric(homeTeamScore) || !$.isNumeric(awayTeamScore)) {
        gameLine.append($('<td>').addClass('BetSaveMessage').text("Error! Please fill the bet fields!"));
        return;
    }
    var betDto = {
        "gameId": gameLine.find(".gameIdCell").html(),
        "userId": 1,
        "homeTeamScore": homeTeamScore,
        "awayTeamScore": awayTeamScore
    };
    $.ajax({
        type: "POST",
        url: "v1/bets",
        contentType: "application/json",
        data: JSON.stringify(betDto),
        success: function () {
            console.log("The bet was saved");
            $(gameLine).append($('<td>').addClass('BetSaveStatus').text("OK"));
        },
        failure: function () {
            console.log("Something went wrong, the bet was not saved, try again");
            gameLine.append($('<td>').addClass('BetSaveMessage').text("Что-то пошло не так, попробуйте еще раз"));
        }
    })
}

function getTeamFromApi() {
    var id = $("#teamRequested").val();
    console.log("id = " + id);
    $.ajax({
        url: "/v1/teams?name=" + id,
        type: "GET",
        success: function(data) {
            console.log("Got team: Id=" + data[0].id + ", name = " + data[0].name + ", city = " + data[0].city);
            $("#teamId").val(data[0].id);
            $("#teamName").val(data[0].name);
            $("#teamCity").val(data[0].city);
        },
        error: function(){
            $("#teamId").val("n/a");
            $("#teamName").val("n/a");
            $("#teamCity").val("n/a");
        }
    })
}

var helpers =
    {
        buildSimpleIntegerDropdown: function(result, dropdown, emptyMessage)
        //TODO: переделать нормально
        {
            // Remove current options
            dropdown.html('');
            // Add the empty option with the empty message
            dropdown.append('<option value="">' + emptyMessage + '</option>');
            if(result !== '')
            {
                // Loop through each of the results and append the option to the dropdown
                $.each(result, function(k, value) {
                    dropdown.append('<option value="' + value + '">' + 'Тур ' + value + '</option>');
                });
            }
        },
        buildDropdown: function(result, dropdown, emptyMessage)
        {
            // Remove current options
            dropdown.html('');
            // Add the empty option with the empty message
            dropdown.append('<option value="">' + emptyMessage + '</option>');
            if(result !== '')
            {
                // Loop through each of the results and append the option to the dropdown
                $.each(result, function(k, v) {
                    dropdown.append('<option value="' + v.id + '">' + v.name + '</option>');
                });
            }
        }
    };
