package in.phani.springai.controller;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.messages.Media;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class ImageController {

    private final ChatClient chatClient;

    ImageController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/describe-image")
    public String describeNoTextImage() throws IOException {
        byte[] imageData = new ClassPathResource("/images/confusing-Knick-knacks.jpeg").getContentAsByteArray();
        UserMessage userMessage = new UserMessage("Can you please explain what you see in the following image?",
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG,imageData)));
        var response = chatClient
                .call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();
    }

    @GetMapping("/describe-image-with-non-english-text")
    public String describeImageWithNonEnglishText() throws IOException {
        byte[] imageData = new ClassPathResource("/images/kannada.jpeg").getContentAsByteArray();
        UserMessage userMessage = new UserMessage("Can you please explain what you see in the following image?",
                List.of(new Media(MimeTypeUtils.IMAGE_JPEG,imageData)));
        var response = chatClient
                .call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();
    }


    @GetMapping("/describe-code")
    public String describeCode() throws IOException {
        byte[] imageData = new ClassPathResource("/images/composefile.png").getContentAsByteArray();
        UserMessage userMessage = new UserMessage("Can you please explain the code in the following image?",
                List.of(new Media(MimeTypeUtils.IMAGE_PNG,imageData)));
        var response = chatClient
                .call(new Prompt(userMessage));
        return response.getResult().getOutput().getContent();
    }
}
