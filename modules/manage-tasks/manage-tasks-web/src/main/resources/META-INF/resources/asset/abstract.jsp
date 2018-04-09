<%--
/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */
--%>

<%@ include file="/init.jsp" %>

<%
Task task = (Task)request.getAttribute(ManageTasksWebKeys.TASK);
%>

<%= task.getTitle() %>