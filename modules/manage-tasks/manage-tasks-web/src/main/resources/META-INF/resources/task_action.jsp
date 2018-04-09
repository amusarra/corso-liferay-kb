<%--
/**
 * Copyright (c) SMC Treviso Srl. All rights reserved.
 */
--%>

<%@ include file="/init.jsp" %>

<%
ResultRow row = (ResultRow)request.getAttribute(ManageTasksWebKeys.SEARCH_CONTAINER_RESULT_ROW);

Task task = (Task)row.getObject();

long taskId = task.getTaskId();
%>

<liferay-ui:icon-menu markupView="lexicon">
	<c:if test="<%= TaskPermissionChecker.contains(permissionChecker, task, ActionKeys.UPDATE) %>">
		<portlet:renderURL var="updateTaskURL">
			<portlet:param name="mvcRenderCommandName"
				value="/manage-tasks/task/edit"
			/>
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="taskId" value="<%= String.valueOf(taskId) %>" />
		</portlet:renderURL>

		<liferay-ui:icon message="edit" url="<%= updateTaskURL %>" />
	</c:if>

	<c:if test="<%= TaskPermissionChecker.contains(permissionChecker, task, ActionKeys.PERMISSIONS) %>">
		<liferay-security:permissionsURL
			modelResource="it.smc.lrtraining.manage.tasks.model.Task"
			modelResourceDescription="<%= task.getTitle() %>"
			resourcePrimKey="<%= String.valueOf(taskId) %>"
			var="permissionsURL"
			windowState="<%= LiferayWindowState.POP_UP.toString() %>"
		/>

		<liferay-ui:icon
			message="permissions"
			method="get"
			url="<%= permissionsURL %>"
			useDialog="<%= true %>"
		/>
	</c:if>

	<c:if test="<%= TaskPermissionChecker.contains(permissionChecker, task, ActionKeys.DELETE) %>">
		<portlet:actionURL name="deleteTask" var="deleteTaskURL">
			<portlet:param name="redirect" value="<%= currentURL %>" />
			<portlet:param name="taskId" value="<%= String.valueOf(taskId) %>" />
		</portlet:actionURL>

		<liferay-ui:icon-delete url="<%= deleteTaskURL %>" />
	</c:if>
</liferay-ui:icon-menu>