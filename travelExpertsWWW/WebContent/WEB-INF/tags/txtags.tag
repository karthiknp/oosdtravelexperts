<%@ tag body-content="empty" %>

<jsp:useBean id="today" class="java.util.Date">
<%= today.toGMTString() %>
</jsp:useBean>
