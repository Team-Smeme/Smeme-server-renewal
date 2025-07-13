package com.smeem.output.web.gpt.adapter.template;

public class PromptTemplate {
    public static String getCorrectionPrompt(String content) {
        return String.format("""
                Paragraph : %s
                
                Please correct the following English sentences, determine whether each sentence is correct or incorrect, and generate a JSON response in the specified format.
                Make sure to keep the order of the sentences.
                The JSON format should be as follows:

                JSON response description:
                - original_sentence: The original sentence exactly as it appears.
                - corrected_sentence: The corrected sentence (if correct, repeat the original).
                - reason: The reason for the correction, explained in Korean.
                - is_correct: Whether the sentence has been corrected. Set to true if corrected, and false if unchanged.
                """, content);
    }

    public static String getExpressionPrompt(String content) {
        return String.format("""
                Based on the text below, extract exactly **one useful English expression (a phrase or short sentence)** and its **Korean meaning** that would help a Korean English learner.
                
                ❗️Use only the information in the text. Do NOT use outside knowledge.
                
                The expression should:
                - Be more than just a word (preferably a phrase or short sentence).
                - Be practically useful for real-life situations.
                
                When translating to Korean:
                - Translate in a natural and commonly spoken form.
                - Do **not** include punctuation like question marks (?) unless it's truly necessary for the Korean sentence.
                
                ---
                %s
                ---
                
                ✅ Your output must be in the following **JSON format**, with no explanation — just the JSON block.
                
                ```json
                {
                  "expression": "EnglishExpression",
                  "translatedExpression": "KoreanMeaning"
                }
                ```
                """, content);
    }
}
