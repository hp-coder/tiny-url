package com.luban.tinyurl.api;

import com.luban.common.base.model.Response;

/**
 * @author hp
 */
public record CreateTinyUrlResponse(String code) implements Response {
}
