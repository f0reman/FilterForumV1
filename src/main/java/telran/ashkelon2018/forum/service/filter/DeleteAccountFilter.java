package telran.ashkelon2018.forum.service.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.configuration.AccountConfiguration;
import telran.ashkelon2018.forum.configuration.AccountUserCredentials;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.UserAccount;

@Service
@Order(4)
public class DeleteAccountFilter implements Filter {

	@Autowired
	UserAccountRepository repository;
	
	@Autowired
	AccountConfiguration configuration;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;	// casting
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		System.out.println(path);
		String method = request.getMethod();
		System.out.println(method);
		boolean filter1 = path.startsWith("/account") &&  "DELETE".equals(method);
		if(filter1)  {
			String token = request.getHeader("Authorization");
			if(token == null) {
				response.sendError(401, "Unauthorized");
				return;
			}
			AccountUserCredentials userCredentials = null;
			try {
				userCredentials = configuration.decodeToken(token);
			} catch (Exception e) {
				response.sendError(401, "Can't decode token");
				return;
			}
			UserAccount userAccount = repository.findById(userCredentials.getLogin()).orElse(null);
			if(userAccount == null) {
				response.sendError(401, "User not found");
				return;
			} else {
				if (!BCrypt.checkpw(userCredentials.getPassword(), userAccount.getPassword())) {
					response.sendError(403, "Wrong password");
					return;
				}
			}
			boolean flagSet ;
			flagSet = userAccount.getRoles().retainAll(new HashSet<String>(Arrays.asList("moderator","admin")));
			if (!(path.contentEquals(userCredentials.getLogin()))||
					!flagSet){
			response.sendError(403, "Acsess denied");
			return;
			}
//			if (!path.contentEquals("login")) {
//				response.sendError(401, "Wrong request");
//				return;
//			}
		
			try 
			{
				String loginX	= path.substring(path.indexOf("/account/")+9,path.length());
				repository.findById(loginX);
				}
			catch (Exception e) {
				response.sendError(401, "Wrong login removable user");
				return;
			};
			}
			
		chain.doFilter(request, response);
	}

}
