$(document).ready(function() {
    // Credit application form submission
    $('form').submit(function(event) {
        event.preventDefault();

        var formData = {
            'identityNumber': $('input[name=identityNumber]').val(),
            'nameSurname': $('input[name=nameSurname]').val(),
            'monthlyIncome': $('input[name=monthlyIncome]').val(),
            'phoneNumber': $('input[name=phoneNumber]').val(),
            'birthDate': $('input[name=birthDate]').val(),
            'collateralAmount': $('input[name=collateralAmount]').val(),
            'creditScore': $('input[name=creditScore]').val()
        };

        $.ajax({
            type: 'POST',
            url: '/credit-application',
            data: formData,
            dataType: 'json',
            encode: true
        })
            .done(function(data) {
                console.log(data);
                if (data.creditResult == 'APPROVAL') {
                    $('.credit-result-message').html('Congratulations! Your credit application has been approved with a limit of ' + data.creditLimit);
                } else {
                    $('.credit-result-message').html('We regret to inform you that your credit application has been rejected.');
                }
                $('.credit-result').show();
            });

    });

    // Credit application search form submission
    $('#searchForm').submit(function(event) {
        event.preventDefault();

        var formData = {
            'identityNumber': $('input[name=identityNumber]').val(),
            'birthDate': $('input[name=birthDate]').val()
        };

        $.ajax({
            type: 'POST',
            url: '/search-credit-application',
            data: formData,
            dataType: 'json',
            encode: true
        })
            .done(function(data) {
                console.log(data);
                if (data) {
                    var birthDate = new Date(data.birthDate);
                    var formattedBirthDate = ('0' + birthDate.getDate()).slice(-2) + '/'
                        + ('0' + (birthDate.getMonth() + 1)).slice(-2) + '/'
                        + birthDate.getFullYear();
                    $('.credit-application-result').html(
                        '<p>ID: ' + data.id + '</p>' +
                        '<p>Identity Number: ' + data.identityNumber + '</p>' +
                        '<p>Name Surname: ' + data.nameSurname + '</p>' +
                        '<p>Monthly Income: ' + data.monthlyIncome + '</p>' +
                        '<p>Phone Number: ' + data.phoneNumber + '</p>' +
                        '<p>Birth Date: ' + formattedBirthDate + '</p>' +
                        '<p>Collateral Amount: ' + data.collateralAmount + '</p>' +
                        '<p>Credit Score: ' + data.creditScore + '</p>' +
                        '<p>Credit Result: ' + data.creditResult + '</p>' +
                        '<p>Credit Limit: ' + data.creditLimit + '</p>'
                    );
                } else {
                    $('.credit-application-result').html('<p>Credit application not found.</p>');
                }
                $('.credit-application-result').show();
            });
    });

    // Hide credit application result and search result divs on page load
    $('.credit-result').hide();
    $('.credit-application-result').hide();
});
