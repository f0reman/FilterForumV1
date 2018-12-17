package telran.ashkelon2018.forum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.dto.DatePeriodDto;
import telran.ashkelon2018.forum.dto.NewCommentDto;
import telran.ashkelon2018.forum.dto.NewPostDto;
import telran.ashkelon2018.forum.dto.PostUpdateDto;
import telran.ashkelon2018.forum.service.ForumService;

@RestController
@RequestMapping("/forum")
public class ForumController {
	
	@Autowired
	ForumService forumService;
	
	@PostMapping("/post") //owner
	public Post addNewPost(@RequestBody NewPostDto newPost) {
		return forumService.addNewPost(newPost);
	};
	
	@GetMapping("/post/{id}")
	public Post getPost(@PathVariable String id) {
		return forumService.getPost(id);
	};
	
	@DeleteMapping("/post/{id}") //owner,admin,moderator
	public Post removePost(@PathVariable String id) {
		return forumService.removePost(id);
	};
	
	@PutMapping("/post/update") //owner
	public Post updatePost(@RequestBody PostUpdateDto postUpdate) {
		return forumService.updatePost(postUpdate);
	};
	
	@PutMapping("/post/{id}/like") //any
	public boolean addLike(@PathVariable String id) {
		return forumService.addLike(id);
	};
	
	@PutMapping("/post/{id}/comment") //owner, admin, moderotor
	public Post addComment(@PathVariable String id, @RequestBody NewCommentDto newComment) {
		return forumService.addComment(id, newComment);
	};
	
	@GetMapping("/posts/tags") 
	public Iterable<Post> findPostsByTags(@RequestBody List<String> tags) {
		return forumService.findPostsByTags(tags);
	};
	
	@GetMapping("/posts/author/{author}")
	public Iterable<Post> findPostsByAuthor(@PathVariable String author){
		return forumService.findPostsByAuthor(author);
	};	
	
	@GetMapping("/posts/dates")
	public Iterable<Post> findPostsByDates(@RequestBody DatePeriodDto datesDto){
		return forumService.findPostsByDates(datesDto);
	};

}
