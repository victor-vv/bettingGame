$(document).ready(
    function buildTournamentDropdown() {
        $.ajax({
            type: "GET",
            url: "/v1/tournaments",
            success: function(data) {
                helpers.buildDropdown(data, $('#tournamentId'), 'Выберите турнир');
            }
        });
    }
);

function buildUserRankingTable() {
    const tournamentId = $('#tournamentId').val(),
        userRankingTable = $('#userRankingTable');

    $.ajax({
        type: "GET",
        url: "/v1/userRanking?tournamentId=" + tournamentId,
        success: function(data) {
            //clearing the table
            userRankingTable.find("tr:gt(0)").remove();
            if (data.userScores.length === 0) {
                $('<tr>').append($('<td>').text("Нет игроков")).appendTo('#userRankingTable');
                return;
            }
            $.each(data.userScores, function(i, item) {
                $('<tr id="games_table_line">').append(
                    $('<td>').addClass('rankCell').text(i + 1),
                    $('<td>').addClass('userIdCell').text(item.userId).hide(),
                    $('<td>').addClass('userNameCell').text(item.username),
                    $('<td>').addClass('numberOfPointsCell').text(item.numberOfPoints),
                ).appendTo('#userRankingTable');
            });
        },
        failure: function() {
            userRankingTable.find("tr:gt(0)").remove();
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
        }
    };