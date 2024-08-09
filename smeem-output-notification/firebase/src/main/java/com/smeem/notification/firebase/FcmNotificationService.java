package com.smeem.notification.firebase;

import com.google.firebase.messaging.*;
import com.smeem.common.exception.ExceptionCode;
import com.smeem.common.exception.SmeemException;
import com.smeem.notification.firebase.dto.request.NotificationMulticastRequest;
import com.smeem.notification.firebase.dto.request.NotificationRequest;
import com.smeem.notification.firebase.dto.request.NotificationSingleRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationService {
    private final FirebaseMessaging firebaseMessaging;

    public void sendMessage(NotificationSingleRequest request) {
        try {
            val message = request.buildMessage().setApnsConfig(getApnsConfig(request)).build();
            firebaseMessaging.sendAsync(message);
        } catch (RuntimeException exception) {
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE, "FCM Service");
        }
    }

    public void sendMessages(NotificationMulticastRequest request) {
        try {
            val messages = request.buildSendMessage().setApnsConfig(getApnsConfig(request)).build();
            firebaseMessaging.sendMulticastAsync(messages);
        } catch (RuntimeException exception) {
            throw new SmeemException(ExceptionCode.SERVICE_AVAILABLE, "FCM Service");
        }
    }

    private ApnsConfig getApnsConfig(NotificationRequest request) {
        val alert = ApsAlert.builder().setTitle(request.title()).setBody(request.body()).build();
        val aps = Aps.builder().setAlert(alert).setSound("default").build();
        return ApnsConfig.builder().setAps(aps).build();
    }
}
