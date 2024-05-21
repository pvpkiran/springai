package in.phani.springai.controller;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {
    private final ChatClient chatClient;

    ChatController(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @GetMapping("/chat")
    public String chat() {
        return chatClient.call("Name a phrase from mankuthimmana kagga");
    }


    @GetMapping("/ipl-history")
    private IplRecords iplHistory() {

        String promptMessage = """
                Give me the list of  ipl teams with number of times they have won the title in descending order.
                Also include the year they won
                {format}
                """;

        var outputParser = new BeanOutputConverter<>(IplRecords.class);
        String format = outputParser.getFormat();

        PromptTemplate promptTemplate = new PromptTemplate(promptMessage, Map.of("format",format));
        Prompt prompt = promptTemplate.create();
        Generation generation = chatClient.call(prompt).getResult();
        return outputParser.convert(generation.getOutput().getContent());
    }

    record IplRecord(String name, String wins, List<String> years){}
    record IplRecords(List<IplRecord> champions){}
}
