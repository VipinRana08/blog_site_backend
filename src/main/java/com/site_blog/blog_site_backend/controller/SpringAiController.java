package com.site_blog.blog_site_backend.controller;

import java.util.Map;

import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai")
@CrossOrigin
public class SpringAiController {

    private final OpenAiChatModel chatModel;

    public SpringAiController(OpenAiChatModel chatModel ) {
        this.chatModel = chatModel;
    }

    @PostMapping("/chat")
    public Map<String, String> chat(@RequestBody Map<String, String> body) {
        String userMessage = body.get("message");
        String aiResponse = chatModel.call(userMessage);
        return Map.of("response", aiResponse);
    }
}
