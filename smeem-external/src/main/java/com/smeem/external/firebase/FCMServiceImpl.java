package com.smeem.external.firebase;

import com.google.firebase.messaging.*;
import com.smeem.external.firebase.dto.request.NotificationRequest;
import com.smeem.external.firebase.dto.request.NotificationSingleRequest;
import com.smeem.external.firebase.dto.request.NotificationMulticastRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class FCMServiceImpl implements FCMService {

    private final FirebaseMessaging firebaseMessaging;

    public void sendMessage(final NotificationSingleRequest request) {
        val message = request.buildMessage().setApnsConfig(getApnsConfig(request)).build();
        firebaseMessaging.sendAsync(message);
    }

    public void sendMessages(final NotificationMulticastRequest request) {
        val messages = request.buildSendMessage().setApnsConfig(getApnsConfig(request)).build();
        firebaseMessaging.sendMulticastAsync(messages);
    }

    private ApnsConfig getApnsConfig(NotificationRequest request) {
        val alert = ApsAlert.builder().setTitle(request.title()).setBody(request.body()).build();
        val aps = Aps.builder().setAlert(alert).setSound("default").build();
        return ApnsConfig.builder().setAps(aps).build();
    }
}
