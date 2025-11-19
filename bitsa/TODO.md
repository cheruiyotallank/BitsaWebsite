# TODO: Fix Test Compilation Errors

## Overview
Fix 48 compilation errors across 8 test files by updating tests to match current implementation.

## Key Issues to Address
- Replace `Role` enum usage with `UserRole` enum
- Replace `String` status with `RegistrationStatus` enum
- Change `LocalDateTime` to `Instant` for timestamps
- Update method signatures to match implementations
- Add missing repository methods if required

## Test Files to Fix
- [ ] EventRegistrationServiceTests.java
- [ ] EventControllerIntegrationTests.java
- [ ] BlogControllerIntegrationTests.java
- [ ] RatingServiceTests.java
- [ ] CommentServiceTests.java
- [ ] EventServiceTests.java
- [ ] BlogServiceTests.java

## Steps
1. Update enum imports and usage
2. Fix type mismatches (LocalDateTime -> Instant)
3. Update method calls to match service implementations
4. Add missing repository methods if needed
5. Run compilation tests to verify fixes
