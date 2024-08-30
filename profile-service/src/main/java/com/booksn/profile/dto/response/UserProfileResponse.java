package com.booksn.profile.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserProfileResponse {
    String id;
    String username;
    String email;
    String firstName;
    String lastName;
    LocalDate dob;
    String city;
}
