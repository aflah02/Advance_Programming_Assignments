Two chief stakeholders, citizens and hospitals

### Hospitals

- Register themselves in Portal
- Upload their slots

### Citizens

- Register themselves on Portal
- Look up vaccination slots based on Hospitals in an area and a specific vaccine
- Should be able to book slots
- Query portal for vaccination details
- Query for next dose details

### Slots

- Day
- Vaccine Given
- Quantity in the slot

### Vaccine

- Name
- Number of Doses
- Gap Between Doses

### Citizen Registration

- Name
- Age
- They give themselves **unique** 12 Digit IDs

### Hospital Registration

- Name
- Pincode
- Program gives them **unique** 8 Digit ID

### COVIN Portal

- Details of Citizens, Hospitals, and Vaccines
- Vaccine Information must be added before Hospitals add their slots
- Once booked assuming Vaccination is instantaneous
- IMP: On a particular day and hospital, the  number of citizens registered for that slot does not exceed the maximum available slots, and  the chosen slot does not violate the stipulated duration between slots.