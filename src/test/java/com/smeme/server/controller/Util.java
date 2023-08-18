package com.smeme.server.controller;

import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import com.epages.restdocs.apispec.HeaderDescriptorWithType;
import com.epages.restdocs.apispec.ResourceSnippetParameters;

public class Util {

	public static FieldDescriptor makeFieldDescriptor(String name, JsonFieldType type, String description) {
		return fieldWithPath(name).type(type).description(description);
	}

	public static void doDocument(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		ArrayList<HeaderDescriptorWithType> requestHeaders,
		ArrayList<FieldDescriptor> requestFields,
		ArrayList<FieldDescriptor> responseFields,
		ResultMatcher status
	) throws Exception {
		resultActions
			.andDo(document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()),
				resource(ResourceSnippetParameters.builder()
					.tag(tag)
					.description(description)
					.requestHeaders(requestHeaders)
					.requestFields(requestFields)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	public static void doDocument(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		ArrayList<FieldDescriptor> requestFields,
		ArrayList<FieldDescriptor> responseFields,
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
					.requestFields(requestFields)
					.responseFields(responseFields)
					.build())))
			.andExpect(status);
	}

	public static void doDocument(
		ResultActions resultActions,
		String identifier,
		String tag,
		String description,
		ArrayList<FieldDescriptor> responseFields,
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
					.responseFields(
						fieldWithPath("success").type(BOOLEAN).description("응답 성공 여부"),
						fieldWithPath("message").type(STRING).description("응답 메시지"),
						fieldWithPath("data").type(NULL).description("응답 데이터")
					)
					.build())))
			.andExpect(status().isOk());
	}
}
