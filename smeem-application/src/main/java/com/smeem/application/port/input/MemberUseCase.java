package com.smeem.application.port.input;

import com.smeem.application.port.input.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.application.port.input.dto.request.member.UpdateMemberRequest;
import com.smeem.application.port.input.dto.response.member.RetrieveMemberResponse;
import com.smeem.application.port.input.dto.response.member.RetrievePerformanceResponse;
import com.smeem.application.port.input.dto.response.member.UpdateMemberResponse;
import com.smeem.application.port.input.dto.response.member.UsernameDuplicatedResponse;

public interface MemberUseCase {
    UpdateMemberResponse updateMember(long memberId, UpdateMemberRequest request);
    RetrieveMemberResponse retrieveMember(long memberId);
    UsernameDuplicatedResponse checkUsernameDuplicated(String username);
    void updateMemberHasPush(long memberId, UpdateMemberHasPushAlarmRequest request);
    RetrievePerformanceResponse retrieveMemberPerformance(long memberId);
    void checkAttendance(long memberId);
}
