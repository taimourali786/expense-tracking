package com.cotech.helpdesk.model.user;

import com.cotech.helpdesk.jpa.userauth.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAuth {

    private String email;
    private String password;
    private User user;
    private Role role;
}
