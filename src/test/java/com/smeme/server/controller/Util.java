package com.smeme.server.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import com.epages.restdocs.apispec.ParameterDescriptorWithType;
import com.epages.restdocs.apispec.ResourceSnippetParameters;

public class Util {

	//TODO: 메서드명 정리

	public static List<FieldDescriptor> getNullDataResponseFields() {
		List<FieldDescriptor> responseFields = new ArrayList<>();
		responseFields.add(fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"));
		responseFields.add(fieldWithPath("message").type(STRING).description("응답 메시지"));
		responseFields.add(fieldWithPath("data").type(NULL).description("응답 데이터"));
		return responseFields;
	}

	/**
	 * Request Header, Request Body, Response Header, Response Body
	 */
	public static void doDocument(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<HeaderDescriptorWithType> requestHeaders,
		List<FieldDescriptor> requestFields,
		List<HeaderDescriptorWithType> responseHeaders,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.requestHeaders(requestHeaders)
					.requestFields(requestFields)
					.responseHeaders(responseHeaders)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * Request Body, Response Body
	 */
	public static void doDocument(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<FieldDescriptor> requestFields,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.requestFields(requestFields)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * Response Body
	 */
	public static void doDocument(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(
				identifier,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * [GET] PathParam, Response Body
	 */
	public static void getDocumentPathParam(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<ParameterDescriptorWithType> parameters,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(
				identifier,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.pathParameters(parameters)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * [GET] QueryParam(RequestParam), Response Body
	 */
	public static void getDocumentQueryParam(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<ParameterDescriptorWithType> parameters,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(
				identifier,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.queryParameters(parameters)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * [PATCH] PathParam, Request Body, Response Body
	 */
	public static void patchDocumentPathParam(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<ParameterDescriptorWithType> parameters,
		List<FieldDescriptor> requestFields,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(
				identifier,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.pathParameters(parameters)
					.requestFields(requestFields)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * [DELETE] PathParam, Response Body
	 */
	public static void deleteDocumentPathParam(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		List<ParameterDescriptorWithType> parameters,
		List<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(
				identifier,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.pathParameters(parameters)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	/**
	 * Response Body {"success": true, "message": "~", "data": null}
 	 */
	public static void doDocumentNullData(
		ResultActions resultActions, String identifier, String tag, String description
	) throws Exception {
		resultActions
			.andDo(document(
				identifier,
				preprocessRequest(prettyPrint()),
				preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.responseFields(getNullDataResponseFields())
					.build())))
			.andExpect(status().isOk());
	}
}
