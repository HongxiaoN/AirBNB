# Airbnb Data Simulation Project

## Authors:
- Hongxiao Niu
- Yitong Xu

## Project Overview

This project simulates a simplified version of the Airbnb platform, focusing on data structures and relational database design. The project models users, listings, and reservations with several key functionalities that mimic real-world operations such as creating listings, making reservations, and managing user data.

### Entity Sets
- **users**: Stores user information such as username, email, and other relevant details.
- **lists**: Stores details of Airbnb listings such as house type, location, and amenities.

### Relationships
- **changeprice**: Tracks price changes for listings.
- **owns**: Tracks the ownership of listings by users.
- **reservations**: Tracks the reservations made by renters and hosts.

### Conceptual Features
- Each table includes a `status` attribute, which helps track if a user, listing, or reservation is active or deleted.
- Reservation conflicts and cancellations are handled using `status` flags, ensuring that data is retained without permanent deletions.
- The current date is fixed to **January 1st, 2022 (20220101)**, which simplifies future and past reservation handling.

### Assumptions
- Users can be both hosts and renters.
- Deleting a user does not permanently delete their data; instead, their `status` is updated.
- Latitude, longitude, and postal code formats are simplified for testing and simulation purposes.

[Complete Documentation and User manual](https://drive.google.com/file/d/1rN6A2IenSshPrOx8f3G-fuhqBJRx_9GP/view?usp=sharing)
