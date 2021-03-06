package telran.ashkelon2018.forum.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2018.forum.dto.UserProfileDto;
import telran.ashkelon2018.forum.dto.UserRegDto;
import telran.ashkelon2018.forum.service.AccountService;

@RestController
@RequestMapping("/account")
// all end-points begin with /account and then followed by written below end-points
public class AccountManagementController {
	@Autowired
	AccountService accountService;
	
	@PostMapping("/admin/hgfddRdg53bmm+ddsff6g64chhc")
	public UserProfileDto registerAdmin(@RequestBody UserRegDto userRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountService.addAdmin(userRegDto, token);
	}

	
	@PostMapping
	public UserProfileDto register(@RequestBody UserRegDto userRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountService.addUser(userRegDto, token);
	}
	
	@GetMapping
	public UserProfileDto update(@RequestBody UserRegDto userRegDto, 
			@RequestHeader("Authorization") String token) {
		return accountService.editUser(userRegDto, token);
	}
	
	@DeleteMapping("/{login}")
	public UserProfileDto remove(@PathVariable String login, 
			@RequestHeader("Authorization") String token) {
		return accountService.removeUser(login, token);
	}

	@PutMapping("/{login}/{role}")
	public Set<String> addRole(@PathVariable String login, @PathVariable String role, 
			@RequestHeader("Authorization") String token){
		return accountService.addRole(login, role, token);
	};
	
	@DeleteMapping("/{login}/{role}")
	public Set<String> removeRole(@PathVariable String login, @PathVariable String role, 
			@RequestHeader("Authorization") String token){
		return accountService.removeRole(login, role, token);
	};
	
	@PutMapping("/password")
	public void changePassword(@RequestHeader("X-Authorization") String password, 
			@RequestHeader("Authorization") String token) {
		accountService.changePassword(password, token);
	};
	@GetMapping("/login")
	public UserProfileDto loginUser(@RequestHeader("Authorization") String token)
	{
		return accountService.loginUser(token);
	}
}
