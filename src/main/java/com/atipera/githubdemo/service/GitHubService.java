package com.atipera.githubdemo.service;

import com.atipera.githubdemo.exception.UserNotFoundException;
import com.atipera.githubdemo.model.Branch;
import com.atipera.githubdemo.model.GitHubRepository;
import com.atipera.githubdemo.model.User;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GitHubService {
    private final String GITHUB_API_BASE_URL = "https://api.github.com";
    private final WebClient webClient;
    private final RestTemplate restTemplate;
    public Optional<User> getUser(String username) {
        try {
            String url = "https://api.github.com/users/" + username;
            User user = restTemplate.getForObject(url, User.class);
            return Optional.ofNullable(user);
        } catch (HttpClientErrorException.NotFound ex) {
            return Optional.empty();
        }
    }

    public List<GitHubRepository> getUserRepositories(String username) {
        String uri = GITHUB_API_BASE_URL + String.format("/users/%s/repos", username);

        if(getUser(username).isEmpty()) {
            throw new UserNotFoundException("Did not find the user with: " + username);
        }

        /*
        ResponseEntity<GitHubRepository[]> responseEntity =
                restTemplate.getForEntity(uri, GitHubRepository[].class);

        List<GitHubRepository> gitRepos = Arrays.stream(responseEntity.getBody()).map(repo -> {
            repo.setBranches(getBranches(username, repo.getName()));
            return repo;
        })
                .toList();

        return gitRepos;
        */

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(GitHubRepository.class)
                .filter(repo -> !repo.isFork())
                .flatMap(repo -> getBranches(username, repo.getName())
                        .map(branches -> {
                            repo.setBranches(branches);
                            return repo;
                        }))
                .collectList()
                .block();
    }

    private Flux<List<Branch>> getBranches(String ownerLogin, String repoName) {
        String uri = GITHUB_API_BASE_URL + String.format("/repos/%s/%s/branches",
                ownerLogin,
                repoName);

        return webClient.get()
                .uri(uri)
                .retrieve()
                .bodyToFlux(Branch.class)
                .collectList()
                .flux();
    }

}
