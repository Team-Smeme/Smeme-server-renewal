package com.smeem.external.firebase;

import com.smeem.external.firebase.dto.request.NotificationSingleRequest;
import com.smeem.external.firebase.dto.request.NotificationMulticastRequest;

public interface FcmService {
    void sendMessage(final NotificationSingleRequest request);
    void sendMessages(final NotificationMulticastRequest request);
}
