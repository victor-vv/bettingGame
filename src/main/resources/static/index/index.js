var helpers =
    {

    };
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

function fillGamesTable() {
    var $gamesTable = $("#games_table");
    var tourNumber = $("#tourNumber").val();
    $.ajax({
            url: "v1/game?tourNumber=" + tourNumber,
            type: "GET",
            success: function(data) {
                //clearing the table
                $gamesTable.find("tr:gt(0)").remove();
                if (data.length === 0) {
                    $('<tr>').append($('<td>').text("No games found")).appendTo('#games_table');
                    return;
                }
                $.each(data, function(i, item) {
                    $('<tr id="games_table_line">').append(
                        $('<td>').addClass('gameIdCell').text(item.id).hide(),
                        $('<td>').addClass('gameDateCell').text(item.date),
                        $('<td>').addClass('homeTeamIdCell').text(item.homeTeam.id).hide(),
                        $('<td>').addClass('homeTeamNameCell').text(item.homeTeam.name),
                        $('<td>').addClass('awayTeamIdCell').text(item.awayTeam.id).hide(),
                        $('<td>').addClass('awayTeamNameCell').text(item.awayTeam.name),
                        $('<td>').addClass('homeTeamBetCell').text(item.homeTeamBet === null ? "" : item.homeTeamBet).attr('contenteditable', 'true'),
                        $('<td>').addClass('awayTeamBetCell').text(item.awayTeamBet === null ? "" : item.awayTeamBet).attr('contenteditable', 'true'),
                        $('<td>').addClass('homeTeamScoreCell').text(item.homeTeamScore === null ? "-" : item.homeTeamScore),
                        $('<td>').addClass('awayTeamScoreCell').text(item.awayTeamScore === null ? "-" : item.awayTeamScore)
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
        url: "v1/bet",
        contentType: "application/json",
        data: JSON.stringify(betDto),
        success: function () {
            console.log("The bet was saved");
            $(gameLine).append($('<td>').addClass('BetSaveStatus').text("OK"));
        },
        failure: function () {
            console.log("Something went wrong, the bet was not saved, try again");
            gameLine.append($('<td>').addClass('BetSaveMessage').text("Something went wrong, the bet was not saved, try again"));
        }
    })
}

function getTeamFromApi() {
    var id = $("#teamRequested").val();
    console.log("id = " + id);
    $.ajax({
        url: "/v1/team?name=" + id,
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
