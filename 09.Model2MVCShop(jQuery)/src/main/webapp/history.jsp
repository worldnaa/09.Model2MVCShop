<%@page import="java.net.URLDecoder"%>
<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>��� ��ǰ ����</title>

</head>
<body>
	����� ��� ��ǰ�� �˰� �ִ�
<br>
<br>
<%
	System.out.println("========== history.jsp ���� ==========");

	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	Cookie[] cookies = request.getCookies();
	System.out.println("cookies ���� : " + cookies.length);
	
	if (cookies!=null && cookies.length > 0) {
		
		System.out.println("Client���� ���۵� Cookie �ֽ��ϴ�");
		
		for (int i=0; i<cookies.length; i++) {
			
			Cookie cookie = cookies[i];
			System.out.println("cookie ���� : " + cookies[i]);
			
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
	System.out.println("========== history.jsp ���� ==========");
%>

</body>
</html>