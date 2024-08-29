package com.hp.tinyurl.api;

import com.hp.common.base.model.Response;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author hp
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTinyUrlResponse implements Response {

    private String code;
}
