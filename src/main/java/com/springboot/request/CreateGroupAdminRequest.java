package com.springboot.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by User: mehak.goyal 😈 on Date: 1/25/20 12:03 AM
 */
@Data
@NoArgsConstructor
public class CreateGroupAdminRequest {

    private List<String> wa_ids;

}
