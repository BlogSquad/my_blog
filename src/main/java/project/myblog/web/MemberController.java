package project.myblog.web;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import project.myblog.web.dto.MemberRequest;

import java.net.URI;

@RestController
public class MemberController {
    @PostMapping(value = "/members", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createMember(@RequestBody MemberRequest memberRequest) {
        return ResponseEntity.created(URI.create("/members/" + 1L)).build();
    }
}
