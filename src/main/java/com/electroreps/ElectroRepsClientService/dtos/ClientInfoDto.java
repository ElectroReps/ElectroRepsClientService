package com.electroreps.ElectroRepsClientService.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientInfoDto {
    private String name;
    private String email;
}
