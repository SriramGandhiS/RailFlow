# Domain Gap Analysis & Implementation Roadmap

## Overview
During the autonomous recovery phase, multiple entity classes were generated as skeleton classes (scaffolding) to satisfy Java compiler imports and database constraints quickly. This document details the gap between the currently implemented stubs and the expected domain models required for a production-ready railway reservation system.

---

## 1. Domain Gap Analysis

### Auth
*   **Current Fields**: `Long id`, `LocalDateTime createdAt`
*   **Expected Fields**: `String email`, `String passwordHash`, `Role role`, `String token`, `LocalDateTime expiresAt`, `boolean revoked`
*   **Missing Fields**: `email`, `passwordHash`, `roleId`, `token`, `expiresAt`, `revoked`
*   **Services Affected**: `AuthService`, `UserService`, `JwtUtil`, `JwtAuthenticationFilter`
*   **Repositories Affected**: `AuthRepository`, `UserRepository`
*   **Migration Changes Required**: Modify table `auths` to include security credentials and token expiration properties.

### Admin
*   **Current Fields**: `Long id`, `LocalDateTime createdAt`
*   **Expected Fields**: `User user` (relationship), `String department`, `String clearanceLevel`, `boolean isActive`
*   **Missing Fields**: `userId`, `department`, `clearanceLevel`, `isActive`
*   **Services Affected**: `AdminService`, `UserService`
*   **Repositories Affected**: `AdminRepository`
*   **Migration Changes Required**: Link the `admins` table with `users` via a foreign key constraint and add metadata attributes.

### Booking
*   **Current Fields**: `Long id`, `LocalDateTime createdAt`
*   **Expected Fields**: `User user`, `Schedule schedule`, `String pnr`, `String status` (CONFIRMED, CANCELLED, WAITING_LISTED), `double totalFare`, `LocalDateTime bookedAt`
*   **Missing Fields**: `userId`, `scheduleId`, `pnr`, `status`, `totalFare`, `bookedAt`
*   **Services Affected**: `BookingService`, `TrainService`, `PassengerService`
*   **Repositories Affected**: `BookingRepository`
*   **Migration Changes Required**: Align class schema with the database's existing `bookings` table defined in `V1__init.sql`.

### DistributedLock
*   **Current Fields**: `Long id`, `LocalDateTime createdAt`
*   **Expected Fields**: `String lockKey` (Unique), `String lockValue`, `LocalDateTime expireTime`, `LocalDateTime acquiredAt`
*   **Missing Fields**: `lockKey`, `lockValue`, `expireTime`, `acquiredAt`
*   **Services Affected**: `DistributedLockService`
*   **Repositories Affected**: `DistributedLockRepository`
*   **Migration Changes Required**: Create a database table `distributed_locks` with unique constraints on the lock key.

### Search
*   **Current Fields**: `Long id`, `LocalDateTime createdAt`
*   **Expected Fields**: `Station sourceStation`, `Station destinationStation`, `LocalDate travelDate`, `LocalDateTime searchTime`, `int resultsCount`
*   **Missing Fields**: `sourceStationId`, `destinationStationId`, `travelDate`, `searchTime`, `resultsCount`
*   **Services Affected**: `SearchService`, `StationService`
*   **Repositories Affected**: `SearchRepository`
*   **Migration Changes Required**: Create an index on `source_station_id`, `destination_station_id`, and `travel_date` for query performance optimization.

### WaitingList
*   **Current Fields**: `Long id`, `String name`, `LocalDateTime createdAt`
*   **Expected Fields**: `Booking booking`, `Schedule schedule`, `String seatClass` (SLEEPER, AC, GENERAL), `int queuePosition`, `LocalDateTime enteredAt`
*   **Missing Fields**: `bookingId`, `scheduleId`, `seatClass`, `queuePosition`, `enteredAt`
*   **Services Affected**: `WaitingListService`, `BookingService`
*   **Repositories Affected**: `WaitingListRepository`
*   **Migration Changes Required**: Align model structure with the database's `waiting_list_entries` table defined in `V1__init.sql`.

---

## 2. Implementation Roadmap

1.  **Phase A: Database Schema Alignment (Booking & WaitingList)**: Fleshing out attributes for Booking and WaitingList models to match existing PostgreSQL schema defined in migration files.
2.  **Phase B: Security Models Integration (Auth & Admin)**: Link authentication/authorization flows with actual user records.
3.  **Phase C: Distributed Systems Setup (DistributedLock & Search)**: Add performance logging, locks audit logging, and cache index support.
