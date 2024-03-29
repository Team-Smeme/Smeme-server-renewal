package com.smeem.external.firebase;

import com.google.firebase.messaging.*;
import com.smeem.external.firebase.dto.request.NotificationRequest;
import com.smeem.external.firebase.dto.request.NotificationSingleRequest;
import com.smeem.external.firebase.dto.request.NotificationMulticastRequest;
import com.smeem.external.firebase.exception.FcmException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import static com.smeem.common.code.failure.FcmFailureCode.FCM_SERVICE_UNAVAILABLE;

@Service
@RequiredArgsConstructor
@Slf4j
public class FcmNotificationService implements NotificationService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMessage(final NotificationSingleRequest request) {
        try {
            val message = request.buildMessage().setApnsConfig(getApnsConfig(request)).build();
            firebaseMessaging.sendAsync(message);
        } catch (RuntimeException exception) {
            throw new FcmException(FCM_SERVICE_UNAVAILABLE, exception.getMessage());
        }
    }

    public void sendMessages(final NotificationMulticastRequest request) {
        try {
            val messages = request.buildSendMessage().setApnsConfig(getApnsConfig(request)).build();
            firebaseMessaging.sendMulticastAsync(messages);
        } catch (RuntimeException exception) {
            throw new FcmException(FCM_SERVICE_UNAVAILABLE, exception.getMessage());
        }
    }

    private ApnsConfig getApnsConfig(NotificationRequest request) {
        val alert = ApsAlert.builder().setTitle(request.title()).setBody(request.body()).build();
        val aps = Aps.builder().setAlert(alert).setSound("default").build();
        return ApnsConfig.builder().setAps(aps).build();
    }
}
