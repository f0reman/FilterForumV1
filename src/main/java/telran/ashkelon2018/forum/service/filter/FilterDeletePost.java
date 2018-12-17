package telran.ashkelon2018.forum.service.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import telran.ashkelon2018.forum.configuration.AccountConfiguration;
import telran.ashkelon2018.forum.configuration.AccountUserCredentials;
import telran.ashkelon2018.forum.dao.ForumRepository;
import telran.ashkelon2018.forum.dao.UserAccountRepository;
import telran.ashkelon2018.forum.domain.Post;
import telran.ashkelon2018.forum.domain.UserAccount;

@Service
@Order(6)
public class FilterDeletePost implements Filter {

	@Autowired
	UserAccountRepository repository;

	@Autowired
	AccountConfiguration configuration;

	@Autowired
	ForumRepository repositoryForume;

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req; // casting
		HttpServletResponse response = (HttpServletResponse) resp;
		String path = request.getServletPath();
		System.out.println(path);
		String method = request.getMethod();
		System.out.println(method);
		if (path.startsWith("/forum/post") && "DELETE".equals(method)) {
			AccountUserCredentials userCredentials = null;

			try {
				String token = request.getHeader("Authorization");
				userCredentials = configuration.decodeToken(token);
				UserAccount userAccount = repository.findById(userCredentials.getLogin()).orElse(null);
				Post postFind = repositoryForume
						.findById(path.substring(path.indexOf("/post/") + 6, path.length())).orElse(null);
				if (!postFind.getAuthor().equals(userAccount.getLogin())) {
					response.sendError(403, "Acsess denied");
					return;	
				}
				
			} catch (Exception e) {
				response.sendError(403, "Acsess denied");
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
