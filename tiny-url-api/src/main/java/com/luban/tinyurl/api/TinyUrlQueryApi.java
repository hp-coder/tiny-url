package com.luban.tinyurl.api;

/**
 * @author hp
 */
public interface TinyUrlQueryApi {

    String PATH = "tingurl/query";

    String queryByCode(String code);

    String accessByCode(String code);
}
