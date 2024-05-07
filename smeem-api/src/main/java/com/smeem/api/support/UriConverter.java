package com.smeem.api.support;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class UriConverter {

    public static URI getURI(String path, long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(path)
                .buildAndExpand(id)
                .toUri();
    }
}
