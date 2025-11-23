document.addEventListener('DOMContentLoaded', function() {
  let measurementIndex = 0;
  const measurementsDiv = document.getElementById('measurements');
  const genderSelect = document.getElementById('gender');

  // Function to create a new measurement row
  function createMeasurementRow(index, type = '') {
    const newMeasurementRow = document.createElement('div');
    newMeasurementRow.classList.add('measurement-row');
    newMeasurementRow.innerHTML = `
      <label>Type:</label>
      <input type="text" name="measurements[${index}].type" value="${type}" required>
      <label>Value:</label>
      <input type="text" name="measurements[${index}].value" required>
      <button type="button" class="remove-measurement">Remove</button>
    `;
    return newMeasurementRow;
  }

  // Function to load measurements based on gender
  function loadMeasurements(gender) {
    measurementsDiv.innerHTML = '';
    measurementIndex = 0;

    let types;
    if (gender === 'Male') {
      types = ['Neck', 'Head', 'Wrist', 'Length', 'Waist', 'Leg Round', 'Arm Round'];
    } else if (gender === 'Female') {
      types = ['Neck', 'Head', 'Wrist', 'Length', 'Waist', 'Leg Round', 'Arm Round', 'Hip', 'Chest'];
    } else {
      types = ['Type 1', 'Type 2', 'Type 3', 'Type 4', 'Type 5']; // default
    }

    for (let i = 0; i < types.length; i++) {
      const newMeasurementRow = createMeasurementRow(measurementIndex, types[i]);
      measurementsDiv.appendChild(newMeasurementRow);
      measurementIndex++;

      // Add event listener for remove button
      newMeasurementRow.querySelector('.remove-measurement').addEventListener('click', function() {
        measurementsDiv.removeChild(newMeasurementRow);
      });
    }
  }

  // Load default measurements
  loadMeasurements('');

  // Add event listener for gender change
  genderSelect.addEventListener('change', function() {
    const selectedGender = genderSelect.value;
    loadMeasurements(selectedGender);
  });

  // Add event listener for add measurement button
  document.getElementById('addMeasurement').addEventListener('click', function() {
    const newMeasurementRow = createMeasurementRow(measurementIndex);
    measurementsDiv.appendChild(newMeasurementRow);
    measurementIndex++;

    // Add event listener for remove button
    newMeasurementRow.querySelector('.remove-measurement').addEventListener('click', function() {
      measurementsDiv.removeChild(newMeasurementRow);
    });
  });
});

document.getElementById('save-btn').addEventListener('click', function(event) {
        event.preventDefault();
        Swal.fire({
            title: 'Are you sure?',
            text: 'You are about to save customer details!',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Yes, save it!'
        }).then((result) => {
            if (result.isConfirmed) {
                document.getElementById('save-form').submit();
            }
        });
    });