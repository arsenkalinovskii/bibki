package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Array;
import java.util.List;
import java.util.ArrayList;
@Controller
@RestController
public class ApiController {
    private final List<String> messages = new ArrayList<>();

    // curl -X GET localhost:8080/messages
    @GetMapping("messages")
    public ResponseEntity<List<String>> getMessages(
            @RequestParam(value = "subtext", required = false) String subtext) {
        List<String> sublist = new ArrayList<>();
        if(subtext == null)
            return ResponseEntity.ok(messages);
        for(int i = 0; i < messages.size(); i++){
            if(messages.get(i).startsWith(subtext))
                sublist.add(messages.get(i));
        }
        return ResponseEntity.ok(sublist);
    }
    // curl localhost:8080/messages/search/text
    @GetMapping("messages/search/{text}")
    public ResponseEntity<Integer> messageStartsWith(@PathVariable("text") String string){
        Integer i = 0;
        for(String str : messages){
            if(str.contains(string))
                break;
            i++;
        }
        return ResponseEntity.ok(i);
    }
    // curl localhost:8080/messages/count
    @GetMapping("messages/count")
    public ResponseEntity<Integer> countOfMessages(){
            return ResponseEntity.ok((Integer)messages.size());
    }
    // curl -X POST localhost:8080/messages/2/create -H "Content-type: text/plain" -d "newdata"
    @PostMapping("messages/{index}/create")
    public ResponseEntity<Void> createMessageWithIndex(
            @PathVariable("index") int index,
            @RequestBody String text){
        messages.set(index, text);
        return ResponseEntity.accepted().build();
    }
    // curl -X POST localhost:8080/messages -d "text=something"
    @PostMapping("messages")
    public ResponseEntity<Void> addMessage(@RequestBody String text) {
        messages.add(text);
        return ResponseEntity.accepted().build();
    }
    // curl -X DELETE localhost:8080/messages/0
    @DeleteMapping("messages/{index}")
    public ResponseEntity<Void> deleteText(@PathVariable("index") Integer
                                                   index) {
        messages.remove((int) index);
        return ResponseEntity.noContent().build();
    }
    //curl -X DELETE localhost:8080/messages/search/text
    @DeleteMapping("messages/search/{text}")
    public ResponseEntity<Void> deleteTextsWithSubstring(@PathVariable("text") String substr){
        for(int i = 0; i < messages.size(); i++){
            if(messages.get(i).contains(substr))
                messages.remove(i);
        }
        return ResponseEntity.noContent().build();
    }
    // curl -X PUT localhost:8080/messages/0 -H "Content-type: text/plain" -d "newestdata"
    @PutMapping("messages/{index}")
    public ResponseEntity<Void> updateMessage(
            @PathVariable("index") Integer i,
            @RequestBody String message) {
        messages.remove((int) i);
        messages.add(i, message);
        return ResponseEntity.accepted().build();
    }

}
