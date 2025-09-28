$(document).ready(function() {
    $('input[name="search"]').on('keyup', function(event) {
        event.preventDefault(); // prevent default form submission

        var search = $(this).val();
        $.ajax({
            type: 'GET',
            url: '/customers/search',
            data: {search: search},
            beforeSend: function() {
            },
            success: function(data) {
                $('table tbody').html(data);
            },
            error: function(xhr, status, error) {
                console.log('Error occurred');
                //console.log(xhr.responseText);
            }
        });
    });
});