
# githubdemo

Short application to retrieve user's repositories using GitHub API.


## API Reference

#### Get all items

```http
  GET /api/repos/${username}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. User's username |


#### Get the user
```http
  GET /api/user/${username}
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. User's username |

## Technology stack 
* Spring Boot
* Java 17
* WebClient
* GitHub API
