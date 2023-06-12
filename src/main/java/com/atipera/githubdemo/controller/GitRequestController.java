package com.atipera.githubdemo.controller;

import com.atipera.githubdemo.model.GitHubRepository;
import com.atipera.githubdemo.response.error.ErrorResponse;
import com.atipera.githubdemo.exception.UserNotFoundException;
import com.atipera.githubdemo.model.User;
import com.atipera.githubdemo.response.GitHubRepoResponse;
import com.atipera.githubdemo.service.GitHubService;
import com.atipera.githubdemo.utils.ResponseParser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GitRequestController {
    private final GitHubService gitHubService;
    private final ResponseParser parser;

    @GetMapping("/repos/{username}")
    public ResponseEntity<List<GitHubRepoResponse>> getRepositories(@PathVariable String username) {
        List<GitHubRepoResponse> response = parser.toResponse(gitHubService.getUserRepositories(username));

        return ResponseEntity.ok()
                .body(response);
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        User user = gitHubService.getUser(username)
                .orElseThrow(() -> new UserNotFoundException("Did not find the appropriate user"));

        return ResponseEntity.ok().body(user);
    }
}
