package com.api.focvs.focvs_api.projections.account;

import com.api.focvs.focvs_api.domain.user.User;

public interface AccountMinProjection {
    String getId();
    String getEmail();
    String getPassword();
    User getUser();
}
