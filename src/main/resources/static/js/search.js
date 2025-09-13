$(document).ready(function() {
    $('input[name="search"]').on('keyup', function(event) {
        event.preventDefault(); // prevent default form submission
        console.log('Keyup event triggered');
        var search = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/customers/search',
            data: {search: search},
            beforeSend: function() {
                console.log('Sending AJAX request');
            },
            success: function(data) {
                console.log('Received response from server');
                console.log(data);
                $('table tbody').html(data);
            },
            error: function(xhr, status, error) {
                console.log('Error occurred');
                console.log(xhr.responseText);
            }
        });
    });
});