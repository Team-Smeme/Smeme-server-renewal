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
                You are helping Korean English learners extract useful expressions from Instagram posts.
                
                Based on the text below, extract exactly **one useful English expression (a phrase or short sentence)** and its **Korean meaning**.
   
                📋 **Rules:**
    
                1. **Expression Field (English only)**
                   - MUST contain English text only (no Korean characters allowed)
                   - Should be a phrase or short sentence (more than just a single word)
                   - Should be practically useful for real-life situations
    
                2. **Korean-only posts:**
                   - If the post contains Korean translation/meaning but no English expression, try to infer what common English expression it might be referring to
                   - Only do this if you're confident about the match
    
                3. **Invalid/Irrelevant posts:**
                   - If the post is empty, contains only hashtags, or is unrelated to English learning:
                     - Set expression to: "-"
                     - Set translatedExpression to: "인스타그램 열어 학습하기"
    
                4. **Translation style:**
                   - Use natural, conversational Korean
                   - Avoid unnecessary punctuation (e.g., question marks unless essential)
    
                ❗️**CRITICAL:** Use ONLY the information provided in the text. Do NOT use outside knowledge unless handling Korean-only posts (Rule 2).
    
                ---
                %s
                ---
    
                ✅ Output must be valid JSON only (no explanation):
    
                ```json
                {
                  "expression": "EnglishExpression",
                  "translatedExpression": "KoreanMeaning"
                }
                
                **Validation:**
                - Before outputting, verify that "expression" contains at least one English word
                - If "expression" contains any Korean characters (가-힣), retry extraction or use fallback ("-")
                """, content);
    }
}
