package com.dm.platform.security;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

public class IPAuthenticationFilter extends OncePerRequestFilter {

	// 过滤ip
	private List<String> ips;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		// 是否ip验证可以通过
		boolean hasIp = false;
		// 对ip进行对比
		if (ips.size() > 0) {
			// 获取ip
			String requestsIp = request.getRemoteAddr();
			for (String ip : ips) {
				if (ip.equals(requestsIp)) {
					hasIp = true;
					break;
				}
			}
			if (!hasIp) {
				throw new AccessDeniedException("ip被拦截,您的ip是: " + requestsIp);
			}
		}
		chain.doFilter(request, response);
	}

	public List<String> getIps() {
		return ips;
	}

	public void setIps(List<String> ips) {
		this.ips = ips;
	}

}
