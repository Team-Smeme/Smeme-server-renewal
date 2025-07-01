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

    //todo. 테스트 필요
    public static String getExpressionPrompt(String content) {
        return String.format("""
                아래 텍스트를 참고해서 영어 학습자가 공부할 만한 '영어 단어 - 한국어 의미' 표현을 딱 하나만 추출해줘.
                ❗️다른 자료는 참고하지 말고, 반드시 아래 텍스트만 기반으로 판단해줘.
                                
                ---
                %s
                ---
                                
                ✅ 출력은 아래와 같은 JSON 형식으로 정확히 해줘. 설명 없이 JSON만 출력해.
                                
                ```json
                {
                  "english": "영어단어",
                  "korean": "한글의미"
                }
                ```
                """, content);
    }

    public static String getExpressionsPrompt(String content) {
        return String.format("""
                아래 텍스트를 참고해서 영어 학습자가 공부할 만한 '영어 단어 - 한국어 의미' 표현 쌍을 최대한 많이 추출해줘.
                ❗️다른 자료는 참고하지 말고, 반드시 아래 텍스트만 기반으로 판단해줘.
                
                ---
                %s
                ---
                
                ✅ 출력은 아래와 같은 **JSON 배열** 형식으로 정확히 해줘. 설명 없이 JSON만 출력해.
                
                ```json
                [
                  {
                    "english": "영어단어",
                    "korean": "한글의미"
                  },
                  ...
                ]
                """, content);
    }
}
