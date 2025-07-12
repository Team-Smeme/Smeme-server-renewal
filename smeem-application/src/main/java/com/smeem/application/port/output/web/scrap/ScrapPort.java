package com.smeem.application.port.output.web.scrap;

import lombok.NonNull;

public interface ScrapPort {
    @NonNull ScrapInfo scrap(@NonNull String url);
}
