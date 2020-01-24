package com.springboot.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/25/20 1:16 AM
 */

@Data
@NoArgsConstructor
public class CreateAdminRequest {

    private String groupId;

    private String whatsappId;
}
