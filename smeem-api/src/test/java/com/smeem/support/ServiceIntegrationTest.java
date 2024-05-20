package com.smeem.support;

import com.smeem.api.SmemeServerRenewalApplication;
import com.smeem.api.auth.jwt.TokenProvider;
import com.smeem.api.auth.jwt.TokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;

import static org.springframework.test.context.TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS;

@SpringBootTest(classes = SmemeServerRenewalApplication.class)
@TestExecutionListeners(mergeMode = MERGE_WITH_DEFAULTS)
@ActiveProfiles("test")
public abstract class ServiceIntegrationTest {

    @Autowired
    TokenProvider tokenProvider;

    @Autowired
    TokenValidator tokenValidator;
}
