package com.smeem.http.controller.docs;

import com.smeem.http.controller.dto.request.member.UpdateMemberHasPushAlarmRequest;
import com.smeem.http.controller.dto.request.member.UpdateMemberRequest;
import com.smeem.http.controller.dto.response.SmeemResponse;
import com.smeem.http.controller.dto.response.member.RetrieveMemberResponse;
import com.smeem.http.controller.dto.response.member.RetrievePerformanceResponse;
import com.smeem.http.controller.dto.response.member.UpdateMemberResponse;
import com.smeem.http.controller.dto.response.member.UsernameDuplicatedResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.security.Principal;

@Tag(name = "MemberApi", description = "회원(Member) 관련 Api 입니다.")
public interface MemberApiDocs {

    @Operation(summary = "회원 정보 수정 api", description = "회원의 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = UpdateMemberResponse.class)))
    })
    SmeemResponse<UpdateMemberResponse> updateMember(
            @Parameter(hidden = true) Principal principal,
            @RequestBody(
                    description = "회원 정보 수정 Request Body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateMemberRequest.class))
            ) UpdateMemberRequest request
    );

    @Operation(summary = "회원 정보 조회 api", description = "회원의 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrieveMemberResponse.class)))
    })
    SmeemResponse<RetrieveMemberResponse> retrieveMember(@Parameter(hidden = true) Principal principal);

    @Operation(summary = "닉네임 중복 체크 api", description = "닉네임의 중복 여부를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = UsernameDuplicatedResponse.class)))
    })
    SmeemResponse<UsernameDuplicatedResponse> checkUsernameDuplicated(
            @Parameter(
                    name = "name",
                    description = "닉네임",
                    required = true,
                    in = ParameterIn.QUERY
            ) String name
    );

    @Operation(summary = "푸시알림 수신 정보 수정 api", description = "회원의 푸시알림 수신 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success")
    })
    SmeemResponse<?> updateMemberHasPushAlarm(
            @Parameter(hidden = true) Principal principal,
            @RequestBody(
                    description = "푸시알림 동의 정보 수정 Request Body",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UpdateMemberHasPushAlarmRequest.class))
            ) UpdateMemberHasPushAlarmRequest request
    );

    @Operation(summary = "성과요약 조회 api", description = "회원의 성과요약을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success",
                    content = @Content(schema = @Schema(implementation = RetrievePerformanceResponse.class)))
    })
    SmeemResponse<RetrievePerformanceResponse> retrieveSummary(@Parameter(hidden = true) Principal principal);

    @Operation(summary = "출석체크 api", description = "회원이 출석체크합니다.")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK success")
    })
    SmeemResponse<?> checkAttendance(@Parameter(hidden = true) Principal principal);
}
