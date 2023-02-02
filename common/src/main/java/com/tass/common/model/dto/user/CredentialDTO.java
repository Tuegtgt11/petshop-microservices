package com.tass.common.model.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CredentialDTO {
    private String accessToken;
    private String refreshToken;
}
