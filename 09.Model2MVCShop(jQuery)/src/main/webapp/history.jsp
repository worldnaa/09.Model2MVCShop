<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%
	System.out.println("========== history.jsp 시작 ==========");

	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	Cookie[] cookies = request.getCookies();
	System.out.println("cookies 길이 : " + cookies.length);
	
	if (cookies!=null && cookies.length > 0) {
		
		System.out.println("Client에서 전송된 Cookie 있습니다");
		
		for (int i=0; i<cookies.length; i++) {
			
			Cookie cookie = cookies[i];
			System.out.println("cookie 저장 : " + cookies[i]);
			
			if (cookie.getName() != null && cookie.getName().startsWith("history")) {
				
				history = cookie.getValue();
				System.out.println("cookie.getName : " + cookie.getName());
				System.out.println("history : " + history);
		
		/* 
		if (history != null) {
			String[] h = history.split(",");
			for (int i=0; i<h.length; i++) {
				if (!h[i].equals("null")) {
		 */			
				%>
				<a href="/getProduct.do?prodNo=<%=history%>&menu=search"
				   target="rightFrame"><%=history%></a>
				<br>
				<%	
					
			}
		}
	}
	System.out.println("========== history.jsp 종료 ==========");
%>

</body>
</html>