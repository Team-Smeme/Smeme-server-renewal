package com.smeem.notification.firebase;

import com.smeem.external.firebase.dto.request.NotificationSingleRequest;
import com.smeem.external.firebase.dto.request.NotificationMulticastRequest;

public interface NotificationService {
    void sendMessage(final NotificationSingleRequest request);
    void sendMessages(final NotificationMulticastRequest request);
}
