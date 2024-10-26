package com.smeem.output.web.gpt.adapter.template;

public class PromptTemplate {
    public static String getCorrectionPrompt(String content) {
        return String.format("""
        Paragraph : %s

        JSON response description:
        - original_sentence: The original sentence exactly as it appears.
        - corrected_sentence: The corrected sentence (if correct, repeat the original).
        - reason: The reason for the correction, explained in Korean.
        - is_correct: Whether the sentence has been corrected. Set to true if corrected, and false if unchanged.
        """, content);
    }
}
