package com.api.focvs.focvs_api.dtos.user;

import com.api.focvs.focvs_api.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountDTO {
    private String id;
    private String email;
    private String name;
    private String imageUrl;
}
