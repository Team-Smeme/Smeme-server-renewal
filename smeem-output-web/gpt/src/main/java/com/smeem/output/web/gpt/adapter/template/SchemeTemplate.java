package com.smeem.output.web.gpt.adapter.template;

public class SchemeTemplate {
    public static String getCorrectionScheme() {
        return """
                {
                    "type": "object",
                    "properties": {
                        "results": {
                            "type": "array",
                            "items": {
                                "type": "object",
                                "properties": {
                                    "original_sentence": {"type": "string"},
                                    "corrected_sentence": {"type": "string"},
                                    "reason": {"type": "string"},
                                    "is_corrected": {"type": "boolean"}
                                },
                                "required": ["original_sentence", "corrected_sentence", "reason", "is_corrected"],
                                "additionalProperties": false
                            }
                        }
                    },
                    "required": ["results"],
                    "additionalProperties": false
                }
        """;
    }

    public static String getExpressionScheme() {
        return """
                    {
                        "type": "object",
                        "properties": {
                            "expression": {
                                "type": "string",
                                "description": "A useful English word extracted from the input text."
                            },
                            "translatedExpression": {
                                "type": "string",
                                "description": "The Korean meaning of the extracted English word."
                            }
                        },
                        "required": ["expression", "translatedExpression"],
                        "additionalProperties": false
                    }
                """;
    }
}
