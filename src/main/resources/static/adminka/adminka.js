$(document).ready(
    function() {
        $.ajax({
            type: "GET",
            url: "/v1/teams",
            success: function(data) {
                helpers.buildDropdown(
                    data,
                    $('#home_team'),
                    'Select an option'
                );
                helpers.buildDropdown(
                    data,
                    $('#away_team'),
                    'Select an option'
                );
            }
        });
    }
);

$(document).ready(
    function buildAdminTournamentDropdown() {
        $("#tourId").prop('disabled', true).addClass('disabled');
        $.ajax({
            type: "GET",
            url: "/v1/tournaments",
            success: function(data) {
                helpers.buildDropdown(data, $('#tournamentId'), 'Выберите турнир');
            }
        });
    }
);

function fillAdminToursDropDown() {
    var tournamentDropdown = $('#tournamentId');
    var tourDropdown = $('#tourId');
    $.ajax({
        type: "GET",
        url: "/v1/tours?tournamentId=" + tournamentDropdown.find(":selected").val(),
        success: function(data) {
            helpers.buildTourDropdown(data, tourDropdown, 'Выберите тур');
            tourDropdown.prop('disabled', false).removeClass('disabled');
        }
    });
}

function fillAdminGamesTable() {

    var adminGamesTable = $("#adminGamesTable");
    var tourId = $("#tourId").val();

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
            url: "v1/games?tourNumber=" + tourId,
            type: "GET",
            success: function(data) {
                //clearing the table
                adminGamesTable.find("tr:gt(0)").remove();
                if (data.length === 0) {
                    $('<tr>').append($('<td>').text("Нет доступных игр для введенных параметров")).appendTo('#adminGamesTable');
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
                        $('<td>').addClass('homeTeamScoreCell').text(item.homeTeamScore === null ? "0" : item.homeTeamScore).attr('contenteditable', 'true'),
                        $('<td>').addClass('awayTeamScoreCell').text(item.awayTeamScore === null ? "0" : item.awayTeamScore).attr('contenteditable', 'true'),
                        $('<td>').addClass('gameChangingStatus').hide()
                    ).appendTo('#adminGamesTable');
                });
            },
            failure: function() {
                adminGamesTable.find("tr:gt(0)").remove();
            }
        }
    );
}

function closeGame(gameLine) {
    const homeTeamScore = gameLine.find(".homeTeamScoreCell").html(),
        awayTeamScore = gameLine.find(".awayTeamScoreCell").html();
    if (!$.isNumeric(homeTeamScore) || !$.isNumeric(awayTeamScore)) {
        gameLine.find('.betChangingStatus').html("Ставки введены некорректно").show();
        return;
    }
    const gameScoreDto = {
        "gameId": gameLine.find(".gameIdCell").html(),
        "homeTeamScore": homeTeamScore,
        "awayTeamScore": awayTeamScore
    };
    $.ajax({
        type: "PATCH",
        url: "v1/games/score",
        contentType: "application/json",
        data: JSON.stringify(gameScoreDto),
        success: function () {
            console.log("The game was closed");
            gameLine.find('.gameChangingStatus').html("OK").show();
        },
        failure: function () {
            console.log("Something went wrong, the game was not saved, try again");
            gameLine.find('.gameChangingStatus').html("Что-то пошло не так, попробуйте еще раз").show();
        }
    })
}

function computeTour(tourId) {

    const gamesTable = $('#adminGamesTable');
    gamesTable.find('tr#games_table_line').each(function() {
        closeGame($(this))
    });

    $.ajax({
        type: "PUT",
        url: "v1/tours/" + tourId + "/computed",
        contentType: 'application/json',
        success: function() {
            alert("The tour was computed and closed");
            console.log("The tour was computed and closed");
        },
        failure: function() {
            alert("Something went wrong, tour wasn't closed");
            console.log("Something went wrong, tour wasn't closed");
        }
    });
}

var helpers =
    {
        buildTourDropdown: function(result, dropdown, emptyMessage)
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
                    dropdown.append('<option value="' + value.id + '">' + value.name + '</option>');
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
        },
        computeTour: function(tourId) {

        },

        createGame: function(homeTeam, awayTeam, tourNumber, date) {
            var homeTeamId = homeTeam.find(":selected").val();
            var awayTeamId = awayTeam.find(":selected").val();
            var tour = tourNumber.val();
            var gameDate = date.val();
            var gameDto = {
                "homeTeamId": homeTeamId,
                "awayTeamId": awayTeamId,
                "date": gameDate,
                "tour": tour
            };
            $.ajax({
                type: "POST",
                url: "v1/games",
                contentType: 'application/json',
                data: JSON.stringify(gameDto),
                success: {},
                failure: {}
            });
        },
    };