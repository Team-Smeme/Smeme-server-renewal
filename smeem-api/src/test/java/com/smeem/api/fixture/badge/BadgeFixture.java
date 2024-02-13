//package com.smeem.api.fixture.badge;
//
//import com.smeem.api.badge.controller.dto.response.BadgeListResponse;
//import com.smeem.api.badge.controller.dto.response.BadgeListResponse.BadgeTypeResponseDTO;
//import com.smeem.api.badge.controller.dto.response.BadgeResponse;
//import com.smeem.api.badge.service.dto.response.BadgeServiceResponse;
//import com.smeem.domain.badge.model.BadgeType;
//
//import java.util.List;
//import java.util.stream.Stream;
//
//import static com.smeem.domain.badge.model.BadgeType.COMBO;
//
//public class BadgeFixture {
//
//    private static final String BADGE_NAME = "연속 3일 일기 뱃지";
//    private static final Long BADGE_ID = 1L;
//    private static final BadgeType BADGE_TYPE = COMBO;
//    private static final String BADGE_IMAGE_URL = "https://m.s3.ap-northeast-2.amazonaws.com/badge/streak.png";
//
//    public static BadgeServiceResponse createBadgeResponseDTO() {
//        return BadgeServiceResponse.of()
//                .id(BADGE_ID)
//                .name(BADGE_NAME)
//                .type(BADGE_TYPE.toString())
//                .imageUrl(BADGE_IMAGE_URL)
//                .build();
//    }
//
//    public static BadgeListResponse createBadgeListResponseDTO() {
//        return new BadgeListResponse(createBadgeTypesResponse());
//    }
//
//    private static List<BadgeTypeResponseDTO> createBadgeTypesResponse() {
//        return Stream.iterate(1, i -> i + 1).limit(5)
//                .map(i -> createBadgeTypeResponse())
//                .toList();
//    }
//
//    private static BadgeTypeServiceResponse createBadgeTypeResponse() {
//        return new BadgeTypeResponseDTO(BADGE_TYPE, BADGE_NAME, createBadgeResponses());
//    }
//
//    private static List<BadgeListResponse.BadgeResponseDTO> createBadgeResponses() {
//        return Stream.iterate(1, i -> i + 1).limit(5)
//                .map(BadgeFixture::createBadgeResponse)
//                .toList();
//    }
//
//    private static BadgeListResponse.BadgeResponseDTO createBadgeResponse(int i) {
//        return BadgeListResponse.BadgeResponseDTO.builder()
//                .name(BADGE_NAME + i)
//                .type(BADGE_TYPE)
//                .imageUrl("https://...")
//                .build();
//    }
//
//}
