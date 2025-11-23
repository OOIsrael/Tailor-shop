$(document).ready(function() {
  $(document).on('click', '.view-customer', function() {
    var id = $(this).data('id');
    console.log('Customer ID:', id);
    $.ajax({
      type: 'GET',
      url: '/customers/getCustomerDetails/' + id,
      success: function(data) {
        console.log('AJAX response:', data);
        var measurementHtml = `
          <table class="table table-bordered">
            <thead>
              <tr>
                <th>Type</th>
                <th>Value</th>
              </tr>
            </thead>
            <tbody>
        `;
        $.each(data.measurements, function(index, measurement) {
          measurementHtml += `
              <tr>
                <td>${measurement.type}</td>
                <td>${measurement.value}</td>
              </tr>
          `;
        });
        measurementHtml += `
            </tbody>
          </table>
        `;
        var modalHtml = `
          <div class="modal" id="viewCustomerModal" style="display: block; position: fixed; top: 0; right: 0; bottom: 0; left: 0; background-color: rgba(0, 0, 0, 0.5); display: flex; justify-content: center; align-items: center;">
            <div class="modal-dialog" style="background-color: #fff; padding: 20px; border-radius: 10px; box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.2); width: 700px; max-width: 90%;">
              <div class="modal-header" style="display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #ddd; padding-bottom: 10px; position: relative;">
                <div style="position: absolute; left: 50%; transform: translateX(-50%);">
                  <h5 class="modal-title" style="font-weight: bold; font-size: 30px; color: #664d03;">Customer Details</h5>
                </div>
                <button type="button" class="btn-close" style="border: none; padding: 15px; font-size: 24px; cursor: pointer; background-color: transparent; margin-left: auto;" onmouseover="this.style.backgroundColor='#ff0000'; this.style.color='#fff'" onmouseout="this.style.backgroundColor='transparent'; this.style.color='#000'" onclick="$('#viewCustomerModal').remove()">×</button>
              </div>
              <div class="modal-body" style="padding-top: 20px;">
                <div class="row">
                  <div class="col-md-6">
                    <p style="font-size: 20px;"><strong>Name:</strong> ${data.name}</p>
                    <p style="font-size: 20px;"><strong>Email:</strong> ${data.email}</p>
                    <p style="font-size: 20px; "><strong>Phone:</strong> ${data.phone}</p>
                  </div>
                  <div class="col-md-6">
                    <p style="font-size: 28px;"><strong>Measurements</strong></p>
                    ${measurementHtml}
                  </div>
                </div>
              </div>
            </div>
          </div>
        `;
        $('body').append(modalHtml);
      },
      error: function(xhr, status, error) {
        console.log('AJAX request failed');
        console.log('Error:', error);
      }
    });
  });
});